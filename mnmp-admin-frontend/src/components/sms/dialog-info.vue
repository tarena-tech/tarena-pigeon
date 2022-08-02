<template>
  <!-- 短信模板详情 -->
  <el-dialog
    :visible.sync="dialogVisible"
    width="70%"
    custom-class="cus-drawer"
    append-to-body
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    @closed="closeFn"
  >
    <p>这里是内容:{{ rowData.name }}</p>
  </el-dialog>
</template>

<script>

export default {
  name: 'SmsDialogInfo',
  components: {
  },
  data() {
    return {
      dialogVisible: false,
      // 表单
      claForm: {},
      rowData: {}
    }
  },
  watch: {
  },
  methods: {
    init() {
      // TODO ajax
    },
    // 弹窗关闭后回调，要求重置数据状态及表单验证状态
    closeFn() {
      // this.$refs.claForm.resetFields()
    },
    show(param) {
      this.dialogVisible = true
      this.$nextTick(() => {
        if (param) {
          this.rowData = param
          console.log('init-show', param)
        } else {
          this.rowData = {}
        }
        this.init()
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
