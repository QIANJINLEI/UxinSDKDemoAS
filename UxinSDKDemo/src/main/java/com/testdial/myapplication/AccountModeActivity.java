package com.testdial.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.testdial.myapplication.utils.DialogUtil;
import com.yx.api.USDKCommonManager;
import com.yx.common.net.interfaces.IUSDKHttpCallback;
import com.yx.login.USDKIAppLoginCallback;
import com.yx.test.USDKTest;
import com.yx.test.USDKTestResultGetTokenBean;

import org.json.JSONObject;

/**
 * 账号模式
 *
 * @author wytiger
 * @date 2016-4-15
 */
public class AccountModeActivity extends Activity {
    protected static final String TAG = AccountModeActivity.class.getSimpleName();
    private Button btn_login;
    private Button btn_call_voice;
    private Button btn_call_video;
    private Button btn_test;
    private Button btn_logout;
    private Button btn_handleBrandContact;

    private String callerPhone;
    private EditText ed_callerPhone;
    private EditText ed_calledPhone;

    private EditText edMaxTime;
    private EditText edCallType;
    private EditText edCalledNickName;

    private CheckBox needRecord;

    private Context context;

    DialogUtil dialog = null;
    String json;
    private SharedPreferences phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_mode);
        context = this;
        // 初始化有信颁发的第三方app－ key
        // 在更新有信app key时也需要进行此项设置

        dialog = new DialogUtil(this);
        phone = getSharedPreferences(Utils.CALL_PHONE_NUMBER, MODE_PRIVATE);
        initView();
    }

    private void initView() {
/*		*//**
         * btn_call_phone 拨打电话~~~
         */
        /**
         * login
         */
        btn_login = (Button) findViewById(R.id.btn_login);

        /**主教电话号码~*/
        ed_callerPhone = (EditText) findViewById(R.id.ed_callerPhone);
        String string = phone.getString(Utils.PHONE_NUMBER, "");
        if (!TextUtils.isEmpty(string)) {
            ed_callerPhone.setText(string);
            loginTest();
        }
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                /************************************************* 这部分是用例代码 ****************************************/
                loginTest();
                /************************************************* 这部分是用例代码 ****************************************/

            }

        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loginTest() {
        callerPhone = ed_callerPhone.getText().toString();
        if (TextUtils.isEmpty(callerPhone)) {
            Toast.makeText(context, "我的号码不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        dialog.showDialog();
        String acountID = callerPhone;
        USDKTest.getToken(AccountModeActivity.this, callerPhone, acountID, new IUSDKHttpCallback() {
            @Override
            public void onSuccess(Object result) {
                final String json = (String) result;
                try {

                    Log.i(TAG, "json=" + json);
                    String token = "";

                    USDKTestResultGetTokenBean bean = new USDKTestResultGetTokenBean(new JSONObject(json));
                    token = bean.getInfo().getToken();
                    if (TextUtils.isEmpty(token)) {
                        btn_login.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog.closeDialog();
                                Toast.makeText(AccountModeActivity.this, "获取token失败 json=" + json, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    USDKCommonManager.loginUxinSDK(context, token, callerPhone, new USDKIAppLoginCallback() {

                        @Override
                        public void onSuccess(final Object result) {
                            btn_login.post(new Runnable() {

                                @Override
                                public void run() {
                                    dialog.closeDialog();
                                    String data = (String) result;
                                    Toast.makeText(AccountModeActivity.this, "登录成功" + data, Toast.LENGTH_LONG).show();
                                    phone.edit().putString(Utils.PHONE_NUMBER, ed_callerPhone.getText().toString()).commit();
                                    Intent intent = new Intent(AccountModeActivity.this, CallPhone.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailed(final Object result) {
                            btn_login.post(new Runnable() {

                                @Override
                                public void run() {
                                    dialog.closeDialog();
                                    String data = (String) result;

                                    Toast.makeText(AccountModeActivity.this, "登录失败" + data, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    btn_login.post(new Runnable() {
                        @Override
                        public void run() {
                            dialog.closeDialog();

                            Toast.makeText(AccountModeActivity.this, "登录失败 json=" + json, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }

            @Override
            public void onFailed(final Object result) {
                System.out.println("onFailed result =" + result);
                btn_login.post(new Runnable() {

                    @Override
                    public void run() {
                        dialog.closeDialog();
                        Toast.makeText(AccountModeActivity.this, "获取token失败：" + result, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

}
