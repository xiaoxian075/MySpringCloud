import fetch from 'common/fetch'
import * as baseurl from './baseurl'

//登录
export function login(data) {
  let url = baseurl.BASE_URL + '/mn/login';
  return fetch(url, data);
}
//登出
export function logout() {
  let url = baseurl.BASE_URL + '/mn/logout';
  return fetch(url, null);
}
