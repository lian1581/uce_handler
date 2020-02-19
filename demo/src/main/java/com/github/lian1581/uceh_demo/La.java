package com.github.lian1581.uceh_demo;
import android.app.Application;
import android.util.Log;
import com.github.lian1581.uce_handler.UCEHandler;

public class La extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
		/** init */
        new UCEHandler.Builder(getBaseContext())
        .build();

    }
}
