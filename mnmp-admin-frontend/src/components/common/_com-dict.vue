<template>
  <!--
  通用数据字典下拉列表
  用法  <com-dict :val.sync="data.value" dict-name="thatName" :is-all="false"  />
  editor lianglei
   -->
  <el-select
    :value="val"
    v-bind="$attrs"
    @change="onChange"
    @clear="onClear"
  >
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>

<script>
import { localDict } from '@/settings.js'

export default {
  name: 'ComDict',
  components: {},
  props: {
    val: {
      type: [String, Number],
      default: null
    },
    dictName: {
      type: String,
      required: true
    },
    isAll: { // 是否显示全部
      type: Boolean,
      default: false
    },
    allLabel: { // 全部的label名称
      type: [String, Number],
      default: '全部'
    },
    allValue: { // 全部的默认值
      type: [String, Number],
      default: null
    },
    filterStr: { // 过滤，正则条件，匹配的则被过滤掉
      type: [String],
      default: null
    }
  },
  data() {
    return {
      options: []
    }
  },
  computed: {},
  watch: {},
  mounted() {
    this.init()
  },
  methods: {
    init() {
      let temp_ = localDict[this.dictName]
      if (temp_ && Array.isArray(temp_)) {
        if (this.filterStr !== null) {
          temp_ = temp_.filter(v => {
            return !(new RegExp('^(' + this.filterStr + ')$').test(v.value))
          })
        }
        if (this.isAll) {
          this.options = [{
            value: this.allValue,
            label: this.allLabel
          }].concat(temp_)
        } else {
          this.options = [].concat(temp_)
        }
      } else {
        console.error('字典', this.dictName, '不存在或非数组类型！')
      }
    },
    onChange(value) {
      console.log('--onChange--', value)
      this.$emit('update:val', value)
      const cur_ = this.options.filter(item => {
        return item.value === value
      })
      this.$emit('change', cur_.length ? cur_[0] : {})
    },
    onClear() {
    }
  }
}
</script>

<style>
</style>
