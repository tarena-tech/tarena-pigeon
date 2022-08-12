import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/app/query/page', data)
export const changeEnable = (params) => post('/change/enable/status?id=' + params, null, null)
export const audit = (data) => post('/app/audit', data, null)
export const save = (data) => post('/app/save', data, null)
export const queryAppList = (params) => get('/app/query/list', params, null)
