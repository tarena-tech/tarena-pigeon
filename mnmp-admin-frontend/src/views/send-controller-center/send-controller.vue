<!-- 签名 -->
<template>
  <div class="app-container">
    <el-form
      ref="claFrom"
      :inline="true"
      :model="claForm"
    >
      <div class="form-container">
        <div class="form-left-box">
          <el-form-item prop="name" label="手机号">
            <el-input v-model.trim="claForm.name" placeholder="" style="width: 120px" />
          </el-form-item>
          <el-form-item label="应用" prop="appId">
            <template>
              <el-select v-model="claForm.appId" filterable placeholder="请选择" :filter-method="queryApps">
                <el-option v-for="item in apps" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </template>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="toResetPageForList" >查询</el-button>
            <el-button type="default" icon="el-icon-delete" @click="resetForm">重置</el-button>
          </el-form-item>
        </div>
        <div class="form-right-box" v-if="$store.state.user.role !== 'ROLE_user'">
          <el-button type="success" icon="el-icon-plus" @click="save(null)">新建</el-button>
        </div>
      </div>
    </el-form>
    <div class="cus-main-wrap">
      <tmp-table-pagination
        ref="tmp_table"
        :table-data="tableData"
        :pagination="pagination"
        border
        show-size
        hide-on-single-page
        @callback="getTabelData"
      >

        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="appName" label="应用编码" />
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="start" label="生效开始时间" />
        <el-table-column prop="end" label="生效结束次数" />
        <el-table-column prop="isEnable" label="启用" />
        <el-table-column prop="createTime" label="创建时间"   />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button  type="text" size="small" @click="changeStatus(scope.row)" v-if="scope.row.auditStatus === 1">
              {{scope.row.enabled === 1 ? '禁用' : '启用'}}
            </el-button>
            <el-button  type="text" size="small" @click="save(scope.row)" v-if="$store.state.user.role === 'ROLE_user' && scope.row.auditStatus !== 0">
              修改
            </el-button>
          </template>
        </el-table-column>
      </tmp-table-pagination>
    </div>
    <dialog-sign-save ref="DialogSignSave"  @refresh="refresh"/>
    <dialog-sign-audit ref="DialogSignAudit" @refresh="refresh"/>
  </div>
</template>

<script>
import { queryPage, changeEnable } from '@/api/send-controller.js'
import TmpTablePagination from '@/components/table-pagination/table-pagination.vue'
import DialogSignSave from '@/components/sign/dialog-save'
import DialogSignAudit from '@/components/sign/dialog-audit'
import { queryAppList } from "@/api/app";
export default {
  name: 'DemoTable',
  components: {
    TmpTablePagination,
    DialogSignSave,
    DialogSignAudit
  },
  data() {
    return {
      claForm: {
        appName: null, // 应用名称
        appCode: null, // 应用code
        auditStatus: null// 审核状态
      },
      apps: {},
      tableData: { recordCount: 0, list: [] },
      pagination: {
        // 数据表格配置项
        currentPageIndex: 1,
        pageSize: 10
      }
    }
  },
  computed: {},
  watch: {},
  mounted() {
    this.getTabelData()
    this.queryApps()
  },
  created() {},
  methods: {
    handleClick(row) {
      this.$message('点击了按钮！')
      console.log('click-row-data:', row)
    },
    queryApps(param) {
      queryAppList({
        name: param,
        enable: 1,
        auditStatus: 1
      }).then(res => {
        this.apps = res
      }).catch(err => {
        console.error(err)
      })},
    resetForm() {
      this.$refs.claFrom.resetFields()
    },
    getTabelData() {
      this.$refs.tmp_table.loadingState(true)
      const _data = {
        ...this.claForm,
        ...this.pagination
      }
      queryPage(_data)
        .then(res => {
          console.log('list-res:', res)
          this.$refs.tmp_table.loadingState(false)
          this.tableData = res
        })
        .catch((err) => {
          console.log('list-err:', err)
          this.$refs.tmp_table.loadingState(false)
        })
    },
    // 重置页码并搜索
    toResetPageForList() {
      this.pagination.currentPageIndex = 1
      this.getTabelData()
    },
    changeStatus(_data) {
      const msg = _data.enabled === 1 ? '禁用' : '启用'
      const str = '是否要' + msg + '【' + _data.name + '】'
      this.$confirm(str, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeEnable(_data.id).then(res => {
          this.successMsg()
          this.getTabelData()
        }).catch(err => {
          console.log('list-err:', err)
          this.$refs.tmp_table.loadingState(false)
        })
      })
    },
    successMsg() {
      this.$message({
        type: 'success',
        message: '操作成功!'
      })
    },

    showAudit(data) {
      this.$refs.DialogSignAudit.show(data)
    },
    save(data) {
      this.$refs.DialogSignSave.show(data)
    },
    refresh() {
      this.toResetPageForList()
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep.cus-main-wrap .el-table th.el-table__cell.is-leaf {
  background-color: #f8fafd;
  height: 55px;
}

::v-deep.cus-main-wrap .el-table th.el-table__cell.is-leaf .cell {
  font-size: 14px;
  color: #000;
  font-weight: 500;
  font-family: PingFang SC, PingFang SC-Medium;
}

::v-deep.el-pagination {
  display: flex;
  justify-content: flex-end;
}
.statusCircle {
  width: 8px;
  height: 8px;
  border-radius: 4px;
  display: inline-block;
}
.statusCircle1 {
  background: #72c040;
}
.statusCircle2 {
  background: #828282;
}
.column-status {
  text-align: center;
}
</style>
