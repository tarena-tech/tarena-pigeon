<!-- 全部课表 -->
<template>
  <div class="app-container">
    <el-form
      ref="claFrom"
      :inline="true"
      :model="claForm"
    >
      <div class="form-container">
        <div class="form-left-box">
          <el-form-item prop="templateName" label="模板名称">
            <el-input v-model.trim="claForm.templateName" placeholder="" style="width: 120px" />
          </el-form-item>
          <el-form-item prop="templateCode" label="模板编码" >
            <el-input v-model.trim="claForm.templateCode" placeholder="" style="width: 120px" />
          </el-form-item>
          <el-form-item prop="auditStatus" label="审核">
            <com-dict :val.sync="claForm.auditStatus" dict-name="auditOpts" :is-all="true"/>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="toResetPageForList">查询</el-button>
            <el-button type="default" icon="el-icon-delete" @click="resetForm">重置</el-button>
          </el-form-item>
        </div>
        <div class="form-right-box">
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

        <el-table-column prop="name" label="模板名称" />

        <el-table-column prop="code" label="模板编码" />

        <el-table-column prop="templateType" label="模板类型">
          <template slot-scope="scope">
            <span v-if="scope.row.templateType === 0">全部</span>
            <span v-if="scope.row.templateType === 1">短信通知</span>
            <span v-if="scope.row.templateType === 2">验证码</span>
            <span v-if="scope.row.templateType === 3">推广短信</span>
          </template>
        </el-table-column>
        <el-table-column prop="noticeType" label="通知类型">
          <template slot-scope="scope">
            <span v-if="scope.row.noticeType === 1">SMS</span>
            <span v-if="scope.row.noticeType === 2">EMAIL</span>
            <span v-if="scope.row.noticeType === 3">WECHAT</span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="模板内容">
          <template slot-scope="scope" v-if="scope.row.content">
            <el-popover trigger="hover" placement="top">
              <p>{{ scope.row.content }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">模板内容</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column prop="useCount" label="使用次数" />

        <el-table-column prop="appCode" label="应用编码" />

        <el-table-column prop="auditStatus" label="审核状态">
          <template slot-scope="scope">
            <span v-if="scope.row.auditStatus === 1">通过</span>
            <span v-if="scope.row.auditStatus === 0">待审核</span>
            <span v-if="scope.row.auditStatus === -1">拒绝</span>
          </template>
        </el-table-column>

        <el-table-column prop="enabled" label="应用状态">
          <templat slot-scope="scope">
            <span>{{ scope.row.enabled === 1 ? '启用' : '禁用' }}</span>
          </templat>
        </el-table-column>

        <el-table-column prop="createTime" label="创建时间" />

        <el-table-column label="操作" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="changeStatus(scope.row)">
              {{ scope.row.enabled === 1 ? '禁用' : '启用' }}
            </el-button>
            <el-button v-if="scope.row.auditStatus === 0" @click="showAudit(scope.row.id)" type="text" size="small">
              审核
            </el-button>
            <el-button type="text" size="mini" @click="save(scope.row)">
              修改
            </el-button>
          </template>
        </el-table-column>
      </tmp-table-pagination>
    </div>
    <!-- 详情弹窗 -->
    <dialog-sms-info ref="dialogSmsInfo"/>
    <dialog-sms-info ref="dialogSmsInfo"/>
    <!-- 创建弹窗 -->
    <dialog-sms-save ref="DialogSmsSave" @refresh="refresh"/>
    <dialog-sms-audit ref="DialogSmsAudit" @refresh="refresh"/>
  </div>
</template>

<script>
import {queryListByPage, changeEnableStatus} from '@/api/sms.js'
import TmpTablePagination from '@/components/table-pagination/table-pagination.vue'
import DialogSmsAudit from "@/components/sms/dialog-audit";
import DialogSmsSave from "@/components/sms/dialog-save"

export default {
  name: 'DemoTable',
  components: {
    TmpTablePagination,
    DialogSmsAudit,
    DialogSmsSave
  },
  data() {
    return {
      claForm: {
        appCode: null, // 应用编码
        templateCode: null, // 模板编码
        templateName: null, // 模板名称
        auditStatus: null
      },

      tableData: {recordCount: 0, list: []},
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
  },
  created() {
  },
  methods: {
    handleClick(row) {
      this.$message('点击了按钮！')
      console.log('click-row-data:', row)
    },
    resetForm() {
      this.$refs.claFrom.resetFields()
    },
    getTabelData() {
      this.$refs.tmp_table.loadingState(true)
      const _data = {
        ...this.claForm,
        ...this.pagination
      }
      queryListByPage(_data)
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
    refresh() {
      this.toResetPageForList();
    },

    changeStatus(_data) {
      let msg = _data.enabled === 1 ? '禁用' : '启用';
      let str = '是否要' + msg + '【' + _data.name + '】';
      this.$confirm(str, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeEnableStatus(_data.id).then(res => {
          this.successMsg();
          console.dir(this.claForm)
          this.getTabelData()
        }).catch(err => {
          console.log('list-err:', err)
          this.$refs.tmp_table.loadingState(false)
        })
      });
    },

    successMsg() {
      this.$message({
        type: 'success',
        message: '操作成功!'
      });
    },

    // 审核
    showAudit(id) {
      this.$refs.DialogSmsAudit.show(id);
    },

    // 重置页码并搜索
    toResetPageForList() {
      this.pagination.currentPageIndex = 1
      this.getTabelData()
    },
    // 行内编辑
    toEditBtnFn(row) {
      this.$refs['updateSeriesClass'].show(row)
    },
    // 创建
    save(data) {
      this.$refs.DialogSmsSave.show(data)
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
