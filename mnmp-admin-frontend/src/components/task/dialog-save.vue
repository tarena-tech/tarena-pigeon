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
          <el-upload
            class="upload-demo"
            ref="upload"
            action=""
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :multiple="false"
            :on-change="fileCallback"
            :limit="1"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取Excel文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
          </el-upload>


          <el-form-item label="模板名称" prop="name">
            <el-input v-model="ruleForm.name" />
          </el-form-item>
          <el-form-item label="模板编码" prop="code">
            <el-input v-model="ruleForm.code" />
          </el-form-item>
          <el-form-item label="模板类型" prop="templateType">
            <com-dict :val.sync="ruleForm.templateType" dict-name="templateType" :is-all="false" />
          </el-form-item>
          <el-form-item label="通知类型" prop="noticeType">
            <com-dict :val.sync="ruleForm.noticeType" dict-name="noticeType" :is-all="false" />
          </el-form-item>
          <el-form-item label="应用" prop="appId">
            <template>
              <el-select v-model="ruleForm.appId" filterable placeholder="请选择" :filter-method="queryApps">
                <el-option
                  v-for="item in apps"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
            </template>
          </el-form-item>

          <el-form-item label="签名" prop="sign">
            <template>
              <el-select v-model="sign" filterable placeholder="请选择" :filter-method="querySigns">
                <el-option
                  v-for="item in signs"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
            </template>
          </el-form-item>

          <el-form-item label="模板" prop="template">
            <template>
              <el-select v-model="template" filterable placeholder="请选择" :filter-method="queryTemplates">
                <el-option
                  v-for="item in templates"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </template>
          </el-form-item>

          <el-form-item label="模板内容" prop="content">
            <el-input v-model="ruleForm.content" type="textarea" />
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="ruleForm.remark" type="textarea" />
          </el-form-item>

        </el-form>
      </el-scrollbar>
      <div class="cus-drawer__footer">
        <el-button @click="cancelForm()">取 消</el-button>
        <el-button type="primary" :loading="loading" @click="submitForm()">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { save } from '@/api/task'
import { queryAppList } from '@/api/app'
import { querySignList } from '@/api/sign'
import { querySmsTemplateList } from '@/api/sms'


export default {
  name: 'DialogTaskSave',
  data() {
    return {
      dialogVisible: false,
      windowName: '创建',
      loading: false,
      ruleForm: {
        name: null,
        code: null,
        appId: null,
        noticeType: null,
        content: null,
        templateType: null,
        remark: null
      },
      apps: {

      },
      signs: {

      },
      templates: {

      },
      sign: null,
      app: null,
      template: null,
      rules: {
        name: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入code码', trigger: 'blur' }
        ],
        appId: [
          { required: true, message: '请选择应用', trigger: 'blur' }
        ],
        noticeType: [
          { required: true, message: '请选择通知类型', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请填写模板内容', trigger: 'blur' }
        ],
        templateType: [
          { required: true, message: '请选择模板类型', trigger: 'blur' }
        ]
      }
    }
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
      queryAppList({name: param})
        .then(res => {
          this.apps = res
          console.dir(res)
      }).catch(err => {
        console.log(err);
      })
    },
    querySigns(param) {
      console.log("333333", param)
      querySignList({name: param})
        .then(res => {
          this.signs = res
          console.dir(res)
        })
        .catch(err => {
          console.log(err);
        })
    },
    queryTemplates(param) {
      console.log("333333", param)
      querySmsTemplateList({templateName: param})
        .then(res => {
          console.dir(res)
          this.template = res
        })
        .catch(err => {
          console.log(err);
        })
    },


    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },

    fileCallback(file, list) {
      console.dir(file)
      console.dir(list)
    },

    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          // TODO 假数据
          this.ruleForm.appCode = 333
          this.ruleForm.appId = 333
          save(this.ruleForm)
            .then(res => {
              console.dir(res)
              this.cancelForm()
            })
            .catch(err => {
              console.error('create fail', err)
            })
          this.$emit('callback')
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
      if (data != null) {
        this.windowName = '修改'
        this.ruleForm = data
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
  .cus-form{
    padding-right: 15px;
  }
  .cus-scrollbar{
    flex: 1 1 0;
  }
  ::v-deep .el-scrollbar__wrap{
    overflow-x: hidden;
  }
}
.cus-drawer__footer{
  display: flex;
  button {
    flex: 1;
  }
}
</style>
