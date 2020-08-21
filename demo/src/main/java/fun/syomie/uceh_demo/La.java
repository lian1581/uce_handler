package fun.syomie.uceh_demo;
import android.app.Application;
import fun.syomie.uce_handler.UCEHandler;

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
