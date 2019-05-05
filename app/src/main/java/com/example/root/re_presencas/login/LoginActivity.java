package com.example.root.re_presencas.login;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    private ProgressDialog progressDialog;

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

        this.auth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.dismiss();
    }

    private void auth() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute();
            }
        });
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

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private boolean isInternetAvailable() {
        try {
            InetAddress ipAddress = InetAddress.getByName("www.google.com");
            return !ipAddress.equals("");
        } catch (UnknownHostException e) {
            Log.e("Conexao", e.toString());
        }
        return false;
    }

    /**
     * Class will execute jobs during authentication
     */
    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void, Void, Void> {

        MyTask() {
            progressDialog = new ProgressDialog(LoginActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, "", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... integers) {

            final String email = edtUsuario.getText().toString();
            final String senha = edtSenha.getText().toString();
            // Auth in table User (firebase)
            if (isNetworkConnected() && isInternetAvailable()) {
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {

                    /*Loop for docentes user*/
                    Query queryDocente = raiz.child("docente");
                    queryDocente.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean flag = false;
                            Docente docente = new Docente();
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                docente = d.getValue(Docente.class);
                                assert docente != null;

                                if (email.equals(docente.getEmail()) && senha.equals(docente.getSenha())) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                Intent intent = new Intent(LoginActivity.this, ProfessorMenu.class);
                                String[] extras = new String[]{
                                        docente.getId(), docente.getNome(), docente.getEmail(), docente.getSenha()
                                };
                                intent.putExtra(PR_LOGADO, extras);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Username ou senha incorrectos!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //Loop for estudantes user
                    Query query = raiz.child("estudante");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean flag = false;
                            Estudante estudante = new Estudante();
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                estudante = d.getValue(Estudante.class);
                                assert estudante != null;
                                if (email.equals(estudante.getEmail()) && senha.equals(estudante.getSenha())) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                Intent intent = new Intent(LoginActivity.this, EstudanteMenu.class);
                                String[] extras = new String[]{
                                        estudante.getId(), estudante.getNome(), estudante.getEmail(), estudante.getSenha()
                                };
                                intent.putExtra(EST_LOGADO, extras);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Username ou senha incorrectos!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            edtUsuario.setError("Campo obrigatório");
                            edtSenha.setError("Campo obrigatório");
                        }
                    });
                }
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Active os dados móveis ou conecte-se a uma rede WI-FI", Toast.LENGTH_LONG).show();
                    }
                });
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            progressDialog.dismiss();
        }
    }
}
