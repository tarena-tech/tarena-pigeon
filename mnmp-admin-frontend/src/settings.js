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
    ],
    auditOpts: [
      {
        value: '0',
        label: '待审核'
      },
      {
        value: '-1',
        label: '审核拒绝'
      },
      {
        value: '1',
        label: '审核通过'
      }
    ],
    taskStatusOpts: [
      {
        value: '0',
        label: '未开启'
      },
      {
        value: '1',
        label: '推送中'
      },
      {
        value: '2',
        label: '终止'
      },
      {
        value: '3',
        label: '已结束'
      },
      {
        value: '4',
        label: '失败'
      },
    ]
  }
}
