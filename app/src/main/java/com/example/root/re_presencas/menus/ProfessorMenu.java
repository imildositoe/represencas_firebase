package com.example.root.re_presencas.menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.login.LoginActivity;
import com.example.root.re_presencas.professor_activities.activities.ProfControlePresencaStart;

public class ProfessorMenu extends AppCompatActivity {

    private Button btnGo;
    public static final String PR_LOGADO = "professor_logado";
    private String[] extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_menu);

        btnGo = findViewById(R.id.btn_go_professor);
        TextView tvNomeDocentePerfil = findViewById(R.id.tv_nome_docente_perfil);
        Intent intent = getIntent();
        extras = intent.getStringArrayExtra(LoginActivity.PR_LOGADO);

        tvNomeDocentePerfil.setText(extras[1]);
        this.start();
    }

    private void start() {
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorMenu.this, ProfControlePresencaStart.class);
                intent.putExtra(PR_LOGADO, extras);
                startActivity(intent);
            }
        });
    }
}
