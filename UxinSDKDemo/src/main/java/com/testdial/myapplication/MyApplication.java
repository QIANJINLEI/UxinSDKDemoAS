package com.testdial.myapplication;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.yx.api.USDKCommonManager;
import com.yx.api.crm.USDKCrmNoticeImplDefault;
import com.yx.api.interfaces.USDKCommonCallback;
import com.yx.common.net.interfaces.IUSDKHttpCallback;
import com.yx.crm.USDKCrmAppCallback;
import com.yx.crm.USDKCrmAppCallbackData;
import com.yx.crm.USDKCrmNoticeData;
import com.yx.test.USDKTest;
import com.yx.test.USDKTestResultGetTokenBean;

import org.json.JSONObject;

/**
 *
 */
public class MyApplication extends Application {

    public static String thirdAppId = "SDKdemo";
    // realease
	public static String thirdKey = "7a93da7ed27aa5297b19bac3a322711ce0fcc59b";

    // test
//    public static String thirdKey = "946d896a2618c4432keicl9mab45376f1329sl92";

    // debug
//	 public static String thirdKey = "7a93da7ed27aa5297b19bac3a322711ce0fcc59b";

    @Override
    public void onCreate() {
        super.onCreate();
        setUSDK();
    }

    public void setUSDK(){
        // hehe
        USDKCommonManager.build(USDKCommonManager.PUBLIC); //打印日志，第三方APP待上线的时候注释掉。
        // 注册app
        USDKCommonManager.registerApp(this, MyApplication.thirdKey, MyApplication.thirdAppId);
        // 设置有信SDK回调第三方APP事件
        USDKCommonManager.setUsdkAppCallback(new USDKCommonCallback() {
            @Override
            public void tokenOverdue(final Context context) {
                // TODO Auto-generated method stub
                System.out.println("tokenOverdue token 失效，重新获取token");

                // 开发者通过自己的方法获取token，这里是demo
                String acountId = "";
                USDKCommonManager.updateToken(context, acountId);

                /**** 这份代码是例子。。。。。 ****/
                acountId = USDKTest.getSPToken(context);
                String callerPhone = USDKTest.getCallerPhone(context);
                if (TextUtils.isEmpty(acountId) || TextUtils.isEmpty(acountId)) {
                    return;
                }
                USDKTest.getToken(context, callerPhone, acountId, new IUSDKHttpCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            String json = (String) result;
                            System.out.println("onSuccess json =" + json);
                            String token = "";

                            USDKTestResultGetTokenBean bean = new USDKTestResultGetTokenBean(new JSONObject(json));
                            token = bean.getToken();
                            USDKCommonManager.updateToken(context, token);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(Object result) {
                        System.out.println("onFailed result =" + result);
                    }
                });
                /**** 这份代码是例子。。。。。 ****/
            }
        });


        /** CRM 处理例子， 如果不关注CRM这个需求，不需要理会 注释就可以了 */
        // 设置有信CRM回调
        USDKCommonManager.setUSDKCrmNotice(new USDKCrmNoticeImplDefault() {
            @Override
            public void onCrmNotice(final USDKCrmNoticeData data, final USDKCrmAppCallback crmAppCallback) {
                System.out.println("CRM CRM收到通知");
                if (data != null && crmAppCallback != null) {
                    String callerPhone = data.getCallerPhone();
                    System.out.println("CRM happen callerPhone=" + callerPhone);
                    // 主叫号码
                    USDKTest.getCRM(getApplicationContext(), callerPhone, new IUSDKHttpCallback() {

                        @Override
                        public void onSuccess(Object result) {
                            if (result == null) {
                                System.out.println("CRM getCRM onSuccess result=" + result);
                                return;
                            }

                            String crmData = (String) result;
                            System.out.println("CRM getCRM onSuccess =" + crmData);

                            int role = data.getRole();
                            if (role == USDKCrmNoticeData.ROLE_CALLER) {
                                // 主叫时CRM
                            } else if (role == USDKCrmNoticeData.ROLE_CALLEE) {
                                // 被叫时CRM
                            }
                            USDKCrmAppCallbackData callbackData = new USDKCrmAppCallbackData();
                            callbackData.setCrmData(crmData);

                            try {
                                crmAppCallback.onCrmAppCallback(callbackData);
                            } catch (Exception e) {
                                System.out.println("CRM onCrmAppCallback e =" + e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onFailed(Object result) {
                            System.out.println("CRM getCRM onfailed =" + result);
                        }
                    });

                } else {
                    System.out.println("CRM error happen ");
                }
            }
        });
        /** CRM 处理例子， 如果不关注CRM这个需求，不需要理会 注释就可以了 */
    }
}
