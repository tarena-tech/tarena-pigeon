/**
 * edit by @lianglei
 * 1、修改路由监听范围，只对mock-server 路径生效，否则全局监控会导致 SSO 重定向失败；
 * 2、添加监控路由配置文件自动注入功能，自动加载 /mock/json 下的路由配置文件
 */
const chokidar = require('chokidar')
const bodyParser = require('body-parser')
const chalk = require('chalk')
const path = require('path')
const Mock = require('mockjs')

// 【重点】 require.context 在此不可使用，所以显式注入进来
const requireContext = require('require-context')

const mockDir = path.join(process.cwd(), 'mock/json')

// 加载 mock 路由数据文件
function toLoadRoutesTemplate() {
  var mocks_ = []
  const req_ = requireContext(mockDir, false, /\.js$/)

  /*
  // 过滤示例文件
  const dataArr_ = req_.keys().filter((key) => {
    return true
    // return key !== 'example.js'
  }).map(req_) */
  // 不过滤文件
  const dataArr_ = req_.keys().map(req_)

  dataArr_.forEach((arr) => {
    mocks_ = mocks_.concat(arr)
  })
  return mocks_
}

function registerRoutes(app) {
  let mockLastIndex
  const mock_ = toLoadRoutesTemplate()
  const mocksForServer = mock_.map(route => {
    return responseFake(route.url, route.type, route.response)
  })
  for (const mock of mocksForServer) {
    app[mock.type](mock.url, mock.response)
    mockLastIndex = app._router.stack.length
  }
  const mockRoutesLength = Object.keys(mocksForServer).length
  return {
    mockRoutesLength: mockRoutesLength,
    mockStartIndex: mockLastIndex - mockRoutesLength
  }
}

function unregisterRoutes() {
  Object.keys(require.cache).forEach(i => {
    if (i.includes(mockDir)) {
      delete require.cache[require.resolve(i)]
    }
  })
}

// for mock server
const responseFake = (url, type, respond) => {
  return {
    url: new RegExp(`${process.env.VUE_APP_MOCK_SERVER}${url}`),
    type: type || 'get',
    response(req, res) {
      // console.log('request invoke:' + req.path)
      res.json(Mock.mock(respond instanceof Function ? respond(req, res) : respond))
    }
  }
}

module.exports = app => {
  // app.listen(3000, () => console.log('Example app listening on port 3000!'))
  // parse app.body
  // https://expressjs.com/en/4x/api.html#req.body
  // 【重点】只对mock-server路由生效，否则与SSO冲突
  app.use(process.env.VUE_APP_MOCK_SERVER, bodyParser.json())
  app.use(process.env.VUE_APP_MOCK_SERVER, bodyParser.urlencoded({
    extended: true
  }))

  // return true
  const mockRoutes = registerRoutes(app)
  var mockRoutesLength = mockRoutes.mockRoutesLength
  var mockStartIndex = mockRoutes.mockStartIndex

  // watch files, hot reload mock server
  chokidar.watch(mockDir, {
    ignored: /mock-server/,
    ignoreInitial: true
  }).on('all', (event, path) => {
    if (event === 'change' || event === 'add') {
      try {
        // remove mock routes stack
        app._router.stack.splice(mockStartIndex, mockRoutesLength)

        // clear routes cache
        unregisterRoutes()

        const mockRoutes = registerRoutes(app)
        mockRoutesLength = mockRoutes.mockRoutesLength
        mockStartIndex = mockRoutes.mockStartIndex

        console.log(chalk.magentaBright(`\n > Mock Server hot reload success! changed  ${path}`))
      } catch (error) {
        console.log(chalk.redBright(error))
      }
    }
  })
}
