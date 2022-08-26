<template>
  <el-dialog title="审核" :visible.sync="dialogFormVisible" :destroy-on-close="true">

    <el-form ref="ruleForm" class="cus-form" :model="ruleForm" :rules="rules" label-width="100px">
      <el-form-item label="签名名称" prop="name">
        <el-input v-model="ruleForm.name" :disabled="true" />
      </el-form-item>
      <el-form-item label="签名编码" prop="code">
        <el-input v-model="ruleForm.code" :disabled="true" />
      </el-form-item>
      <el-form-item label="应用" prop="">
        <template >
          <el-select :disabled="true" v-model="ruleForm.appId" filterable placeholder="请选择" :filter-method="queryApps">
            <el-option v-for="item in apps" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input v-model="ruleForm.remarks" type="textarea" :disabled="true" />
      </el-form-item>
      <el-form-item label="审核备注" prop="auditResult">
        <el-input v-model="ruleForm.auditResult" autocomplete="on" type="textarea"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="auditing(-1)">拒 绝</el-button>
      <el-button @click="auditing(1)">通 过</el-button>
    </div>
  </el-dialog>
</template>
<script>

import { audit } from '@/api/sign'
import { queryAppList } from "@/api/app";

export default {
  name: 'DialogSignAudit',
  data() {
    return {
      dialogFormVisible: false,
      ruleForm: {
        id: null,
        auditStatus: null,
        auditResult: null
      },
      apps: {

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
  mounted() {
    this.ruleForm = {}
  },
  methods: {
    show(data) {
      this.dialogFormVisible = true
      this.ruleForm = data
      this.queryApps(data.appId)
    },
    auditing(status) {
      this.ruleForm.auditStatus = status
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          audit(this.ruleForm)
            .then(res => {
              this.$message({
                type: 'success',
                message: '操作成功!'
              })
              this.dialogFormVisible = false
              this.$emit('refresh')
            })
            .catch(err => {
              console.error('audit fial', err)
            })
        }
      })
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
  }
}
</script>
