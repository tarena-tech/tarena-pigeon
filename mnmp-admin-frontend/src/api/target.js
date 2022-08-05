import {
  get, post
} from '@/utils/request.js'

export const queryPage = (data) => get('/task/target/query/page', data)
