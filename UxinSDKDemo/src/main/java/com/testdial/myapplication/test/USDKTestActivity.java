package com.testdial.myapplication.test;

import com.testdial.myapplication.R;
import com.testdial.myapplication.utils.ToastUtil;
import com.yx.test.USDKTest;
import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class USDKTestActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testusdk);
		
		findViewById(R.id.btnCloseTcpConnect).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeTcpConnect();
			}
		});
		
		findViewById(R.id.btnTcpState).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTcpState();
			}
		});
		findViewById(R.id.btnReconnectTcp).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reconnectTcp();
			}
		});
		findViewById(R.id.btnRC4).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RC4();
			}
		});
		findViewById(R.id.btnTokenRefresh).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tokenRefresh();
			}
		});
		findViewById(R.id.btnGetBuild).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnGetBuild();
			}
		});
		findViewById(R.id.btnRequestRecordPermission).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnRequestRecordPermission();
			}
		});

	}

	private void btnRequestRecordPermission(){
		USDKTest.requestPermission(this, Manifest.permission.RECORD_AUDIO, 1000);
	}

	private void showTcpState(){
		boolean state = USDKTest.isConnection();
		String csip = USDKTest.getCSConnectAddr();

		ToastUtil.showToast(this, "state=" + state + "  csip="+csip);
	}
	
	private void closeTcpConnect(){
		USDKTest.disTCPconnect();
		ToastUtil.showToast(this, "close");
	}
	private void reconnectTcp(){
		ToastUtil.showToast(this, "reconnectTcp");
		USDKTest.reconnectTcp();
	}
	private void tokenRefresh(){
		ToastUtil.showToast(this, "tokenRefresh");
		USDKTest.updateToken(this);
	}
	private void btnGetBuild(){
//		ToastUtil.showToast(this, "btnGetBuild");
//		System.out.println(""+USDKBuild.getBuild());
//
//		System.out.println(""+USDKHttpManager.LOGIN_URL);
//		String version = UGoManager.getInstance().pub_UGoGetVersion();
//		System.out.println("ugoVersion="+version);
//		ToastUtil.showToast(this, "ugoVersion="+version);
		
	}
	
	private void RC4(){
//		USDKTcpProxy.getInstance().reconnectTcp();
//		ToastUtil.showToast(this, "reconnectTcp");
		
//		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//		ToastUtil.showToast(this, "currentapiVersion="+currentapiVersion);
//
//
//		String key = "k1oET&Yh7@EQnp2XdTP1o/Vo=";
//
//		String data = "liuhaikang jiang lai shi dashen";
//
//		String resultEncode;
////		byte resultEncodeByte[] = USDKRC4.rc4_encode(data, key).getBytes();
////		USDKCustomLog.i("resultBytep="+resultEncodeByte);
//		resultEncode = new String(resultEncodeByte);
////		USDKCustomLog.i("resultEncode="+resultEncode);
//
//
//		String resultDecode = resultEncode;
//		byte resultDecodeByte[] = USDKRC4.rc4_encode(resultDecode, key).getBytes();
////		USDKCustomLog.i("resultDecodeByte="+resultDecodeByte);
//		resultDecode = new String(resultDecodeByte);
////		USDKCustomLog.i("resultDecode="+resultDecode);
//
//
		
//		String keyEncryptEnResult = new String(KeyEncrypt.getInstance().pub_UGoGetRc4_RC4BaseJNI(data.getBytes()));
//		USDKCustomLog.i("keyEncryptEnResult="+keyEncryptEnResult);
//		String keyEncryptDeResult = new String(KeyEncrypt.getInstance().pub_UGoGetRc4_RC4BaseJNI(keyEncryptEnResult.getBytes()));
//		USDKCustomLog.i("keyEncryptDeResult="+keyEncryptDeResult);
//		byte[] keyEncryptEnResult = KeyEncrypt.getInstance().pub_UGoGetRc4_RC4BaseJNI(data.getBytes());
//		USDKCustomLog.i("keyEncryptEnResult="+keyEncryptEnResult);
//		String keyEncryptDeResult = new String(KeyEncrypt.getInstance().pub_UGoGetRc4_RC4BaseJNI(keyEncryptEnResult));
//		USDKCustomLog.i("keyEncryptDeResult="+keyEncryptDeResult);
	}
	
}
