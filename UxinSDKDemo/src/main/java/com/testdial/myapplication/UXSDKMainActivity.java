package com.testdial.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class UXSDKMainActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uxsdkmain);
		initView();
	}

	private void initView() {
		
/*		findViewById(R.id.btnCommonMode).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UXSDKMainActivity.this, CommonModeActivity.class);
				startActivity(intent);
			}
		});*/
		findViewById(R.id.btnAcountMode).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UXSDKMainActivity.this, AccountModeActivity.class);
				startActivity(intent);
			}
		});

	}
	
	
}
