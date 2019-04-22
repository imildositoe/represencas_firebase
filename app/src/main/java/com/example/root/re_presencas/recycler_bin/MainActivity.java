package com.example.root.re_presencas.recycler_bin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.databinding.ContentMainBinding;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.example.root.re_presencas.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ContentMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.content_main);

        //this.nextAction();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                revealButton();
                fadeOutProgressDialog();
                delayedAuth();

            }
        }, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealButton() {
        mainBinding.revealViewMain.setVisibility(View.VISIBLE);

        int x = mainBinding.revealViewMain.getWidth();
        int y = mainBinding.revealViewMain.getHeight();
        float radius = Math.max(x, y) * 1.2f;

        Animator reveal = ViewAnimationUtils.createCircularReveal(mainBinding.revealViewMain, 30, 30,
                getFinalWidth(), radius);
        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
        reveal.start();
    }

    private void fadeOutProgressDialog() {
        mainBinding.progressBarMain.animate().alpha(0f).setDuration(200).start();
    }

    private void delayedAuth() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 0);
    }

    private int getFinalWidth() {
        return (int) getResources().getDimension(R.dimen.get_width);
    }
}
