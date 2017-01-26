package com.testdial.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.api.USDKCommonManager;
import com.yx.api.dial.USDKIDialParam;
import com.yx.api.dial.USDKIDialStateCallbackDefaultImpl;

public class CallPhone extends Activity {

    private Button btn_call_voice;
    private EditText ed_calledPhone;
    private EditText edCalledNickName;
    private USDKIDialParam param;
    private Context context = null;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_call_phone);
        TextView tv_number = (TextView) findViewById(R.id.tv_number);
        tv_number.setText("当前手机号：" + getSharedPreferences(Utils.CALL_PHONE_NUMBER, MODE_PRIVATE).getString(Utils.PHONE_NUMBER, "18914010426"));
        findViewById(R.id.btn_change_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /**
         * btn_call_phone 拨打电话~~~
         */
        btn_call_voice = (Button) findViewById(R.id.btn_call_phone);
        /**
         * ed_calledPhone  背胶号码~~
         */
        ed_calledPhone = (EditText) findViewById(R.id.ed_calledPhone);
        ed_calledPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                EditText editText = (EditText) view;
                Drawable compoundDrawables = (editText).getCompoundDrawables()[2];
                if(compoundDrawables==null&&event.getAction() != MotionEvent.ACTION_UP && (event.getX()< editText.getWidth() - editText.getPaddingRight()
                        - compoundDrawables.getIntrinsicWidth())){
                    return false;
                }else{
                           Toast.makeText(context,"我被点击了！"+(event.getX()< editText.getWidth() - editText.getPaddingRight()
                            - compoundDrawables.getIntrinsicWidth()),Toast.LENGTH_SHORT).show();
                    return true;
                }



                /*if ()
                    return false;

            *//*    if ( ) {
                    //进入这表示图片被选中，可以处理相应的逻辑了
                }*/
                //return false;

            }
        });

        edCalledNickName = (EditText) findViewById(R.id.edCalledNickName);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_call_voice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialVoicePhone();
            }
        });
        /**退出*/
        btn_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(1);
            }
        });
    }

    private void clickImageView(EditText et) {
        Spanned s = et.getText();//得到Spanned对象

        ImageSpan[] imagespans = s.getSpans(0, s.length(), ImageSpan.class); //得到该EditText中多有的ImageSpan对象

        int selectStart = et.getSelectionStart(); //获得当前EditText中的光标位置

//遍历所有的ImageSpan 根据光标位置判断点击的是哪一个ImageSpan
        for (ImageSpan span : imagespans) {

            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            Log.i("info", "start:" + start + ",end:" + end);
            if (selectStart >= start && selectStart <= end) {
                Toast.makeText(context, "点击了图片",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    // 拨打语音
    private void dialVoicePhone() {

        String calledPhone = ed_calledPhone.getText().toString();
        //  boolean isNeedRecord = needRecord.isChecked();

        long maxTime = 0;
        try {
            maxTime = Long.parseLong("100000");
        } catch (Exception e) {
            Toast.makeText(context, "e=" + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        // int callType = Integer.parseInt(edCallType.getText().toString());

        param = new USDKIDialParam();
        param.setCalledPhone(calledPhone);
        param.setNeedRecord(false);
        param.setDialType(2);    // 1.8.1开始不需要设置了
        param.setLimitTalkTime(maxTime);

        /******************************************************************/
        // 被叫号的昵称，用于在通话页面显示被叫昵称， 如果第三方APP没有这个需求则不理会
        String calledNickName = edCalledNickName.getText().toString();
        if (!TextUtils.isEmpty(calledNickName)) {
            param.setCalledNickName(calledNickName);
        }
        /******************************************************************/


        USDKCommonManager.dialPhoneByUxinSDK(context, param, new USDKIDialStateCallbackDefaultImpl() {

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

            @Override
            public void onFail(final int code, final String msg) {
                Toast.makeText(context, "拨打失败 code=" + code + "  msg=" + msg, Toast.LENGTH_LONG).show();
            }
        });

    }

}
