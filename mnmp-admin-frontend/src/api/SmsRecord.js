import {
  post
} from '@/utils/request.js'

export const queryPage = (data) => post('/record/target/sms/query/page', data)
