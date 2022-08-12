import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/admin/task/query/page', data)
export const changeStatus = (data) => post('/admin/task/change/task/status?id=' + data, null, null)
export const audit = (data) => post('/admin/task/audit', data, null, null)
export const addTask = (data) => post('/admin/task/add', data, null, null)
export const downExcel = (params) => get('/admin/task/excel', params, null)
export const detail = (params) => get('/admin/task/detail', params, null)
