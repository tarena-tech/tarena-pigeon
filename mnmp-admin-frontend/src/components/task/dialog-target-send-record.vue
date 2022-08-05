<!-- 任务 -->
<template>
  <div class="app-container">
    <el-form ref="claFrom" :inline="true" :model="claForm">
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

        <el-table-column prop="appCode" label="应用编码" />
        <el-table-column prop="target" label="目标电话" />
        <el-table-column prop="pushTime" label="发送时间"/>
        <el-table-column prop="status" label="发送状态">
          <templat slot-scope="scope">
            <span v-if="scope.row.status === 0">发送给供应商失败</span>
            <span v-if="scope.row.status === 1">发送给供应商成功</span>
            <span v-if="scope.row.status === 2">发送给目标失败</span>
            <span v-if="scope.row.status === 3">发送给目标成功</span>
          </templat>
        </el-table-column>

        <el-table-column prop="triggerTime" label="执行时间"/>
        <el-table-column prop="pushReceiveTime" label="到达时间"/>
        <el-table-column prop="bizId" label="回执码" />

        <el-table-column prop="sendResult" label="响应描述">
          <template slot-scope="scope" >
            <el-popover v-if="scope.row.sendResult" trigger="hover" placement="top">
              <p>{{ scope.row.sendResult }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">目标参数</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column prop="status" label="短信内容">
          <template slot-scope="scope" >
            <el-popover v-if="scope.row.content" trigger="hover" placement="top">
              <p>{{ scope.row.content }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">目标参数</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间"/>
        <el-table-column prop="updateTime" label="更新时间"/>

      </tmp-table-pagination>
    </div>
    <dialog-task-audit ref="DialogTaskAudit" @refresh="refresh" />
    <dialog-task-save ref="DialogTaskSave" @refresh="refresh"/>
  </div>
</template>

<script>
import { queryPage } from '@/api/SmsRecord'
import TmpTablePagination from '@/components/table-pagination/table-pagination.vue'
export default {
  name: 'DemoTable',
  components: {
    TmpTablePagination,
  },
  data() {
    return {
      claForm: {
        name: null, // 任务名
        appId: null, // appId 通过接口获取
        taskStatus: null, // 任务状态 0:未开启 1:推送中 2:终止 3:已结束 4.失败
        taskAudit: null // 审核状态 -1：拒绝， 0：待审核， 1：审核通过
      },
      apps: {

      },
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
    this.claForm.eqTarget = this.$route.params.target;
    this.claForm.taskId = this.$route.params.taskId;
    this.getTabelData()
  },
  created() {},
  methods: {
    handleClick(row) {
      this.$message('点击了按钮！')
      console.log('click-row-data:', row)
    },
    resetForm() {
      this.$refs.claFrom.resetFields()
    },
    refresh() {
      this.toResetPageForList();
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
