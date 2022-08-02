import {
  get, post
} from '@/utils/request.js'

export const queryListByPage = (data) => get('/template/sms/queryListByPage', data)
export const changeEnableStatus = (data) => post(''+ data, null, null)
