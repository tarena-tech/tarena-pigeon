import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/white/phone/query/page', data)
export const downExcel = (params, config) => get('/white/phone/excel', params, config)
export const deleteById = (param) => post('/white/phone/del?id=' + param.id, null, null)
export const deleteAll = (param) => post('/white/phone/del?appCode=' + param.appCode, null, null)
