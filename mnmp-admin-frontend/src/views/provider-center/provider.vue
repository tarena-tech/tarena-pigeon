<!-- 应用 -->
<template>
  <div class="app-container">
    <el-form
      ref="claFrom"
      :inline="true"
      :model="claForm"
    >
      <div class="form-container">
        <div class="form-left-box">
          <el-form-item prop="name" label="供应商名称">
            <el-input v-model.trim="claForm.name" placeholder="" style="width: 120px"></el-input>
          </el-form-item>
          <el-form-item prop="code" label="供应商code" >
            <el-input v-model.trim="claForm.code" placeholder="" style="width: 120px"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              @click="toResetPageForList"
            >查询</el-button>
            <el-button
              type="default"
              icon="el-icon-delete"
              @click="resetForm"
            >重置</el-button>
          </el-form-item>
        </div>
        <div class="form-right-box">
          <el-button type="success" icon="el-icon-plus"  @click="save(null)">新建</el-button>
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

        <el-table-column prop="name" label="供应商名称" />
        <el-table-column prop="code" label="供应商编码" />
        <el-table-column prop="noticeType" label="业务类型">
          <template slot-scope="scope">
            <span v-if="scope.row.noticeType === 1">SMS</span>
            <span v-else-if="scope.row.noticeType === 2">EMAIL</span>
            <span v-else-if="scope.row.noticeType === 3">WECHAT</span>
            <span v-else>未知</span>
          </template>
        </el-table-column>
        <el-table-column prop="officialWebsite"  label="官方网站" />
        <el-table-column prop="remarks" label="应用简介"/>
        <el-table-column prop="contacts" label="联系人"/>
        <el-table-column prop="phone" label="联系电话"/>
        <el-table-column prop="remarks" label="简介"/>
        <el-table-column prop="auditStatus" label="审核状态">
          <template slot-scope="scope">
            <span v-if="scope.row.auditStatus === 1">通过</span>
            <span v-if="scope.row.auditStatus === 0">待审核</span>
            <span v-if="scope.row.auditStatus === -1">拒绝</span>
          </template>
        </el-table-column>
        <el-table-column prop="enabled" label="应用状态">
          <templat slot-scope="scope">
            <span>{{scope.row.enabled === 1 ? '启用' : '禁用'}}</span>
          </templat>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间"/>
        <el-table-column prop="updateTime" label="更新时间"/>


        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button  type="text" size="small" @click="changeStatus(scope.row.id)" >
              {{scope.row.enabled === 1 ? '禁用' : '启用'}}
            </el-button>
            <el-button v-if="scope.row.auditStatus === 0" @click="showAudit(scope.row.id)" type="text" size="small">
              审核
            </el-button>
            <el-button  type="text" size="small" @click="save(scope.row)" >
              修改
            </el-button>
          </template>
        </el-table-column>
      </tmp-table-pagination>
    </div>
    <!-- 详情弹窗 -->
    <dialog-sms-info ref="dialogSmsInfo" />
    <dialog-provider-save ref="DialogProviderSave" />
    <dialog-provider-audit ref="DialogProviderAudit" />
  </div>
</template>

<script>
import { queryList, changeEnableStatus } from '@/api/provider.js'
import TmpTablePagination from '@/components/table-pagination/table-pagination.vue'
import DialogProviderAudit from "@/components/app/dialog-audit";
import DialogProviderSave from "@/components/provider/dialog-create";
export default {
  name: 'DemoTable',
  components: {
    TmpTablePagination,
    DialogProviderSave,
    DialogProviderAudit
  },
  data() {
    return {
      claForm: {
        name: null, // 应用名称
        code: null // 应用code
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
    getTabelData() {
      this.$refs.tmp_table.loadingState(true)
      const _data = {
        ...this.claForm,
        ...this.pagination
      }
      queryList(_data)
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
    save(data) {
      this.$refs.DialogProviderSave.show(data)
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
    // 修改可用状态
    changeStatus(_id) {
      changeEnableStatus(_id)
        .then(res => {
          console.dir('change.....', res);
        }).catch(err => {
          console.dir('change.....', err);
      })
    },
    showAudit(_id) {
      this.$refs.DialogProviderAudit.show(_id);
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
