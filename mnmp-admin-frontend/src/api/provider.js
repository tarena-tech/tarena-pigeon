import {
  get
} from '@/utils/request.js'

export const queryList = (data) => get('/provider/queryList', data)
