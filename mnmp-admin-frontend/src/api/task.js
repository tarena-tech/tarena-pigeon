import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/task/query/page', data)
export const changeStatus = (data) => post('/task/change/task/status?id=' + data, null, null)
export const audit = (data) => post('/task/audit', data, null, null)
export const addTask = (data) => post('/task/add', data, null, null)
export const downExcel = (params, config) => get('/task/excel', params, config)
export const detail = (params) => get('/task/detail', params, null)
