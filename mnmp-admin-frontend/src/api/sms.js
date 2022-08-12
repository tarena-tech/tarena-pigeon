import {
  get, post
} from '@/utils/request.js'

export const queryListByPage = (data) => get('/template/sms/query/page', data)
export const changeEnableStatus = (params) => post('/template/sms/change/enable/status?id=' + params, null, null)
export const audit = (data) => post('/template/sms/doAudit', data, null)
export const save = (data) => post('/template/sms/save', data, null)
export const querySmsTemplateList = (params) => get('/template/sms/query/list', params, null)
