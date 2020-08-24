/*
 * 崩溃友好互交界面
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

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

public class LibraryActivity extends Activity implements OnClickListener
{
    String[] eMail, otherInfo;
	String feedback="";

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        Button showLog= findViewById(R.id.show),
            copyLog= findViewById(R.id.copy),
            sendLog= findViewById(R.id.send),
            restart= findViewById(R.id.restart),
			other= findViewById(R.id.otherButton),
            closeAct= findViewById(R.id.close);
        Intent i=getIntent();
        feedback = i.getStringExtra("feedback");
        eMail = i.getStringArrayExtra("eMail");
		if( i.hasExtra("OtherInfo") ){
            otherInfo = i.getStringArrayExtra("OtherInfo");
			other.setText(otherInfo[0]);
			other.setVisibility(View.VISIBLE);
			other.setOnClickListener(this);
		}
        showLog.setOnClickListener(this);
        copyLog.setOnClickListener(this);
        sendLog.setOnClickListener(this);
        restart.setOnClickListener(this);
        closeAct.setOnClickListener(this);
    }

	@Override
	public void onClick( View p1 ){
        int id= p1.getId();
        if( id == R.id.copy ) copy();
        if( id == R.id.send ) send();
        if( id == R.id.restart ) restartApplication();
        if( id == R.id.otherButton ) openOther(otherInfo[1]);
        if( id == R.id.close ) finish();
        if( id == R.id.show ) showLog();
	}

	private void showLog( ){
		// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
		AlertDialog.Builder builder = new AlertDialog.Builder(LibraryActivity.this);
		builder.setTitle(getString(R.string.Show_Log));
		builder.setMessage(feedback);
		builder.setPositiveButton(getString(R.string.Copy_Log),new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick( DialogInterface dialog,int which ){
					copy();
				}
			});
		builder.setNegativeButton(getString(R.string.Send_Log),new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick( DialogInterface dialog,int which ){
					send();
				}
			});
		builder.show(); 
	}

	private void copy( ){
		ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData mClipData = ClipData.newPlainText("Feedback",feedback);
		cm.setPrimaryClip(mClipData);
	}

	private void restartApplication( ){  
		final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());  
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);  
		startActivity(intent);
	}  

	void openOther( String str ){
        if( str.isEmpty() ){
            Toast.makeText(LibraryActivity.this,getString(R.string.Contact_Fail),Toast.LENGTH_LONG).show();
            return;
		}
		copy();
		Uri uri=Uri.parse(str);
		Intent intent=new Intent(Intent.ACTION_VIEW,uri);
		startActivity(intent);
        super.finish();
	}

    @Override
    public void finish( ){
        super.finish();
        ActivityManager am = (ActivityManager)getSystemService(getApplication().ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTaskList=  am.getAppTasks();
        for( ActivityManager.AppTask at:appTaskList ){
            at.finishAndRemoveTask();
        }
        System.exit(0);
    }

	private void send( ){
		if( null == eMail || eMail.toString().isEmpty() ){
			Toast.makeText(LibraryActivity.this,getString(R.string.Contact_Fail),Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL,eMail);
		intent.putExtra(Intent.EXTRA_CC,eMail); // 抄送人
		intent.putExtra(Intent.EXTRA_SUBJECT,getPackageName() + ":Crash feedback"); // 主题
		intent.putExtra(Intent.EXTRA_TEXT,feedback); // 正文
		intent.setType("message/rfc822");
		startActivity(Intent.createChooser(intent,getString(R.string.Select_Mail_Application)));
        super.finish();
	}
}
