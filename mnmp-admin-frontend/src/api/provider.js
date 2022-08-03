import {
  get, post
} from '@/utils/request.js'

export const queryList = (data) => get('/provider/queryList', data)
export const changeEnableStatus = (data) => post('/provider/changeEnableStatus?id=' + data, null, null)
export const save = (data) => post('/provider/save', data, null)
export const audit = (data) => post('/provider/audit', data, null)
