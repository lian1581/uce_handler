[![Release](https://jitpack.io/v/syomie/uce_handler.svg)](https://jitpack.io/#syomie/uce_handler) [![](https://jitpack.io/v/syomie/uce_handler/month.svg)](https://jitpack.io/#syomie/uce_handler) [![](https://jitpack.io/v/syomie/uce_handler/week.svg)](https://jitpack.io/#syomie/uce_handler)
**因墙加厚，后续更新(如果有需要的话)将放在[gitee/uce_handler](https://gitee.com/syomie/uce_handler)
## 入门
将此库添加到您的Android项目并在Application类中对其进行初始化. 此外,您可以添加电子邮件地址和其他方式用于反馈(见下)。
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

### 构造时的可选设置
##### .setUCEHEnabled(true/false)
//  默认 'true'
 =>  启用/禁用 UCE_Handler.
##### .setEmailAddresses("abc@qql.com, aaa@dd.com,..")
// 默认 - 空
 =>  添加用,分隔的电子邮件地址，这些电子邮件地址将接收反馈邮件。
##### .setOtherInfo(name,uriString)
// 默认-空，为空时不显示该项
 =>  自定义一个反馈渠道。例如:setOtherInfo("反馈Q群","https://jq.qq.com/?_wv=1027&k=xxxxxxxxxxxxxx")

### [javadoc 1.2.0](https://javadoc.jitpack.io/com/github/syomie/uce_handler/1.2.0/javadoc/)

