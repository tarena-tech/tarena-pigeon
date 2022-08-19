<!-- 任务 -->
<template>
  <div class="app-container">
    <el-form
      ref="claFrom"
      :inline="true"
      :model="claForm"
    >
      <div class="form-container">
        <div class="form-left-box">
          <el-form-item prop="name" label="任务名">
            <el-input v-model.trim="claForm.name" placeholder="" style="width: 120px"></el-input>
          </el-form-item>
          <el-form-item label="应用" prop="appId">
            <template>
              <el-select v-model="claForm.appId" filterable placeholder="请选择" :filter-method="queryApps">
                <el-option v-for="item in apps" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </template>
          </el-form-item>
          <el-form-item prop="taskStatus" label="任务状态">
            <com-dict :val.sync="claForm.taskStatus" dict-name="taskStatusOpts" :is-all="true" />
          </el-form-item>
          <el-form-item prop="appCode" label="审核" >
            <com-dict :val.sync="claForm.auditStatus" dict-name="auditOpts" :is-all="true" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="toResetPageForList">查询</el-button>
            <el-button type="default" icon="el-icon-delete" @click="resetForm">重置</el-button>
          </el-form-item>
        </div>
        <div class="form-right-box" v-if="$store.state.user.role === 'ROLE_user'">
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
        :fit="true"
      >

        <el-table-column prop="name" label="内务名称" />
        <el-table-column prop="taskType" label="任务类型">
          <template slot-scope="scope">
            <span v-if="scope.row.taskType === 0">立即</span>
            <span v-if="scope.row.taskType === 1">定时</span>
            <span v-if="scope.row.taskType === 2">周期</span>
            <span v-if="scope.row.taskType === 3">条件规则触发</span>
          </template>
        </el-table-column>
        <el-table-column prop="noticeType" label="消息类型">
          <template slot-scope="scope">
            <span v-if="scope.row.noticeType === 1">SMS</span>
            <span v-else-if="scope.row.noticeType === 2">EMAIL</span>
            <span v-else-if="scope.row.noticeType === 3">WECHAT</span>
            <span v-else>未知</span>
          </template>
        </el-table-column>
        <el-table-column prop="cycleLvel" label="周期类型">
          <template slot-scope="scope">
            <span v-if="scope.row.cycleLevel === 0">分钟</span>
            <span v-if="scope.row.cycleLevel === 1">小时</span>
            <span v-if="scope.row.cycleLevel === 2">日</span>
            <span v-if="scope.row.cycleLevel === 3">周</span>
            <span v-if="scope.row.cycleLevel === 4">月</span>
            <span v-if="scope.row.cycleLevel === 5">年</span>
            <scan v-else>--</scan>
          </template>
        </el-table-column>
        <el-table-column prop="cycleNum" label="周期数">
          <template slot-scope="scope">
            <span v-if="scope.row.cycleNum !== null">scope.row.cycleNum</span>
            <span v-else>--</span>

            <scan v-else>--</scan>
          </template>
        </el-table-column>
<!--        <el-table-column prop="targetFileUrl" label="文件地址">-->
<!--          <template slot-scope="scope" v-if="scope.row.targetFileUrl">-->
<!--            <i class="el-icon-download"  @click="downExcel(scope.row.targetFileUrl)"></i>-->
<!--          </template>-->
<!--        </el-table-column>-->

<!--        <el-table-column prop="error" label="描述">-->
<!--          <template slot-scope="scope">-->
<!--            <el-popover v-if="scope.row.remark" trigger="hover" placement="top">-->
<!--              <p>{{ scope.row.remark }}</p>-->
<!--              <div slot="reference" class="name-wrapper">-->
<!--                <el-tag size="medium">描述</el-tag>-->
<!--              </div>-->
<!--            </el-popover>-->
<!--          </template>-->
<!--        </el-table-column>-->

<!--        <el-table-column prop="error" label="错误日志">-->
<!--          <template slot-scope="scope">-->
<!--            <el-popover v-if="scope.row.error" trigger="hover" placement="top">-->
<!--              <p>{{ scope.row.error }}</p>-->
<!--              <div slot="reference" class="name-wrapper">-->
<!--                <el-tag size="medium">错误日志</el-tag>-->
<!--              </div>-->
<!--            </el-popover>-->
<!--          </template>-->
<!--        </el-table-column>-->

        <el-table-column prop="auditStatus" label="审核状态">
          <template slot-scope="scope">
            <span v-if="scope.row.auditStatus === 1">通过</span>
            <span v-if="scope.row.auditStatus === 0">待审核</span>
            <span v-if="scope.row.auditStatus === -1">拒绝</span>
          </template>
        </el-table-column>
        <el-table-column prop="taskStatus" label="当前状态">
          <template slot-scope="scope">
            <span v-if="scope.row.taskStatus === 0">未开启</span>
            <span v-if="scope.row.taskStatus === 1">推送中</span>
            <span v-if="scope.row.taskStatus === 2">终止</span>
            <span v-if="scope.row.taskStatus === 3">已结束</span>
            <span v-if="scope.row.taskStatus === 4">失败</span>
          </template>
        </el-table-column>
        <el-table-column prop="nextTriggerTime" label="下次触发时间"/>

        <el-table-column prop="createTime" label="创建时间"/>
<!--        <el-table-column prop="updateTime" label="更新时间"/>-->

        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.taskStatus === 0 || scope.row.taskStatus === 1"  type="text" size="small" @click="changeStatus(scope.row)" >
              终止
            </el-button>
            <el-button v-if="scope.row.auditStatus === 0 && $store.state.user.role !== 'ROLE_user'" @click="showAudit(scope.row.id)" type="text" size="small">
              审核
            </el-button>
            <el-button type="text" size="small" @click="jump(scope.row.id)">
              详情
            </el-button>
            <el-button type="text" size="small" @click="target(scope.row.id)">
              任务目标
            </el-button>
          </template>
        </el-table-column>
      </tmp-table-pagination>
    </div>
    <dialog-task-audit ref="DialogTaskAudit" @refresh="refresh" />
    <dialog-task-save ref="DialogTaskSave" @refresh="refresh"/>
  </div>
</template>

<script>
import {changeStatus, downExcel, queryPage} from '@/api/task.js'
import { queryAppList } from '@/api/app'
import { downloadFileByBlob } from '@/utils/download-file'
import TmpTablePagination from '@/components/table-pagination/table-pagination.vue'
import DialogTaskAudit from '@/components/task/dialog-audit'
import DialogTaskSave from '@/components/task/dialog-save'
export default {
  name: 'DemoTable',
  components: {
    TmpTablePagination,
    DialogTaskAudit,
    DialogTaskSave
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
    this.getTabelData()
    this.queryApps()
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
      this.toResetPageForList()
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
    changeStatus(data) {
      changeStatus(data.id).then(res => {
        console.log('list-res:', res)
        this.getTabelData()
      }).catch(err => {
        console.log('list-err:', err)
        this.$refs.tmp_table.loadingState(false)
      })
    },
    showAudit(_id) {
      this.$refs.DialogTaskAudit.show(_id)
    },
    save(data) {
      this.$refs.DialogTaskSave.show(data)
    },
    downExcel(path) {
      downExcel({ path: path }, { responseType: 'blob' }).then(res => {
        downloadFileByBlob(res.data, res.headers['content-excelname'], res.data.type)
      })
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

    jump(_id) {
      this.$router.push({ name: 'detail', query: { id: _id }})
    },
    target(_id) {
      this.$router.push({ name: 'targets', query: { id: _id }})
    },

    // 重置页码并搜索
    toResetPageForList() {
      this.pagination.currentPageIndex = 1
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
