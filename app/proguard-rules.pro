# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Mauricio.AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Retrofit stuff
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-ignorewarnings

 -keep class * {
     public private *;
 }

 -dontwarn okio.**
 -dontwarn retrofit2.Platform$Java8

 -keep class **$$ViewInjector { *; }

 -keepattributes *Annotation*,EnclosingMethod,Signature
 -keepnames class com.fasterxml.jackson.** { *; }
 -dontwarn com.fasterxml.jackson.databind.**
 -keep class org.codehaus.** { *; }
  -keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
  public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *; }


 -dontwarn com.squareup.okhttp.**

 # OkHttp3
 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class okhttp3.** { *; }
 -keep interface okhttp3.** { *; }
 -dontwarn okhttp3.**

 # Rxjava-promises

 -keep class com.darylteo.rx.** { *; }

 -dontwarn com.darylteo.rx.**

 # RxJava 0.21

 -keep class rx.schedulers.Schedulers {
     public static <methods>;
 }
 -keep class rx.schedulers.ImmediateScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.TestScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.Schedulers {
     public static ** test();
 }
 -keep class com.google.**
 -dontwarn com.google.**
 -keep public class com.google.common.**
 -keep public class * extends android.app.Activity

 -dontwarn sun.misc.**