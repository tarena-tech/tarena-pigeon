import {
  get, post
} from '@/utils/request.js'

export const queryListByPage = (data) => get('/template/sms/queryListByPage', data)
export const changeEnableStatus = (data) => post('/template/changeEnableStatus?id='+ data, null, null)
export const audit = (data) => post('', data, null)
