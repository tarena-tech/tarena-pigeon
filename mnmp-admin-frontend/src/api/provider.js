import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/admin/provider/query/page', data)
export const queryProviderList = (param) => get('/admin/provider/query/list', param, null)
export const changeEnable = (data) => post('/admin/provider/change/enable/status?id=' + data, null, null)
export const save = (data) => post('/admin/provider/save', data, null)
export const audit = (data) => post('/admin/provider/audit', data, null)

