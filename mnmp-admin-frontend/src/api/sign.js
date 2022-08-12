import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/sign/queryPage', data)
export const changeEnable = (params) => post('/sign/change/enable/status?id=' + params, null, null)
export const audit = (data) => post('/sign/audit', data, null)
export const save = (data) => post('/sign/save', data, null)
export const querySignList = (params) => get('/sign/queryList', params, null)
