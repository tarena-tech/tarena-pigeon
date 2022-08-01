const tmpData = require('./data/example-data.js')
/**
 * @ lianglei
 * 获取客户端传递过来的数值
 * config.param('name')，自动尝试从 config.params，config.body，config.query 中查询 name的valeu
 * post 类型：config.body
 * get 类型：config.query
 * 使用建议：
 * 1、每个业务模块一个路由文件；
 * 2、大量且复杂的 data-template 建议放在 ./data 目录下，例如 const tmpData = require('./data/example-data.js')
 * 3、不建议使用 all 方法；
 */

module.exports = [
  {
    url: '/testapi/com-list',
    type: 'get',
    response: (config, res) => {
      const { type, title, currentPageIndex = 1, pageSize = 20, sort } = config.query

      let mockList = tmpData.comList_.filter(item => {
        if (type && item.type !== type) return false
        if (title && item.title.indexOf(title) < 0) return false
        return true
      })

      if (sort === '-id') {
        mockList = mockList.reverse()
      }

      const pageList = mockList.filter((item, index) => index < pageSize * currentPageIndex && index >= pageSize * (currentPageIndex - 1))
      // res.status(700)
      return {
        'code': '',
        'data': {
          recordCount: mockList.length,
          list: pageList
        },
        'error': 'api 内部错误',
        'succeed': true
      }
    }
  }
]
