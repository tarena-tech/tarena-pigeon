import {
  get
} from '@/utils/request.js'

export const queryListByPage = (data) => get('/template/sms/queryListByPage', data)
