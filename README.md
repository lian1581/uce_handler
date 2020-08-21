[![Release](https://jitpack.io/v/syomie/uce_handler.svg)](https://jitpack.io/#syomie/uce_handler) [![](https://jitpack.io/v/syomie/uce_handler/month.svg)](https://jitpack.io/#syomie/uce_handler) [![](https://jitpack.io/v/syomie/uce_handler/week.svg)](https://jitpack.io/#syomie/uce_handler)
## 入门
将此库添加到您的Android项目并在Application类中对其进行初始化. 此外,您可以添加开发人员的电子邮件地址，这些地址将获取崩溃日志的电子邮件以及附加的崩溃信息。
##  在您项目的根build.gradle文件的适当位置加入jitpack库:
```
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
## 添加依赖项
    dependencies {
            implementation 'com.github.syomie.uce_handler:uce_handler:1.1' // 或者使用下方的最新版本
    }
**最新版本号:**
[![](https://jitpack.io/v/syomie/uce_handler.svg)](https://jitpack.io/#syomie/uce_handler)

在您的Application类中:
* 使用构造器初始化。
    
        public class MyApplication extends Application {
        @Override public void onCreate() { 
            ...
            /* Initialize */
            new UCEHandler.Builder(this).build();
        } }

* 对于仍在使用Eclipse + ADT的，需要在App清单中手动添加一个Activity. 
    ```
    <application>
        <activity
        android:name="fun.syomie.uce_handler.LibraryActivity"/>
    </application>
    ```
**注意在AndroidManifest.xml文件application标签中指向你的application类**

### 构造时的可选参数
##### .setUCEHEnabled(true/false)
//  默认 'true'
 =>  启用/禁用 UCE_Handler.
##### .setEmailAddresses("abc@qql.com, aaa@dd.com,..")
// 默认 - 空
 =>  添加用,分隔的电子邮件地址，这些电子邮件地址将接收反馈邮件。
*****
