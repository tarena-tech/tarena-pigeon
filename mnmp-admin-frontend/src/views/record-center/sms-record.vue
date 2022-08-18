<!-- 任务 -->
<template>
  <div class="app-container">
    <el-form ref="claFrom" :inline="true" :model="claForm">
      <div>
        <div class="form-left-box">
          <el-form-item prop="target" label="目标">
            <el-input v-model.trim="claForm.target" placeholder="" style="width: 120px"></el-input>
          </el-form-item>
          <el-form-item prop="bizId" label="回执码">
            <el-input v-model.trim="claForm.bizId" placeholder="" style="width: 120px"></el-input>
          </el-form-item>
          <el-form-item label="应用" prop="appCode">
            <template>
              <el-select v-model="claForm.appCode" filterable placeholder="请选择" :filter-method="queryApps">
                <el-option v-for="item in apps" :key="item.id" :label="item.name" :value="item.code" />
              </el-select>
            </template>
          </el-form-item>
          <el-form-item prop="taskStatus" label="任务状态">
            <com-dict :val.sync="claForm.status" dict-name="sendStatus" :is-all="true" />
          </el-form-item>

          <el-form-item prop="triggerTime" label="执行时间">
            <template>
              <div class="block">
                <el-date-picker
                  v-model="claForm.triggerTimes"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </div>
            </template>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="toResetPageForList">查询</el-button>
            <el-button type="default" icon="el-icon-delete" @click="resetForm">重置</el-button>
          </el-form-item>
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

        <el-table-column prop="appCode" label="应用编码" />
        <el-table-column prop="target" label="目标电话" />
        <el-table-column prop="pushTime" label="发送时间"/>
        <el-table-column prop="status" label="发送状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">发送给供应商失败</span>
            <span v-if="scope.row.status === 1">发送给供应商成功</span>
            <span v-if="scope.row.status === 2">发送给目标失败</span>
            <span v-if="scope.row.status === 3">发送给目标成功</span>
          </template>
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
  </div>
</template>

<script>
import { queryPage } from '@/api/SmsRecord'
import { queryAppList } from '@/api/app'
import TmpTablePagination from '@/components/table-pagination/table-pagination.vue'
export default {
  name: 'DemoTable',
  components: {
    TmpTablePagination
  },
  data() {
    return {
      claForm: {
        bizId: null, // 任务名
        appCode: null, // appId 通过接口获取
        status: null, // 任务状态 0:未开启 1:推送中 2:终止 3:已结束 4.失败
        triggerTimes: [],
        startTriggerTime: null,
        endTriggerTime: null,
        taskId: null,
        target: null
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
    this.claForm.target = this.$route.query.target
    this.claForm.taskId = this.$route.query.taskId

    this.getTabelData()
    this.queryApps()
  },
  created() {},
  methods: {
    resetForm() {
      this.claForm.taskId = null
      this.$refs.claFrom.resetFields()
    },
    getTabelData() {
      this.$refs.tmp_table.loadingState(true)

      if (this.claForm.triggerTimes.length === 2) {
        this.claForm.startTriggerTime = this.claForm.triggerTimes[0]
        this.claForm.endTriggerTime = this.claForm.triggerTimes[1]
      }
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
    queryApps(param) {
      queryAppList({
        name: param,
        enable: 1,
        auditStatus: 1
      })
        .then(res => {
          this.apps = res
        }).catch(err => {
        console.error(err)
      })},

    // 重置页码并搜索
    toResetPageForList() {
      this.pagination.currentPageIndex = 1
      // if (null != this.claForm.triggerTimes && this.claForm.triggerTimes.siz) {
      //
      // }
      console.dir(this.claForm.triggerTimes)
      console.log(this.claForm.triggerTimes.length)
      this.getTabelData()
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
