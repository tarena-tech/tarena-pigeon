import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'

// create an axios instance
const instance = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  headers: {
    'content-type': 'application/json'
  },
  timeout: 60000,
  nonConcurrent: true
})

// request interceptor
instance.interceptors.request.use(
  config => {
    // do something before request is sent

    if (process.env.NODE_ENV === 'development' && config.useMockServer === true) {
      config.baseURL = process.env.VUE_APP_MOCK_SERVER
    }

    console.log('http-request-config:', config)

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation

      config.headers['Authorization'] = 'Bearer ' + store.getters.token
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
instance.interceptors.response.use(
  /**
   * 接口业务处理成功，只返回业报文中的 data;
   * 处理失败，返回整个报文，在catch 中捕获，通过 ‘serverError’ 是否为 true 区分是否是 http请求错误；
   */
  response => {
    const res = response.data
    console.log(response)
    if (response.request.responseType === 'blob' || response.headers['content-excelname']) {
      console.log('ress', response)
      return response
    }

    if (res.succeed !== true) { // 全局业务异常信息提示
      if (!response.config.hideGlobalMsg) {
        Message.warning(res.error)
      }
      return Promise.reject(res)
    }
    return res.data

    // return Promise.reject(new Error(res.message || 'Error'))
  },
  error => {
    console.log('http-err：' + error) // for debug
    Message.warning(error.message)
    return Promise.reject({
      'code': -10000,
      'data': '',
      'error': error.message,
      'succeed': false,
      'serverError': true
    })
  }
)

export const post = (url, data, config = {}) => instance.post(url, data, config)
export const get = (url, params, config = {}) => instance.get(url, {
  params,
  ...config
})
export const del = (url, data, config = {}) => instance.delete(url, {
  data,
  ...config
})
export const put = (url, params, config = {}) => instance.put(url, params, config)

export default instance
