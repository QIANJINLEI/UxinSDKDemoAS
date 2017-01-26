
############################################################################
# UxinSDK jar包提供给第三方时，编译不混淆有信sdkjar包
-dontwarn android.telephony.**
-keep class android.telephony.** { *; }
-dontwarn com.android.internal.telephony.**
-keep class com.android.internal.telephony.** { *; }
-dontwarn com.gl.softphone.**
-keep class com.gl.softphone.** { *; }
-dontwarn com.yx.**
-keep class com.yx.** { *; }
-dontwarn org.webrtc.voiceengine.**
-keep class org.webrtc.voiceengine.** { *; }
############################################################################


