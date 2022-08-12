import {
  post
} from '@/utils/request.js'

export const queryPage = (data) => post('/admin/record/target/sms/query/page', data)
