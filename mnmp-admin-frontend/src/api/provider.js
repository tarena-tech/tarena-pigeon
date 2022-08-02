import {
  get, post
} from '@/utils/request.js'

export const queryList = (data) => get('/provider/queryList', data)
export const changeEnableStatus = (data) => post('/provider/changeEnableStatus?id=' + data, null, null)
