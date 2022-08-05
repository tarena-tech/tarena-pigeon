import {
  get
} from '@/utils/request.js'

export const queryPage = (data) => get('/record/target/sms/query/page', data)
