package com.example.root.re_presencas.menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.login.LoginActivity;
import com.example.root.re_presencas.professor_activities.activities.ProfControlePresencaStart;

public class ProfessorMenu extends AppCompatActivity {

    private Button btnGo;
    public static final String PR_LOGADO = "professor_logado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_menu);

        this.start();
    }

    private void start() {
        btnGo = findViewById(R.id.btn_go_professor);
        Intent intent = getIntent();
        final String[] extras = intent.getStringArrayExtra(LoginActivity.PR_LOGADO);

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
