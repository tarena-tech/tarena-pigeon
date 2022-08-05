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

        <el-form-item label="应用名称" prop="name">
          <el-input v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="应用编码" prop="code">
          <el-input v-model="ruleForm.code" />
        </el-form-item>
        <el-form-item label="负责人" prop="leader">
          <el-input v-model="ruleForm.leader" />
        </el-form-item>
        <el-form-item label="组内成员" prop="teamMembers">
          <el-input v-model="ruleForm.teamMembers" />
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
import {save} from "@/api/app";

export default {
  name: 'AppDialogSave',
  data() {
    return {
      dialogVisible: false,
      windowName: '创建',
      loading: false,
      ruleForm: {
        name: null,
        code: null,
        leader: null,
        teamMembers: null,
        remarks: null
      },
      rules: {
        name: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入code码', trigger: 'blur' }
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
              console.dir(res);
              this.$message({
                type: 'success',
                message: '操作成功!'
              });
              this.cancelForm();
            })
            .catch(err => {
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
      this.ruleForm = {};
      if (null != data) {
        this.windowName = "修改"
        this.ruleForm = data;
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
