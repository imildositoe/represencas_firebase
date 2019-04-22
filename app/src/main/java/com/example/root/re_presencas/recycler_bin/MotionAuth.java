package com.example.root.re_presencas.recycler_bin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.root.re_presencas.components.Components;
import com.example.root.re_presencas.databinding.ActivityLoginBinding;
import com.google.firebase.database.DatabaseReference;

public class MotionAuth {

    private ActivityLoginBinding mainBinding;
    private FrameLayout btnSignIn;
    private TextView btnLoginHelp;
    private EditText edtUsuario;
    private EditText edtSenha;
    private Components components;
    private DatabaseReference raiz;
    public static final String PR_LOGADO = "professor_logado";
    public static final String EST_LOGADO = "estudante_logado";

    public void load(View view) {
        animateButtonWidth();
        fadeOutTextAndSetProgressDialog();
        nextAction();
    }

    private void animateButtonWidth() {
        ValueAnimator animator = ValueAnimator.ofInt(mainBinding.btnSignIn.getMeasuredWidth(), getFinalWidth());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mainBinding.btnSignIn.getLayoutParams();
                layoutParams.width = value;
                mainBinding.btnSignIn.requestLayout();
            }
        });
        animator.setDuration(250);
        animator.start();
    }

    private void fadeOutTextAndSetProgressDialog() {
        mainBinding.signInText.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showProgressDialog();
            }
        }).start();
    }


    private void showProgressDialog() {
        mainBinding.progressBar2.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"),
                PorterDuff.Mode.SRC_IN);
        mainBinding.progressBar2.setVisibility(View.VISIBLE);
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
        }, 2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealButton() {
        mainBinding.btnSignIn.setElevation(0f);
        mainBinding.revealView.setVisibility(View.VISIBLE);

        mainBinding.btnLoginHelp.setVisibility(View.INVISIBLE);
        mainBinding.tvHelpLogin1.setVisibility(View.INVISIBLE);
        mainBinding.edtSenha.setVisibility(View.INVISIBLE);
        mainBinding.edtUsuario.setVisibility(View.INVISIBLE);


        int x = mainBinding.revealView.getWidth();
        int y = mainBinding.revealView.getHeight();
        int startX = (int) (getFinalWidth() / 2 + mainBinding.btnSignIn.getX());
        int startY = (int) (getFinalWidth() / 2 + mainBinding.btnSignIn.getY());
        float radius = Math.max(x, y) * 1.2f;

        Animator reveal = ViewAnimationUtils.createCircularReveal(mainBinding.revealView, startX, startY, getFinalWidth(), radius);
        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        reveal.start();
    }

    private void fadeOutProgressDialog() {
        mainBinding.progressBar2.animate().alpha(0f).setDuration(200).start();
    }

    private void delayedAuth() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //was
            }
        }, 500);
    }

    private int getFinalWidth() {
        //return (int) getResources().getDimension(R.dimen.get_width);
        return 0;
    }
}
