package com.example.falling.leyi.ui;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;

        import com.example.falling.leyi.MainActivity;
        import com.example.falling.leyi.R;

/**
 * Created by falling on 2015/11/16.
 */
public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟一秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,
                        MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);

    }
}
