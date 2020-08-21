[![Release](https://jitpack.io/v/lian1581/uce_handler.svg)](https://jitpack.io/#lian1581/uce_handler) [![](https://jitpack.io/v/lian1581/uce_handler/month.svg)](https://jitpack.io/#lian1581/uce_handler) [![](https://jitpack.io/v/lian1581/uce_handler/week.svg)](https://jitpack.io/#lian1581/uce_handler)
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
            implementation 'com.github.lian1581.uce_handler:uce_handler:1.+'
    }
**JitPack最新Tag:**
[![](https://jitpack.io/v/lian1581/uce_handler.svg)](https://jitpack.io/#lian1581/uce_handler)

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
        android:name="com.github.lian1581.uce_handler.LibraryActivity"/>
    </application>
    ```
### 构造时的可选参数
##### .setUCEHEnabled(true/false)
//  默认 'true'
 =>  启用/禁用 UCE_Handler.
##### .setEmailAddresses("abc@qql.com, aaa@dd.com,..")
// 默认 - 空
 =>  添加用,分隔的电子邮件地址，这些电子邮件地址将接收反馈邮件。
*****
## 
用了段时间[RohitSurwase/UCE-Handler](https://github.com/RohitSurwase/UCE-Handler)**(推荐)**，决定参考它造个扳手玩……
别人的轮子很好使，但自己的扳手更顺心
