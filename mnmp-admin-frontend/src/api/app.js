import {
  get,post
} from '@/utils/request.js'

export const queryPage = (data) => get('/app/queryList', data)
export const changeEnable = (param) => post('/app/changeEnableStatus?id=' + param, null, null)
export const audit = (data) => post('/app/audit', data, null)
export const save = (data) => post('/app/save', data, null)
export const queryAppList = (param) => get('/app/queryList', param, null)
