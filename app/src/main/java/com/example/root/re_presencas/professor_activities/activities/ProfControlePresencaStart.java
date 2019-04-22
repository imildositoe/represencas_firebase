package com.example.root.re_presencas.professor_activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.all.adapters.ListViewSalas;
import com.example.root.re_presencas.all.adapters.RecyclerSalas;
import com.example.root.re_presencas.login.LoginActivity;
import com.example.root.re_presencas.model.Alocacao;
import com.example.root.re_presencas.model.Disciplina;
import com.example.root.re_presencas.model.Docente;
import com.example.root.re_presencas.model.Sala;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProfControlePresencaStart extends AppCompatActivity {

    private DatabaseReference raiz;
    private List<Sala> salaList;
    private Spinner spinnerTurmas;
    private ListView listViewSala;
    public static final String ID_SALA = "id";
    public static final String ID_DISCIPLINA = "id_disciplina";
    public static final String SELECTED_ITEM = "selected_item";
    public static final String ID_ALOCACAO = "id_alocacao";
    public static String idDisciplina;
    public static String idAlocacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_controle_presenca_start);
        setTitle("Turmas");

        raiz = FirebaseDatabase.getInstance().getReference();
        salaList = new LinkedList<>();
        spinnerTurmas = findViewById(R.id.spinner_turmas);
        listViewSala = findViewById(R.id.recycler_view_sala);

        this.clickItem();
    }

    private void clickItem() {
        listViewSala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sala sala = salaList.get(position);
                Intent intent = new Intent(ProfControlePresencaStart.this, ProfControleEstatisticasPresenca.class);
                intent.putExtra(ID_SALA, sala.getId());
                intent.putExtra(SELECTED_ITEM, spinnerTurmas.getSelectedItem().toString());
                intent.putExtra(ID_DISCIPLINA, idDisciplina);
                intent.putExtra(ID_ALOCACAO, idAlocacao);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.retrieveSalas();
        this.retrieveTurmas();
    }

    private void retrieveSalas() {
        Query query = raiz.child("sala");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                salaList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Sala sala = d.getValue(Sala.class);
                    salaList.add(sala);
                }
                ListViewSalas adapter = new ListViewSalas(ProfControlePresencaStart.this, salaList);
                listViewSala.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Cancel on start", "Process cancelled - " + databaseError);
            }
        });
    }

    private void retrieveTurmas() {
        final List<Alocacao> list = new LinkedList<>();
        Intent intent = getIntent();
        String[] extras = intent.getStringArrayExtra(LoginActivity.PR_LOGADO);
        final Docente docenteLogado = new Docente(extras[0], extras[1], extras[2], extras[3]);

        Query query = raiz.child("alocacao");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Alocacao alocacao = d.getValue(Alocacao.class);
                    /* Pegar alocacoes(turmas) que fazem parte do docente logado*/
                    assert alocacao != null;
                    if (docenteLogado.getId().equals(alocacao.getId_docente()) && alocacao.getAno() == 2019
                            && alocacao.isEstado_semestre()) {
                        list.add(alocacao);
                    }
                }

                /*Helper loop to create string array*/
                final LinkedList<String> helper = new LinkedList<>();
                for (final Alocacao a : list) {

                    Query queryDisciplinas = raiz.child("disciplina").orderByChild("id").equalTo(a.getId_disciplina());
                    queryDisciplinas.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Disciplina disciplina = d.getValue(Disciplina.class);
                                assert disciplina != null;
                                char idDisc = disciplina.getId().charAt(disciplina.getId().length() - 1);
                                String turma = disciplina.getDesignacao() + "  " + a.getAno() + "  " + a.getPeriodo() + idDisc;
                                helper.add(turma);

                                idDisciplina = disciplina.getId();
                                idAlocacao = a.getId();

                                String[] arraySpinner = new String[helper.size()];
                                for (int i = 0; i < helper.size(); i++) {
                                    arraySpinner[i] = helper.get(i);
                                }

                                /* Setting turmas to spinner */
                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ProfControlePresencaStart.this,
                                        android.R.layout.simple_spinner_item, arraySpinner);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerTurmas.setAdapter(spinnerAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Cancel on start", "Process cancelled - " + databaseError);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Cancel on start", "Process cancelled - " + databaseError);
            }
        });
    }
}
