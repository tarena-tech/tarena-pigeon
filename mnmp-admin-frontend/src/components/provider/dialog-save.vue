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
        <el-form-item label="服务商名称" prop="name">
          <el-input v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="服务商code" prop="code">
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
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="ruleForm.remarks" type="textarea" />
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
import {save} from "@/api/provider";

export default {
  name: 'DialogProviderSave',
  data() {
    return {
      dialogVisible: false,
      loading: false,
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
          { required: true, message: '请输入官网地址', trigger: 'blur' }
        ],
        contacts: [
          { required: true, message: '请输入联系人', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' }
        ],
        clientConfig: [
          { required: true, message: '请输入配置项', trigger: 'blur' }
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
      this.$emit('callback')

    },
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          save(this.ruleForm)
            .then(res => {
              console.dir(res);
              this.cancelForm();
            })
            .catch(err => {
              console.error("create fail", err);
            })
          this.$emit('reload')
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
      if (null != data) {
        this.windowName = "修改"
        this.ruleForm = data;
      }
      this.$nextTick(() => {
        // TODO init
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
