import {
  get,
  post
} from '@/utils/request.js'

export const login = (data) => post('/vue-admin-template/user/login', data, { useMockServer: true })
export const getInfo = (data) => get('/vue-admin-template/user/info', data, { useMockServer: true })
export const logout = (data) => post('/vue-admin-template/user/logout', data, { useMockServer: true })
