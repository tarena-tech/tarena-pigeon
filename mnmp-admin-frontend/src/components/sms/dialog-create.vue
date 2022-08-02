<template>
  <el-drawer
    ref="drawer"
    title="创建"

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

        <el-form-item label="模板名称" prop="name">
          <el-input v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="模板编码" prop="code">
          <el-input v-model="ruleForm.code" />
        </el-form-item>
        <el-form-item label="模板类型" prop="templateType">
          <com-dict :val.sync="ruleForm.templateType" dict-name="templateType" :is-all="false"/>
        </el-form-item>
        <el-form-item label="通知类型" prop="noticeType">
          <com-dict :val.sync="ruleForm.noticeType" dict-name="noticeType" :is-all="false"/>
        </el-form-item>

        <el-form-item label="应用(做成下拉框赛选)" prop="appId">
          <el-input v-model="ruleForm.appId" />
        </el-form-item>
        <el-form-item label="描述" prop="remark">
          <el-input v-model="ruleForm.remark" type="textarea" />
        </el-form-item>


<!--        <el-form-item label="活动名称" prop="name">-->
<!--          <el-input v-model="ruleForm.name" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="活动区域" prop="region">-->
<!--          <el-input v-model="ruleForm.name2" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="活动形式" prop="desc">-->
<!--          <el-input v-model="ruleForm.desc" type="textarea" />-->
<!--        </el-form-item>-->
      </el-form>
      <div class="cus-drawer__footer">
        <el-button @click="cancelForm">取 消</el-button>
        <el-button type="primary" :loading="loading" @click="submitForm()">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
export default {
  name: 'SmsDialogCreate',
  data() {
    return {
      dialogVisible: false,
      loading: false,
      ruleForm: {
        name: '',
        name2: '',
        desc: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入code码', trigger: 'blur' }
        ],
        templateType: [
          { required: true, message: '请选择模板类型', trigger: 'blur' }
        ],
        noticeType: [
          { required: true, message: '请选择通知类型', trigger: 'blur' }
        ],
        appId: [
          { required: true, message: '请选择适用的应用', trigger: 'blur' }
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
    },
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm() {
      this.$refs['ruleForm'].resetFields()
    },
    show() {
      this.dialogVisible = true
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
