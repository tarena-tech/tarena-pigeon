import {
  get
} from '@/utils/request.js'

export const queryPage = (data) => get('/admin/task/target/query/page', data)
