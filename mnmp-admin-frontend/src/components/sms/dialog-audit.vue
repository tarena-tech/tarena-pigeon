<template>
  <el-dialog ref="drawer" direction="rtl" title="审核" :visible.sync="dialogFormVisible" :destroy-on-close="true">
    <el-form ref="ruleForm" class="cus-form" :model="ruleForm" :rules="rules" label-width="100px">

      <el-form-item label="模板名称" prop="name">
        <el-input v-model="ruleForm.name" :disabled="true"/>
      </el-form-item>
      <el-form-item label="模板编码" prop="code">
        <el-input v-model="ruleForm.code" :disabled="true"/>
      </el-form-item>
      <el-form-item label="模板类型" prop="templateType">
        <com-dict :val.sync="ruleForm.templateType" dict-name="templateType" :is-all="false" :disabled="true"/>
      </el-form-item>
      <el-form-item label="通知类型" prop="noticeType">
        <com-dict :val.sync="ruleForm.noticeType" dict-name="noticeType" :is-all="false" :disabled="true"/>
      </el-form-item>
      <el-form-item label="应用" prop="">
        <template>
          <el-select v-model="ruleForm.appId" filterable placeholder="请选择" :disabled="true">
            <el-option v-for="item in apps" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </template>
      </el-form-item>

      <el-form-item label="供应商" prop="" v-if="$store.state.user.role !== 'ROLE_user'">
        <template>
          <el-select v-model="ruleForm.providerId" filterable placeholder="请选择" :filter-method="queryProviders">
            <el-option v-for="item in providers" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </template>
      </el-form-item>

      <el-form-item label="模板内容" prop="content">
        <el-input v-model="ruleForm.content" type="textarea"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="ruleForm.remark" type="textarea" :disabled="true"/>
      </el-form-item>
      <el-form-item label="审核描述" prop="auditResult">
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

import {audit} from '@/api/sms.js'
import {queryProviderList} from '@/api/provider'
import store from "@/store";
import {queryAppList} from "@/api/app";

export default {
  name: 'DialogAppAudit',
  data() {
    return {
      dialogFormVisible: false,
      ruleForm: {
        id: null,
        auditStatus: null,
        auditResult: null
      },
      apps: {},
      providers: {},
      rules: {
        auditResult: [
          {required: true, message: '请输入审核意见', trigger: 'blur'}
        ],
        id: [
          {required: true, message: '网络抖动，请刷新页面重新审核', trigger: 'blur'}
        ],
        auditStatus: [
          {required: true, message: '网络抖动，请刷新页面重新审核', trigger: 'blur'}
        ],
        providerId: [
          {required: true, message: '请选择供应商', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请填写模版内容', trigger: 'blur'}
        ],
      }
    }
  },
  mounted() {
    this.ruleForm = {}
    this.queryProviders()
  },
  methods: {
    cancelForm() {
      this.loading = false
      this.dialogFormVisible = false
      this.$refs.drawer.closeDrawer()
      this.$emit('refresh')
    },
    show(data) {
      this.dialogFormVisible = true
      this.ruleForm = {}
      if (null != data) {
        this.ruleForm = data
      }
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
              this.cancelForm()
            })
            .catch(err => {
              console.error('audit fial', err)
            })
        }
      })
    },
    queryProviders(param) {
      if (store.state.user.role === 'ROLE_user') {
        return
      }
      queryProviderList({
        name: param,
        auditStatus: 1,
        enable: 1,
        appendId: this.ruleForm.providerId
      }).then(res => {
        this.providers = res
      }).catch(err => {
        console.error(err)
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
