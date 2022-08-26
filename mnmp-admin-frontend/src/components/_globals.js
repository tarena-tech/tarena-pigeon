/*
 * 注册全局组件，组件命名格式 _com-xx[-xx].vue 全小写；
 * 使用： <com-xx[-xx]></com-xx[-xx]>
 * editor lianglei
 */

import Vue from 'vue'

// https://webpack.js.org/guides/dependency-management/#require-context
const requireComponent = require.context(
  './common',
  false,
  /_com-[\w-]+\.vue$/
)

requireComponent.keys().forEach((fileName) => {
  const componentConfig = requireComponent(fileName)
  const componentName = fileName
    // Remove  filename "./_"
    .replace(/^\.\/_/, '')
    // Remove 后缀名
    .replace(/\.\w+$/, '')
    .split('-')
    // 转首字母大写
    .map((item) => item.charAt(0).toUpperCase() + item.slice(1)).join('')
  // 注册到全局
  Vue.component(componentName, componentConfig.default || componentConfig)
})
