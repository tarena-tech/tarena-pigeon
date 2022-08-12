import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/admin/sign/queryPage', data)
export const changeEnable = (params) => post('/admin/sign/change/enable/status?id=' + params, null, null)
export const audit = (data) => post('/admin/sign/audit', data, null)
export const save = (data) => post('/admin/sign/save', data, null)
export const querySignList = (params) => get('/admin/sign/queryList', params, null)
