import {
  get
} from '@/utils/request.js'

export const queryList = (data) => get('/app/queryList', data)
