import {
  get,post
} from '@/utils/request.js'

export const queryList = (data) => get('/app/queryList', data)
export const changeEnable = (param) => post('/app/changeEnableStatus?id=' + param, null, null)
export const audit = (param) => post('/app/audit?id' + param.id + '&auditStatus=' + param.audit, null, null)
