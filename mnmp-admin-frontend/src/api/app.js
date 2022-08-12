import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/admin/app/query/page', data)
export const changeEnable = (params) => post('/app/change/enable/status?id=' + params, null, null)
export const audit = (data) => post('/admin/app/audit', data, null)
export const save = (data) => post('/admin/app/save', data, null)
export const queryAppList = (params) => get('/admin/app/query/list', params, null)
