import {
  get, post
} from '@/utils/request.js'

export const queryList = (data) => get('/task/queryList', data)
export const changeStatus = (data) => post('/task/change/task/status?id=' + data, null, null)
export const audit = (data) => post('task/audit', data, null, null)
