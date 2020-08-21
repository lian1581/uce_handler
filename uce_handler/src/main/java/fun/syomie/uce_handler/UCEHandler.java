/*
 * 异常处理
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
package fun.syomie.uce_handler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class UCEHandler implements Thread.UncaughtExceptionHandler
{   

    /** 系统默认UncaughtExceptionHandler */
    private static Thread.UncaughtExceptionHandler mDefaultHandler;

    private static Context mContext;
    private static boolean isUCEHEnabled = true;
    private static String[] emailAddresses;
    private static UCEHandler mInstance;

    private static final String TAG="UCEHandler";

    private UCEHandler()
    {
    }

    private static UCEHandler getInstance(UCEHandler.Builder builder)
    {
        if (null == mInstance) {
            mInstance = new UCEHandler();
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            isUCEHEnabled = builder.isUCEHEnabled;
            emailAddresses = builder.emailAddresses;
            mContext = builder.context;
            if (isUCEHEnabled) {
                Log.i(TAG, "Enabled.");
                Thread.setDefaultUncaughtExceptionHandler(mInstance);
            } else {
                Log.i(TAG, "Disabled.");
            }
        }
        return mInstance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        if (!isUCEHEnabled && mDefaultHandler != null) {
            // 交给系统处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            handleException(ex);   
        }
    }

    /**
     * 处理异常
     * @return 处理了返回true, 否则false
     */
    private boolean handleException(Throwable ex)
    {
        if (ex == null) {
            return false;
        }

        // 获取崩溃信息
        PrintWriter pw = null;
        Writer writer = null;
        try {
            writer = new StringWriter();
            pw = new PrintWriter(writer);
            ex.printStackTrace(pw);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != pw) {
                pw.close();
            }
        }
        String error = writer.toString();

		// 获取设备信息
        Spanned devInfor=CrashInforCollect.getCollectResults(mContext);

        Bundle ie=new Bundle();
        ie.putStringArray("eMail", emailAddresses);
        ie.putString("feedback", devInfor + error);

        // 跳转崩溃界面
        Intent intent = new Intent(mContext, LibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(ie);
        mContext.startActivity(intent);
        return true;
    }

    /** 用于构造UCEHandler */
    public static class Builder
    {
        private Context context;
        private boolean isUCEHEnabled = true;
        private String[] emailAddresses;

        public Builder(Context context)
        {
            this.context = context;
        }
        /** 设置崩溃后是否由ucehandler处理 */
        public Builder setUCEHEnabled(boolean isUCEHEnabled)
        {
            this.isUCEHEnabled = isUCEHEnabled;
            return this;
        }

        /** 设置反馈邮件收件人，多地址以半角","隔开 */
        public Builder setEmailAddresses(String emailAddresses)
        {
            this.emailAddresses = (emailAddresses != null) ? emailAddresses.split(","): new String[]{""};
            return this;
        }
		
		/** 构建并应用崩溃处理 */
        public UCEHandler build()
        {
            return UCEHandler.getInstance(this);
        }
    }
}
