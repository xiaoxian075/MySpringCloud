import fetch from 'common/fetch'
//import * as baseurl from './baseurl'

// //登录
// export function login(data) {
//   let url = baseurl.BASE_URL + '/mn/login';
//   return fetch(url, data);
// }
// //登出
// export function logout() {
//   let url = baseurl.BASE_URL + '/mn/logout';
//   return fetch(url, null);
// }

export const BASE_URL = './api';

// 导航
export const MENU_NAV = BASE_URL + '/mn/menuNav';
// // 导航
// export function menuNav() {
//   let url = baseurl.BASE_URL + '/mn/menuNav';
//   return fetch(url, null);
// }

// 快递100列表查询
export function kd100List(data) {
  let url = baseurl.BASE_URL + '/kd100/list';
  return fetch(url, data);
}

