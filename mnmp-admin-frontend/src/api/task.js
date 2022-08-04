import {
  get, post
} from '@/utils/request.js'

export const queryList = (data) => get('/task/queryList', data)
export const changeStatus = (data) => post('/task/change/task/status?id=' + data, null, null)
export const audit = (data) => post('task/audit', data, null, null)
export const save = (data) => post('task/save', data, null, null)
export const downExcel = (params) => get('/task/excel', params, null)
export const detail = (params) =>get('/task/detail', params, null);
