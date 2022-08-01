<template>
  <el-upload
    class="upload-com-wrap"
    v-bind="merageOpts"
    v-on="$listeners"
  >
    <slot />
  </el-upload>
</template>

<script>
import { objectMerge } from '@/utils/index.js'
export default {
  name: 'ComUpload',
  inheritAttrs: false,
  props: {},
  data () {
    return {
      defaultOpts: {
        headers: {
          Authorization: this.$store.state.user.token
        },
        'show-file-list': false, // 是否显示已上传文件列表
        name: 'fileName', // 上传的文件字段名
        'on-error': 'uploadError'
      }
    }
  },
  computed: {
    merageOpts () {
      return objectMerge(
        this.defaultOpts,
        this.$attrs
      )
    }
  },
  beforeCreate () {
  },
  created () {

  },
  mounted () {

  },
  methods: {
    uploadError (response) {
      const mes_ = (JSON.parse(response.message)).tip
      this.$message.error('上传失败:' + mes_)
    }
  }
}
</script>

<style lang="scss" scoped></style>
