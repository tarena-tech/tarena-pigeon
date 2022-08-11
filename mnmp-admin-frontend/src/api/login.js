import { post } from '@/utils/request';

export const login = (data) => post('/passport/login', data, { baseURL: process.env.VUE_APP_SSO_API })
