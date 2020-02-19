/*
 * 崩溃友好互交Activity
 * Copyright (C) 2020 lian1581
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.lian1581.uce_handler;

import android.content.*;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;

public class LibraryActivity extends Activity 
{
    String[] eMail;
    String feedback="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        Button btn1= (Button) findViewById(R.id.show),
            btn2= (Button) findViewById(R.id.copy),
            btn3= (Button) findViewById(R.id.send),
            btn4= (Button) findViewById(R.id.restart),
            btn5= (Button) findViewById(R.id.close);
        Intent i=getIntent();
        feedback = i.getStringExtra("feedback");
        eMail = i.getStringArrayExtra("eMail");
        btn1.setOnClickListener(new OnClick());
        btn2.setOnClickListener(new OnClick());
        btn3.setOnClickListener(new OnClick());
        btn4.setOnClickListener(new OnClick());
        btn5.setOnClickListener(new OnClick());
    }
    private class OnClick implements OnClickListener
    {

        @Override
        public void onClick(View p1)
        {
            int vid= p1.getId();
            if (vid == R.id.copy) copy();
            if (vid == R.id.send) send();
            if (vid == R.id.restart) restartApplication();
            if (vid == R.id.close) close();
            if (vid == R.id.show) showLog();
        }



        private void showLog()
        {
            // 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryActivity.this);
            // 设置Title的图标
            // builder.setIcon(R.drawable.ic_launcher);
            // 设置Title的内容
            builder.setTitle(getString(R.string.Show_Log));
            // 设置Content来显示一个信息
            builder.setMessage(feedback);
            // 设置一个PositiveButton
            builder.setPositiveButton(getString(R.string.Copy_Log), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 复制
                        copy();
                    }
                });
            // 设置一个NegativeButton
            builder.setNegativeButton(getString(R.string.Send_Log), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 发送
                        send();
                    }
                });
            // 显示出该对话框
            builder.show(); 
        }

        private void copy()
        {
            // 获取系统剪切板
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Feedback", feedback);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }

        private void restartApplication()
        {  
            final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());  
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
            startActivity(intent);

            /**杀死整个进程**/
            android.os.Process.killProcess(android.os.Process.myPid());
        }  

        private void send()
        {
            if (null == eMail || eMail.toString().isEmpty()) {
                Toast.makeText(LibraryActivity.this, getString(R.string.Contact_Fail), Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, eMail);
            intent.putExtra(Intent.EXTRA_CC, eMail); // 抄送人
            intent.putExtra(Intent.EXTRA_SUBJECT, getPackageName() + ":Crash feedback"); // 主题
            intent.putExtra(Intent.EXTRA_TEXT, feedback); // 正文
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, getString(R.string.Select_Mail_Application)));
        }

        private void close()
        {
            // 通过Context获取ActivityManager
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            // 通过ActivityManager获取任务栈
            List appTaskList = activityManager.getAppTasks();
            // 逐个关闭Activity
            while (appTaskList.size() > 0) {
                ActivityManager.AppTask tk=(ActivityManager.AppTask) appTaskList.get(0);
                tk.finishAndRemoveTask();
            }
            // 杀死整个进程
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
