import {
  get, post
} from '@/utils/request.js'

export const queryList = (data) => get('/sign/queryList', data)
export const changeEnable = (param) => post('/sign/change/enable/status?id=' + param, null, null)
export const audit = (data) => post('/sign/audit', data, null)
export const save = (data) => post('/sign/save', data, null)
