import {
  get
} from '@/utils/request.js'

// 本应用内一般业务接口
// export const getTestList3 = (data) => post('/testapi/t01', data)

// 使用 mock 服务接口，仅限本地模拟使用，正式接口提供够，应删除 useMockServer: true
export const getTestList2 = (data) => get('/testapi/com-list', data, { useMockServer: true })

/*
 * 使用 application/x-www-form-urlencoded 格式
 * 需要安装Qs  npm install qs --save
 */

/* import qs from 'qs';
export const getList2 = (data) => post('/student/enumList', qs.stringify(data), {
  headers: {
    'content-type': 'application/x-www-form-urlencoded'
  }
}) */
