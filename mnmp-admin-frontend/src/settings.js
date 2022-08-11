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
        value: 0,
        label: '待审核'
      },
      {
        value: -1,
        label: '审核拒绝'
      },
      {
        value: 1,
        label: '审核通过'
      }
    ],
    cycleLevel: [
      {
        value: 1,
        label: '小时'
      },
      {
        value: 2,
        label: '日'
      },
      {
        value: 3,
        label: '周'
      },
      {
        value: 4,
        label: '月'
      },
      {
        value: 5,
        label: '年'
      },
    ],
    taskStatusOpts: [
      {
        value: 0,
        label: '未开启'
      },
      {
        value: 1,
        label: '推送中'
      },
      {
        value: 2,
        label: '终止'
      },
      {
        value: 3,
        label: '已结束'
      },
      {
        value: 4,
        label: '失败'
      }
    ],
    templateType: [
      {
        value: null,
        label: '请选择'
      },
      {
        value: 1,
        label: '短信通知'
      },
      {
        value: 2,
        label: '验证码'
      },
      {
        value: 3,
        label: '推广短信'
      }
    ],
    noticeType: [
      {
        value: null,
        label: '请选择'
      },
      {
        value: 1,
        label: 'SMS'
      },
      {
        value: 2,
        label: 'EMAIL'
      },
      {
        value: 3,
        label: 'WECHAT'
      }
    ],
    taskType: [
      {
        value: 0,
        label: '立即'
      },
      {
        value: 1,
        label: '定时'
      },
      {
        value: 2,
        label: '周期'
      },
      {
        value: 3,
        label: '条件规则触发'
      }
    ],
    sendStatus: [
      {
        value: 0,
        label: '发送给供应商失败'
      },
      {
        value: 1,
        label: '发送给供应商成功'
      },
      {
        value: 2,
        label: '发送给目标失败'
      },
      {
        value: 3,
        label: '发送给目标成功'
      }
    ]
  }
}
