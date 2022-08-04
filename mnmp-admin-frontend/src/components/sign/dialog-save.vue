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

        <el-form-item label="签名名称" prop="name">
          <el-input v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="签名编码" prop="code">
          <el-input v-model="ruleForm.code" />
        </el-form-item>
        <el-form-item label="应用" prop="appId">
          <el-input v-model="ruleForm.appId" />
        </el-form-item>
        <el-form-item label="描述" prop="remark">
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
import {save} from "@/api/sign";

export default {
  name: 'DialogSignSave',
  data() {
    return {
      dialogVisible: false,
      windowName: '创建',
      loading: false,
      ruleForm: {
        name: null,
        code: null,
        appId: null,
        remarks: null
      },
      rules: {
        name: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入code码', trigger: 'blur' }
        ],
        appId: [
          { required: true, message: '请选择应用', trigger: 'blur' }
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
          // TODO 假数据
          this.ruleForm.appId = 222
          this.ruleForm.appCode = 222
          save(this.ruleForm)
            .then(res => {
              console.dir(res);
              this.cancelForm();
            })
            .catch(err => {
              console.error("create fail", err);
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
