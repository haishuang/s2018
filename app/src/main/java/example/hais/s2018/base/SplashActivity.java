package example.hais.s2018.base;

import android.os.Bundle;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.hais.libraryview.widget.CountDownProgressView;
import example.hais.s2018.MainActivity;
import example.hais.s2018.R;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.progressView)
    example.hais.libraryview.widget.CountDownProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressView.start();
        progressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (progress == 0) {
                    toMain();
                }
            }
        });
    }

    @OnClick(R.id.progressView)
    public void onClick() {
        progressView.stop();
        toMain();
    }

    private void toMain() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.show(activity);
                        finish();
                    }
                });
            }
        }).start();
    }
}
