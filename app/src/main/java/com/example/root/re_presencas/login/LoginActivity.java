package com.example.root.re_presencas.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.support.v7.app.AlertDialog;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.components.Components;
import com.example.root.re_presencas.databinding.ActivityLoginBinding;
import com.example.root.re_presencas.menus.EstudanteMenu;
import com.example.root.re_presencas.menus.ProfessorMenu;
import com.example.root.re_presencas.model.Docente;
import com.example.root.re_presencas.model.Estudante;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mainBinding;
    private FrameLayout btnSignIn;
    private TextView btnLoginHelp;
    private EditText edtUsuario;
    private EditText edtSenha;
    private Components components;
    private DatabaseReference raiz;
    public static final String PR_LOGADO = "professor_logado";
    public static final String EST_LOGADO = "estudante_logado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Autenticação");

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnLoginHelp = findViewById(R.id.btn_login_help);
        edtSenha = findViewById(R.id.edt_senha);
        edtUsuario = findViewById(R.id.edt_usuario);
        components = new Components(this);
        raiz = FirebaseDatabase.getInstance().getReference();

        this.loginHelp();
    }

    private void loginHelp() {
        btnLoginHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Use o email institucional como usuário e use o código correspondente como senha." +
                        "\nAs credenciais são as mesmas usadas no SIGA.");
                builder.setPositiveButton("Ok ", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


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
                finish();
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
                final String email = edtUsuario.getText().toString();
                final String senha = edtSenha.getText().toString();

                // Auth in table User (firebase)
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {

                /*Loop for docentes user*/
                Query queryDocente = raiz.child("docente");
                queryDocente.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Docente docente = d.getValue(Docente.class);
                            assert docente != null;

                            if (email.equals(docente.getEmail()) && senha.equals(docente.getSenha())) {
                                Intent intent = new Intent(LoginActivity.this, ProfessorMenu.class);
                                String[] extras = new String[]{
                                        docente.getId(), docente.getNome(), docente.getEmail(), docente.getSenha()
                                };
                                intent.putExtra(PR_LOGADO, extras);
                                startActivity(intent);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Cancel on start", "Process cancelled - " + databaseError);
                    }
                });

                /*Loop for estudantes user*/
                Query query = raiz.child("estudante");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Estudante estudante = d.getValue(Estudante.class);
                            assert estudante != null;
                            if (email.equals(estudante.getEmail()) && senha.equals(estudante.getSenha())) {
                                Intent intent = new Intent(LoginActivity.this, EstudanteMenu.class);
                                String[] extras = new String[]{
                                        estudante.getId(), estudante.getNome(), estudante.getEmail(), estudante.getSenha()
                                };
                                intent.putExtra(EST_LOGADO, extras);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Cancel on start", "Process cancelled - " + databaseError);
                    }
                });

                } else {
                    edtUsuario.setError("Campo obrigatório");
                    edtSenha.setError("Campo obrigatório");
                }
            }
        }, 500);
    }

    private int getFinalWidth() {
        return (int) getResources().getDimension(R.dimen.get_width);
    }
}
