package com.example.root.re_presencas.menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.estudante_activities._a.activities.EstPresenca;
import com.example.root.re_presencas.professor_activities.activities.ProfControlePresencaStart;

public class EstudanteMenu extends AppCompatActivity {

    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudante_menu);

        this.start();
    }

    private void start() {
        btnGo = findViewById(R.id.btn_go_estudante);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstudanteMenu.this, EstPresenca.class);
                startActivity(intent);
            }
        });
    }
}
