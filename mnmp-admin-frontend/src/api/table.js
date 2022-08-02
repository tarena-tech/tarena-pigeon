import {
  get
} from '@/utils/request.js'

export const getList = (data) => get('/vue-admin-template/table/list', data, { useMockServer: true })
