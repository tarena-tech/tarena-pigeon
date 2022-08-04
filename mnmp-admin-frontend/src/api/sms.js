import {
  get, post
} from '@/utils/request.js'

export const queryListByPage = (data) => get('/template/sms/queryListByPage', data)
export const changeEnableStatus = (data) => post('/template/changeEnableStatus?id='+ data, null, null)
export const audit = (data) => post('/template/sms/doAudit', data, null)
export const save = (data) => post('/template/sms/save', data, null)
export const querySmsTemplateList = (params) => get('/template/sms/queryListByParam', params, null)
