<template>
<!--  <el-drawer-->
<!--    ref="drawer"-->
<!--    :visible.sync="dialogVisible"-->
<!--    direction="rtl"-->
<!--    :close-on-press-escape="false"-->
<!--    :close-on-click-modal="false"-->
<!--    append-to-body-->
<!--    custom-class="cus-drawer"-->
<!--    :destroy-on-close="true"-->
<!--    size="500px"-->
<!--  >-->
  <el-dialog title="审核" :visible.sync="dialogFormVisible" :destroy-on-close="true">
    <el-form ref="ruleForm" class="cus-form" :model="ruleForm" :rules="rules">
      <el-form-item label="描述" prop="auditResult">
        <el-input v-model="ruleForm.auditResult" autocomplete="on" type="textarea"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="auditing(-1)">拒 绝</el-button>
      <el-button @click="auditing(1)">通 过</el-button>
    </div>
  </el-dialog>
<!--  </el-drawer>-->
</template>
<script>

import {audit} from "@/api/app";

export default {
  name: 'DialogAppAudit',
  data() {
    return {
      dialogVisible: false,
      dialogFormVisible: false,
      ruleForm: {
        id: null,
        auditStatus: null,
        auditResult: null
      },
      rules: {
        auditResult: [
          { required: true, message: '请输入审核意见', trigger: 'blur' }
        ],
        id: [
          { required: true, message: '网络抖动，请刷新页面重新审核', trigger: 'blur' }
        ],
        auditStatus: [
          { required: true, message: '网络抖动，请刷新页面重新审核', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    show(_id) {
      this.dialogFormVisible = true
      this.ruleForm = {};
      this.ruleForm.id = _id;
    },
    cancelForm() {
      this.loading = false
      // this.dialogVisible = false
      // this.$refs.drawer.closeDrawer()
      this.$emit('refresh')

    },
    auditing(status) {
      this.ruleForm.auditStatus = status;
      console.dir(this.ruleForm)
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          audit(this.ruleForm)
            .then(res => {
              this.$message({
                type: 'success',
                message: '操作成功!'
              });
              this.dialogFormVisible = false;
              this.cancelForm();
            })
            .catch(err => {
              console.error('audit fial', err);
            })

        }
      })
    }
  }
}
</script>
