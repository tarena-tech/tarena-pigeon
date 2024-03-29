<template>
  <el-drawer
    ref="drawer"
    :title="windowName"
    :visible.sync="dialogVisible"
    direction="rtl"
    :close-on-press-escape="false"
    :close-on-click-modal="false"
    append-to-body
    custom-class="cus-drawer"
    :destroy-on-close="true"
    size="500px"
  >
    <div class="cus-drawer__content">
      <el-scrollbar class="cus-scrollbar">
        <el-form ref="ruleForm" class="cus-form" :model="ruleForm" :rules="rules" label-width="100px">
          <el-form-item>
            <el-upload
              ref="upload"
              class="upload-demo"
              :action=uploadUrl
              :on-success="uploadSuccess"
              :on-error="uploadError"
              :multiple="false"
              :headers=headers
              :limit="1"
              :auto-upload="false">
              <el-button size="small" style="margin-left: 10px;" type="primary" @click="downExcel(null)">下载Excel模板
              </el-button>
              <el-button slot="trigger" size="small" type="primary">选取Excel文件</el-button>
              <el-button prop="filePath" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">
                上传到服务器
              </el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="任务名称" prop="name">
            <el-input v-model="ruleForm.name"/>
          </el-form-item>
          <el-form-item label="任务类型" prop="taskType">
            <com-dict :val.sync="ruleForm.taskType" dict-name="taskType" :is-all="false" @change="taskType"/>
          </el-form-item>

          <el-form-item label="发送时间" prop="nextTriggerTime" v-if="showSendTriggerTime">
            <template>
              <div class="block">
                <el-date-picker
                  v-model="ruleForm.nextTriggerTime"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择日期时间">
                </el-date-picker>
              </div>
            </template>
          </el-form-item>

          <el-form-item label="模板类型" prop="templateType">
            <com-dict :val.sync="ruleForm.templateType" dict-name="templateType" :is-all="false"/>
          </el-form-item>
          <el-form-item label="消息类型" prop="noticeType">
            <com-dict :val.sync="ruleForm.noticeType" dict-name="noticeType" :is-all="false"/>
          </el-form-item>

          <el-form-item label="模拟发送" prop="mock">
            <el-switch
              v-model="ruleForm.mock"
              active-color="#13ce66"
              active-value="1"
              inactive-value="0"
              inactive-color="#ff4949">
            </el-switch>
          </el-form-item>

          <el-form-item label="应用" prop="appId">
            <template>
              <el-select v-model="ruleForm.appId" filterable placeholder="请选择" :filter-method="queryApps">
                <el-option
                  v-for="item in apps"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-form-item>

          <el-form-item label="签名" prop="signId">
            <template>
              <el-select v-model="ruleForm.signId" filterable placeholder="请选择" :filter-method="querySigns">
                <el-option
                  v-for="item in signs"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-form-item>

          <el-form-item label="模板" prop="templateId">
            <template>
              <el-select v-model="ruleForm.templateId" filterable placeholder="请选择" :filter-method="queryTemplates">
                <el-option
                  v-for="item in templates"
                  :key="item.value"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-form-item>

          <el-form-item label="周期类型" prop="cycleLevel" v-if="cycleShow">
            <com-dict :val.sync="ruleForm.cycleLevel" dict-name="cycleLevel" :is-all="false"/>
          </el-form-item>
          <el-form-item label="周期数" prop="cycleNum" v-if="cycleShow">
            <el-input v-model="ruleForm.cycleNum"/>
          </el-form-item>

          <el-form-item label="结束时间" prop="triggerEndTime">
            <template>
              <div class="block">
                <el-date-picker
                  v-model="ruleForm.triggerEndTime"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择日期时间">
                </el-date-picker>
              </div>
            </template>
          </el-form-item>

          <el-form-item label="描述" prop="remark">
            <el-input v-model="ruleForm.remark" type="textarea"/>
          </el-form-item>

        </el-form>
      </el-scrollbar>
      <div class="cus-drawer__footer" v-if="button">
        <el-button @click="cancelForm()">取 消</el-button>
        <el-button type="primary" :loading="loading" @click="submitForm()">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { addTask, downExcel } from '@/api/task'
import { queryAppList } from '@/api/app'
import { querySignList } from '@/api/sign'
import { querySmsTemplateList } from '@/api/sms'
import { downloadFileByBlob } from '@/utils/download-file'
import store from "@/store";

export default {
  name: 'DialogTaskSave',
  data() {
    return {
      dialogVisible: false,
      windowName: '创建',
      loading: false,
      button: true,
      cycleShow: false,
      showSendTriggerTime: false,
      headers: {
        'Authorization': 'Bearer ' + store.getters.token
      },
      uploadUrl: process.env.VUE_APP_BASE_API + '/task/uploadFile',
      ruleForm: {
        name: null,
        templateType: null,
        noticeType: null,
        appId: null,
        signId: null,
        templateId: null,
        cycleLevel: null,
        cycleNum: null,
        remark: null,
        filePath: null,
        mock: 1
      },
      apps: {},
      signs: {},
      templates: {},
      sign: null,
      app: null,
      template: null,
      rules: {
        name: [
          {required: true, message: '请输入模板名称', trigger: 'blur'}
        ],
        templateType: [
          {required: true, message: '请输入code码', trigger: 'blur'}
        ],
        appId: [
          {required: true, message: '请选择应用', trigger: 'blur'}
        ],
        noticeType: [
          {required: true, message: '请选择通知类型', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请填写模板内容', trigger: 'blur'}
        ],
        signId: [
          {required: true, message: '请选择签名模板', trigger: 'blur'}
        ],
        templateId: [
          {required: true, message: '请选择短信模板', trigger: 'blur'}
        ],

        filePath: [
          {required: true, message: '请选择上传文件', trigger: 'blur'}
        ],
        taskType: [
          {required: true, message: '请选择任务类型', trigger: 'blur'}
        ]
      }
    }
  },
  mounted() {
    this.queryApps()
    this.queryTemplates()
    this.querySigns()
  },
  methods: {
    handleClose(done) {

    },
    cancelForm() {
      this.loading = false
      // this.dialogVisible = false
      this.$refs.drawer.closeDrawer()
      this.$emit('refresh')
    },
    queryApps(param) {
      queryAppList({
        name: param,
        enable: 1,
        auditStatus: 1,
        appendId: this.ruleForm.appId
      }).then(res => {
        this.apps = res
      }).catch(err => {
        console.error(err)
      })
    },
    querySigns(param) {
      querySignList({
        name: param,
        enable: 1,
        auditStatus: 1
      })
        .then(res => {
          this.signs = res
        })
        .catch(err => {
          console.error(err)
        })
    },
    queryTemplates(param) {
      const params = {
        templateName: param,
        enable: 1,
        auditStatus: 1,
        appendId: this.ruleForm.templateId
      }
      querySmsTemplateList(params)
        .then(res => {
          this.templates = res
        })
        .catch(err => {
          console.log(err)
        })
    },

    submitUpload() {
      this.$refs.upload.submit()
    },

    uploadSuccess(response, file, fileList) {
      this.ruleForm.filePath = response.data
    },
    uploadError(err, file, fileList) {
      console.error(err)
    },

    downExcel(path) {
      downExcel({ path: path }, { responseType: 'blob' }).then(res => {
        downloadFileByBlob(res.data, res.headers['content-excelname'], res.data.type)
      })
    },
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          addTask(this.ruleForm)
            .then(res => {
              this.cancelForm()
              let msg = '操作成功!'
              let mt = 'success'
              let excelName = res.headers['content-excelname']
              if (excelName) {
                downloadFileByBlob(res.data, res.headers['content-excelname'], res.data.type)
                msg = '部分成功,存在非法数据!'
                mt = 'warning'
              }
              this.$message({
                type: mt,
                message: msg
              })

            })
            .catch(err => {
              console.error('create fail', err)
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm() {
      this.$refs['ruleForm'].resetFields()
    },
    show(data) {
      this.dialogVisible = true
      this.button = true
      this.ruleForm = {}
      if (data != null) {
        this.windowName = '修改'
        this.button = false
        this.ruleForm = data
        if (this.ruleForm.taskType === 2) {
          this.cycleShow = true
        }
        if (this.ruleForm.taskType === 1) {
          this.showSendTriggerTime = true
        }
      }
    },
    taskType(e) {
      if (e.value === 2) {
        this.cycleShow = true
        this.showSendTriggerTime = false
      } else if(e.value === 1) {
        this.showSendTriggerTime = true
        this.cycleShow = false
      }else {
        this.cycleShow = false
        this.showSendTriggerTime = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.cus-drawer__content {
  display: flex;
  flex-direction: column;
  padding: 0 15px 15px 15px;
  height: 100%;

  .cus-form {
    padding-right: 15px;
  }

  .cus-scrollbar {
    flex: 1 1 0;
  }

  ::v-deep .el-scrollbar__wrap {
    overflow-x: hidden;
  }
}

.cus-drawer__footer {
  display: flex;

  button {
    flex: 1;
  }
}
</style>
