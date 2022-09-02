import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/white/phone/query/page', data)
export const downExcel = (params, config) => get('/white/phone/excel', params, config)
export const save = (data) => post('/white/phone/update', data, null)
