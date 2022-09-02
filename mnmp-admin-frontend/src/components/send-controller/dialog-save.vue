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
      <el-form ref="ruleForm" class="cus-form" :model="ruleForm" :rules="rules" label-width="100px">
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="ruleForm.phone" :disabled="true" />
        </el-form-item>
        <el-form-item label="应用编码" prop="appCode">
          <el-input v-model="ruleForm.appCode" :disabled="true" />
        </el-form-item>
        <el-form-item label="应用名称" prop="appName">
          <el-input v-model="ruleForm.appName" :disabled="true" />
        </el-form-item>
        <el-form-item label="生效开始" prop="startTime">
          <template>
            <div class="block">
              <el-date-picker
                v-model="ruleForm.startTime"
                type="date"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期">

              </el-date-picker>
            </div>
          </template>
        </el-form-item>
        <el-form-item label="生效结束" prop="endTime">
          <template>
            <div class="block">
              <el-date-picker
                v-model="ruleForm.endTime"
                type="date"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期">
              </el-date-picker>
            </div>
          </template>
        </el-form-item>
      </el-form>
      <div class="cus-drawer__footer">
        <el-button @click="cancelForm()">取 消</el-button>
        <el-button type="primary" :loading="loading" @click="submitForm()">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { save } from '@/api/send-controller'
import { queryAppList } from '@/api/app'
import store from "@/store";

export default {
  name: 'dialog-dialog-save',
  data() {
    return {
      dialogVisible: false,
      windowName: '创建',
      loading: false,
      disabled: false,
      ruleForm: {
        name: null,
        code: null,
        appId: null,
        remarks: null
      },
      apps: {

      },
      rules: {
        startTime: [
          { required: true, message: '请选择生效开始时间', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '请选择生效结束时间', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.queryApps()
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
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          save(this.ruleForm)
            .then(res => {
            })
            .catch(err => {
              console.error('create fail', err)
            })
          this.cancelForm()
        } else {
          return false
        }
      })
    },
    resetForm() {
      this.$refs['ruleForm'].resetFields()
    },
    show(data) {
      this.ruleForm = {}
      this.dialogVisible = true
      this.disabled = false
      if (data != null) {
        this.windowName = '修改'
        this.ruleForm = data

      }
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
    flex: 1;
  }
}
.cus-drawer__footer{
  display: flex;
  button {
    flex: 1;
  }
}
</style>
