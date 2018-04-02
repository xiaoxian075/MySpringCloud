/**
 * 远端请求统一接口
 */

//导入模块
import axios from 'axios'
import qs from "qs";
import {Message} from 'element-ui'


const BASE_URL = './api';
const ADMIN_URL = '/admin';
const RESOURCE_URL = '/admin';
// const ADMIN_URL = '';
// const RESOURCE_URL = '';

export const UPLOAD_FILE = BASE_URL + RESOURCE_URL + '/file/upload';
export const UPLOAD_FILES = BASE_URL + RESOURCE_URL + '/file/filesUpload';

// 登入登出
export const LOGIN = BASE_URL + ADMIN_URL + '/mn/login';
export const LOGOUT = BASE_URL + ADMIN_URL + '/mn/logout';

export const RICH_SELECT = BASE_URL + ADMIN_URL + '/rich/selectOne';
export const RICH_SUBMIT = BASE_URL + ADMIN_URL + '/rich/submit';

// 导航
export const MENU_NAV = BASE_URL + ADMIN_URL + '/mn/menuNav';

// 快递100
export const KD100_LIST = BASE_URL + ADMIN_URL + '/kd100/list';
export const KD100_ADD = BASE_URL + ADMIN_URL + '/kd100/add';
export const KD100_EDIT = BASE_URL + ADMIN_URL + '/kd100/edit';
export const KD100_DEL = BASE_URL + ADMIN_URL + '/kd100/del';
export const KD100_BATCH_DEL = BASE_URL + ADMIN_URL + '/kd100/batchDel';
export const KD100_GET_ALL = BASE_URL + ADMIN_URL + '/kd100/getAll';

// 轮播图设置
export const CAROUSEL_LIST = BASE_URL + ADMIN_URL + '/carousel/list';
export const CAROUSEL_ADD = BASE_URL + ADMIN_URL + '/carousel/add';
export const CAROUSEL_EDIT = BASE_URL + ADMIN_URL + '/carousel/edit';
export const CAROUSEL_DEL = BASE_URL + ADMIN_URL + '/carousel/del';
export const CAROUSEL_BATCH_DEL = BASE_URL + ADMIN_URL + '/carousel/batchDel';

// 云社区资讯
export const COMMUNITY_LIST = BASE_URL + ADMIN_URL + '/community/list';
export const COMMUNITY_ADD = BASE_URL + ADMIN_URL + '/community/add';
export const COMMUNITY_EDIT = BASE_URL + ADMIN_URL + '/community/edit';
export const COMMUNITY_DEL = BASE_URL + ADMIN_URL + '/community/del';
export const COMMUNITY_BATCH_DEL = BASE_URL + ADMIN_URL + '/community/batchDel';

//商品
export const COMMUDITY_LIST = BASE_URL + ADMIN_URL + '/commodity/list';
export const COMMUDITY_ADD = BASE_URL + ADMIN_URL + '/commodity/add';
export const COMMUDITY_EDIT = BASE_URL + ADMIN_URL + '/commodity/edit';
export const COMMUDITY_DEL = BASE_URL + ADMIN_URL + '/commodity/del';
export const COMMUDITY_BATCH_DEL = BASE_URL + ADMIN_URL + '/commodity/batchDel';
export const COMMUDITY_SET_STATE = BASE_URL + ADMIN_URL + '/commodity/setState';
export const COMMUDITY_ATTR_LIST = BASE_URL + ADMIN_URL + '/commodity/attrList';
export const COMMUDITY_ATTR_ADD = BASE_URL + ADMIN_URL + '/commodity/attrAdd';
export const COMMUDITY_ATTR_EDIT = BASE_URL + ADMIN_URL + '/commodity/attrEdit';
export const COMMUDITY_ATTR_DEL = BASE_URL + ADMIN_URL + '/commodity/attrDel';
export const COMMUDITY_ATTR_BATCH_DEL = BASE_URL + ADMIN_URL + '/commodity/attrBatchDel';

//地区
export const AREA_GET_CHILD = BASE_URL + ADMIN_URL + '/common/getAreaChild';

//店铺
export const SHOP_GET_ALL_SHOP = BASE_URL + ADMIN_URL + '/shop/getAllShop';
export const SHOP_LIST = BASE_URL + ADMIN_URL + '/shop/list';
export const SHOP_ADD = BASE_URL + ADMIN_URL + '/shop/add';
export const SHOP_EDIT = BASE_URL + ADMIN_URL + '/shop/edit';
export const SHOP_DEL = BASE_URL + ADMIN_URL + '/shop/del';
export const SHOP_BATCH_DEL = BASE_URL + ADMIN_URL + '/shop/batchDel';

export const SHOP_ACTIVITY_LIST = BASE_URL + ADMIN_URL + '/shopActivity/list';
export const SHOP_ACTIVITY_ADD = BASE_URL + ADMIN_URL + '/shopActivity/add';
export const SHOP_ACTIVITY_EDIT = BASE_URL + ADMIN_URL + '/shopActivity/edit';
export const SHOP_ACTIVITY_DEL = BASE_URL + ADMIN_URL + '/shopActivity/del';
export const SHOP_ACTIVITY_BATCH_DEL = BASE_URL + ADMIN_URL + '/shopActivity/batchDel';

// 运费地区设置
export const EXPENSES_LIST = BASE_URL + ADMIN_URL + '/expenses/list';
export const EXPENSES_ADD = BASE_URL + ADMIN_URL + '/expenses/add';
export const EXPENSES_EDIT = BASE_URL + ADMIN_URL + '/expenses/edit';
export const EXPENSES_DEL = BASE_URL + ADMIN_URL + '/expenses/del';
export const EXPENSES_BATCH_DEL = BASE_URL + ADMIN_URL + '/expenses/batchDel';

export const ACCOUNT_LIST = BASE_URL + ADMIN_URL + '/account/list';
export const ACCOUNT_STATE = BASE_URL + ADMIN_URL + '/account/state';

// 订单
export const ORDER_LIST = BASE_URL + ADMIN_URL + '/order/list';
export const ORDER_SEND_GOODS = BASE_URL + ADMIN_URL + '/order/sendGoods';

// 消息
export const MESSAGE_LIST = BASE_URL + ADMIN_URL + '/message/list';
export const MESSAGE_ADD = BASE_URL + ADMIN_URL + '/message/add';
export const MESSAGE_PUSH = BASE_URL + ADMIN_URL + '/message/push';
export const MESSAGE_DEL = BASE_URL + ADMIN_URL + '/message/del';
export const MESSAGE_BATCH_DEL = BASE_URL + ADMIN_URL + '/message/batchDel';

// 提醒发货
export const REMIND_LIST = BASE_URL + ADMIN_URL + '/remind/list';
export const REMIND_IGNORE = BASE_URL + ADMIN_URL + '/remind/ignore';


// 支付记录
export const PAY_RECORD_LIST = BASE_URL + ADMIN_URL + '/payRecord/list';

export const VERSION_GETALL = BASE_URL + ADMIN_URL + '/version/getAll';
export const VERSION_EDIT = BASE_URL + ADMIN_URL + '/version/edit';


export function httpPost(url, paramObj) {
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
  let sessionId = window.localStorage.getItem('sessionId');
  //alert(sessionId);
  if (sessionId == undefined) {
    sessionId = '';
  }

  let info = JSON.stringify(paramObj);
  let data1 = {
    sessionId: sessionId,
    requestId: '',
    sign: '',
    info: info
  };
  let data2 = JSON.stringify(data1);
  let param = qs.stringify({data: data2});
  return new Promise((resolve, reject) => {
    axios.post(url, param).then(res => {
      var data = res.data;
      //var data = JSON.parse(res.data);

      if (data.code === 0) {
        let resInfo = '';
        if (data.info == '' || data.info == undefined || data.info == null) {
          resInfo = '';
        } else {
          resInfo = JSON.parse(data.info);
        }
        resolve({data: resInfo});
      } else if (data.code === 2) {

        //setUserInfo(null);
        window.localStorage.clear();
        Message.error('请重新登入');
        // router.replace({name: "login"});
      } else {
        reject({code: data.code, msg: data.msg});
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

