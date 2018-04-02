package cn.jpush.api;


import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import org.slf4j.Logger;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PushMgr {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    protected static final String APP_KEY ="3c9ee062a90e0889f8f6e5c9";
//    protected static final String MASTER_SECRET = "c24ce91d939da69233ee2f2d";

    private PushMgr() {
    }

    private static class PushMgrFactory {
        private static PushMgr instance = new PushMgr();
    }

    public static PushMgr getInstance() {
        return PushMgrFactory.instance;
    }

    private String appKey;
    private String masterSecret;
    private boolean isProduct;

    public void init(String appKey, String masterSecret, boolean isProduct) {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        this.isProduct = isProduct;
    }

    public PushResult pushAll(String data,long id) {
        PushResult result = null;
        try {
            JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
//            PushPayload payload = PushPayload.alertAll(data);
            Map<String, String> extras = new HashMap<String, String>();
            // 添加附加信息
            extras.put("id",String.valueOf(id));
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.all())//推送平台设置
                    .setAudience(Audience.all())//推送设备指定
                    .setOptions(Options.newBuilder().setApnsProduction(isProduct).build())
                    //.setMessage(Message.newBuilder().setTitle("消息1").setMsgContent("详细内容").build())
//                    .setNotification(Notification.alert(data))//通知内容体。是被推送到客户端的内容。与 message 一起二者必须有其一，可以二者并存
                    .setNotification(
                            Notification
                                    .newBuilder()
                                    .setAlert(data)
                                    .addPlatformNotification(
                                            AndroidNotification.newBuilder().addExtras(extras).build())
                                    .addPlatformNotification(
                                            IosNotification.newBuilder().addExtras(extras).build())
                                    .build())
                    .build();
            result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("JIGONG PushMgr Connection error, should retry later", e);
            return null;
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            //logger.error("Should review the error, and fix the request", e);
            logger.info("JIGONG PushMgr HTTP Status: " + e.getStatus() + ", Code:" + e.getErrorCode() + ", Message:" + e.getErrorMessage());
            return null;
        }
        return result;
    }
}
