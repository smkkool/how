package minhpvn.com.swipedemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smkko on 12/31/2017.
 */

public class SlideActivity extends AppCompatActivity {
    @BindView(R.id.ln1)
    LinearLayout ln1;
    @BindView(R.id.ln2)
    LinearLayout ln2;
    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.txt2)
    TextView txt2;
    @BindView(R.id.txt3)
    TextView txt3;
    @BindView(R.id.txt4)
    TextView txt4;
    @BindView(R.id.txt5)
    TextView txt5;
    @BindView(R.id.txt6)
    TextView txt6;
    Animation uptodown, downtoup;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.welcome_layout);
        // making notification bar transparent
        changeStatusBarColor();
        ButterKnife.bind(this);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        ln1.setAnimation(uptodown);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ln2.setVisibility(View.VISIBLE);
                ln2.setAnimation(downtoup);
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (savedInstanceState != null) {

                }
                Intent intent = new Intent(SlideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }

    @Override
    public void onBackPressed() {
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
