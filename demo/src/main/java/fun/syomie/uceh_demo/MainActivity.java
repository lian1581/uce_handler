package fun.syomie.uceh_demo;

import android.app.*;
import android.os.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new Thread(new Runnable(){

                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e)
                    {

                    }
                    finally
                    {
                        throw new RuntimeException("test");   
                    }
                }
            }
        ).start();
    }
}
