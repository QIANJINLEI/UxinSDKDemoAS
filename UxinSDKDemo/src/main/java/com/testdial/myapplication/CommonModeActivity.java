package com.testdial.myapplication;

import com.testdial.myapplication.test.USDKTestActivity;
import com.yx.api.USDKCommonManager;
import com.yx.api.dial.USDKIDialParam;
import com.yx.api.dial.USDKIDialStateCallback;
import com.yx.api.dial.USDKIDialStateCallbackDefaultImpl;
import com.yx.network.tcp.USDKTcpProxy;
import com.yx.test.USDKTest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CommonModeActivity extends Activity {

	protected static final String TAG = CommonModeActivity.class.getSimpleName();
	private Button btn_login;
	private Button btn_call;
	private Button btn_test;
	private Button btn_logout;

	private String callerPhone;
	private EditText ed_callerPhone;
	private EditText ed_calledPhone;

	private EditText edMaxTime;
	private EditText edCallType;

	private CheckBox needRecord;

	private Context context = this;

	long count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_mode);
		// 初始化有信颁发的第三方app－ key
		// 在更新有信app key时也需要进行此项设置

		// Context 必须是activity
		USDKCommonManager.startUSDK(context, USDKCommonManager.USDKMODE_MSG_IDENT);
		initView();

	}

	private void initView() {
		btn_call = (Button) findViewById(R.id.btn_call_phone);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_test = (Button) findViewById(R.id.btn_test);
		btn_logout = (Button) findViewById(R.id.btn_logout);

		ed_callerPhone = (EditText) findViewById(R.id.ed_callerPhone);
		ed_calledPhone = (EditText) findViewById(R.id.ed_calledPhone);

		edMaxTime = (EditText) findViewById(R.id.edMaxTime);
		edMaxTime.setText("" + Short.MAX_VALUE);
		edCallType = (EditText) findViewById(R.id.edCallType);

		needRecord = (CheckBox) findViewById(R.id.needRecord);

		btn_call.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				boolean isLogin = USDKCommonManager.isUxinClientIsLogin(CommonModeActivity.this);
//				if (!isLogin) {
//					Toast.makeText(CommonModeActivity.this, "账号未登录", Toast.LENGTH_SHORT).show();
//					// 跳转登录页面
//					String callerPhone = ed_callerPhone.getText().toString();
//					try {
//						USDKCommonManager.startLoginByUxinSDK(CommonModeActivity.this, callerPhone);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					return;
//				} else {
//					dial();
//				}

				dial();
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转登录页面
				try {
					String callerPhone = ed_callerPhone.getText().toString();
					USDKCommonManager.startLoginByUxinSDK(CommonModeActivity.this, callerPhone);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btn_test.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, USDKTestActivity.class);
				startActivity(intent);
			}
		});
		btn_logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(context, "退出UxinSDK", Toast.LENGTH_LONG).show();
				USDKCommonManager.logoutUxinSDK(context);
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		String caller = USDKTest.getCallerPhone(context);
		ed_callerPhone.setText(caller);
	}

	private void dial() {
		String calledPhone = ed_calledPhone.getText().toString();
		boolean isNeedRecord = needRecord.isChecked();

		long maxTime = 0;
		try {
			maxTime = Long.parseLong(edMaxTime.getText().toString());
		} catch (Exception e) {
			Toast.makeText(context, "e=" + e.getMessage(), Toast.LENGTH_LONG).show();
			return;
		}

		int callType = Integer.parseInt(edCallType.getText().toString());

		USDKIDialParam param = new USDKIDialParam();
		param.setCalledPhone(calledPhone);
		param.setNeedRecord(isNeedRecord);
		param.setDialType(callType);
		param.setLimitTalkTime(maxTime);

		// 通过有信SDK拨打电话
		USDKCommonManager.dialPhoneByUxinSDK(context, param, new USDKIDialStateCallbackDefaultImpl() {
			@Override
			public void onFail(final int code, final String msg) {
				Toast.makeText(context, "拨打失败 code=" + code + "  msg=" + msg, Toast.LENGTH_LONG).show();
			}
			@Override
			public void onConnecting(int code, String data) {
				Toast.makeText(context, "正在接通 code=" + code + "  data=" + data, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onRing(int code, String data) {
				Toast.makeText(context, "对方响铃 code=" + code + "  data=" + data, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onTalking(int code, String data) {
				Toast.makeText(context, "对方接通 code=" + code + "  data=" + data, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onHangUp(final int code, final String data) {
				Toast.makeText(context, "挂断 code=" + code + "  data=" + data, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}
