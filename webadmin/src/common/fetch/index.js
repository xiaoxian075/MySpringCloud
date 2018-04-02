/**
 * @file: index.
 * @intro: 数据请求统一封装.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 14:52.
 * @Copyright(©) 2017 by zzmhot.
 *
 */

//导入模块
import axios from 'axios'
import qs from "qs";
import {port_code} from 'common/port_uri'
import router from 'src/router'
import {Message} from 'element-ui'
import store from 'store'
import {SET_USER_INFO} from 'store/actions/type'
import {server_base_url} from 'common/config'

//设置用户信息action
const setUserInfo = function (user) {
  store.dispatch(SET_USER_INFO, user)
}

// axios.interceptors.request.use((config) => {
//   if (config.method === 'post') {
//     config.data = qs.stringify(config.data);
//   }
//   return config;
// }, (error) => {
//   return Promise.reject(error);
// });

export default function httpPost(url, paramObj) {
  if (paramObj == "" || paramObj == undefined || paramObj == null) {
    paramObj = {};
  }

  // var sid = auth.getToken();
  // if (sid != null) {
  //   params['sid'] = sid;
  // };
  // var _params = JSON.stringify(params);
  // alert(_params);

  //params = qs.stringify(params);
  let info = JSON.stringify(paramObj);
  let data1 = {
    sessionId:'',
    requestId:'',
    sign:'',
    info:info
  };
  let data2 = JSON.stringify(data1);
  let param = qs.stringify({data:data2});
  return new Promise((resolve, reject) => {
    axios.post(url, param).then(res => {
      var data = res.data;
      //var data = JSON.parse(res.data);

      if (data.code === 0) {
        let resInfo = '';
        if (data.info == '' || data.info == undefined || data.info == null) {
          resInfo = '';
        } else {
          //alert(data.info);
          resInfo = JSON.parse(data.info);
        }
        resolve({data:resInfo});
      } else if (data.code === 1001) {
        setUserInfo(null)
        router.replace({name: "login"})
      } else {
        reject({code:data.code, msg:data.msg});
      }
    }, err => {
      Message.error('操作失败！错误原因 ');
      let resError = err.response;
      let resCode = resError.status;
      let resMsg = err.message;
      Message.error('操作失败！错误原因 ' + resMsg);
      reject({code: resCode, msg: resMsg});
    }).catch((error) => {
          let resError = error.response;
          let resCode = resError.status;
          let resMsg = error.message;
          Message.error('操作失败！错误原因 ' + resMsg);
          reject({code: resCode, msg: resMsg});
    })
  })
}

// export default function fetch(options) {
//   return new Promise((resolve, reject) => {
//     // https://github.com/mzabriskie/axios
//
//     //创建一个axios实例
//     const instance = axios.create({
//       //设置默认根地址
//       baseURL: server_base_url,
//       //设置请求超时设置
//       timeout: 2000,
//       //设置请求时的header
//       headers: {
//         //'Github-url': 'https://github.com/zzmhot/vue-admin',
//         'Github-url': 'https://192.168.1.27:16531',
//         'X-Powered-By': 'zzmhot'
//       }
//     })
//     //请求处理
//     instance(options)
//       .then(({data: {code, msg, data}}) => {
//         //请求成功时,根据业务判断状态
//         if (code === port_code.success) {
//           resolve({code, msg, data})
//           return false
//         } else if (code === port_code.unlogin) {
//           setUserInfo(null)
//           router.replace({name: "login"})
//         }
//         Message.warning(msg)
//         reject({code, msg, data})
//       })
//       .catch((error) => {
//         //请求失败时,根据业务判断状态
//         if (error.response) {
//           let resError = error.response
//           let resCode = resError.status
//           let resMsg = error.message
//           Message.error('操作失败！错误原因 ' + resMsg)
//           reject({code: resCode, msg: resMsg})
//         }
//       })
//   })
// }
