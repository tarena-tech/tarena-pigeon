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
        <el-form-item label="服务商名称" prop="name">
          <el-input v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="服务商编码" prop="code">
          <el-input v-model="ruleForm.code" />
        </el-form-item>
        <el-form-item label="消息类型" prop="noticeType">
          <com-dict :val.sync="ruleForm.noticeType" dict-name="noticeType" :is-all="false"/>
        </el-form-item>
        <el-form-item label="官网地址" prop="officialWebsite">
          <el-input v-model="ruleForm.officialWebsite" />
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="ruleForm.contacts" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="ruleForm.phone" />
        </el-form-item>
        <el-form-item label="JSON配置" prop="clientConfig">
          <el-input v-model="ruleForm.clientConfig" type="textarea" />
        </el-form-item>
        <el-form-item label="简介" prop="remarks">
          <el-input v-model="ruleForm.remarks" type="textarea" />
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
import { save } from '@/api/provider'
import { isLegalHttpUrl, isJson } from '@/utils/validate'

export default {
  name: 'DialogProviderSave',
  data() {
    return {
      dialogVisible: false,
      loading: false,
      windowName: '创建',
      ruleForm: {
        name: null,
        code: null,
        noticeType: null,
        officialWebsite: null,
        contacts: null,
        phone: null,
        clientConfig: null,
        remarks: null
      },
      rules: {
        name: [
          { required: true, message: '请输入服务商名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入code码', trigger: 'blur' }
        ],
        noticeType: [
          { required: true, message: '请选择消息类型', trigger: 'blur' }
        ],
        officialWebsite: [
          { required: true, validator: isLegalHttpUrl, trigger: 'blur' }
        ],
        contacts: [
          { required: true, message: '请输入联系人', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' }
        ],
        clientConfig: [
          { required: true, validator: isJson, trigger: 'blur' }
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
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          save(this.ruleForm)
            .then(res => {
              this.cancelForm()
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
      this.ruleForm = {}
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
