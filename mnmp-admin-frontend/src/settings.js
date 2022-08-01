module.exports = {

  title: 'Vue Admin Template',

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: false,
  /**
   * 本地字典数据
   * 枚举类型统一为 value \ label
   * 不包含 全部（value = null）
   */
  localDict: {
    expOpts: [
      {
        value: '1',
        label: '名称'
      }
    ]
  }
}
