import {post} from "@/utils/request";

export const login = (data) => post('/passport/login', data, null)
