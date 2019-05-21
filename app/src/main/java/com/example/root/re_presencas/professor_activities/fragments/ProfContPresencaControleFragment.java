package com.example.root.re_presencas.professor_activities.fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.all.adapters.ListViewMarcacao;
import com.example.root.re_presencas.estudante_activities._a.fragments.EstPresencasMarcacaoFragment;
import com.example.root.re_presencas.login.LoginActivity;
import com.example.root.re_presencas.model.Alocacao;
import com.example.root.re_presencas.model.Aula;
import com.example.root.re_presencas.model.Docente;
import com.example.root.re_presencas.model.Marcacao;
import com.example.root.re_presencas.professor_activities.activities.ProfControlePresencaStart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfContPresencaControleFragment extends Fragment {


    private DatabaseReference raiz;
    private Switch swActivarMarcacao;
    private Switch swSelarMarcacao;
    private ListView listViewControleProf;
    private Intent intent;
    private List<Marcacao> marcacaoList;
    private static String idAula;

    public ProfContPresencaControleFragment() {

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prof_cont_presenca_controle, container, false);

        raiz = FirebaseDatabase.getInstance().getReference();
        swActivarMarcacao = view.findViewById(R.id.sw_activar_marcacao);
        swSelarMarcacao = view.findViewById(R.id.sw_selar_marcacao);
        listViewControleProf = view.findViewById(R.id.recycler_controle_prof);
        intent = Objects.requireNonNull(getActivity()).getIntent();
        marcacaoList = new LinkedList<>();

        swSelarMarcacao.setVisibility(View.INVISIBLE);
        this.switchClicked();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        this.controleMarcacao();
    }

    private void controleMarcacao() {
        String[] extras = intent.getStringArrayExtra(ProfControlePresencaStart.PR_LOGADO);
        final Docente docenteLogado = new Docente(extras[0], extras[1], extras[2], extras[3]);

        Query query = raiz.child("alocacao").orderByChild("id_docente").equalTo(docenteLogado.getId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    final Alocacao alocacao = d.getValue(Alocacao.class);
                    assert alocacao != null;

                    Query query1 = raiz.child("aula").orderByChild("id_alocacao").equalTo(alocacao.getId());
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            marcacaoList.clear();
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                final Aula aula = d.getValue(Aula.class);
                                assert aula != null;

                                Query query2 = raiz.child("marcacao").orderByChild("id_aula").equalTo(aula.getId());
                                query2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        marcacaoList.clear();
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            final Marcacao marcacao = d.getValue(Marcacao.class);
                                            assert marcacao != null;

                                            marcacaoList.add(marcacao);
                                        }
                                        try {
                                            ListViewMarcacao adapter = new ListViewMarcacao(getActivity(), marcacaoList);
                                            listViewControleProf.setAdapter(adapter);
                                        }catch (NullPointerException e) {
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void switchClicked() {
        swActivarMarcacao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    builder.setTitle("Aviso");
                    builder.setMessage("Deseja mesmo activar sessão para que estudantes dessa sala marquem presenças?");
                    builder.setNegativeButton("Não ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            swActivarMarcacao.setChecked(false);
                        }
                    });

                    builder.setPositiveButton("Sim ", new DialogInterface.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            swActivarMarcacao.setChecked(true);
                            swSelarMarcacao.setVisibility(View.VISIBLE);

                            //Salvar aula na tabela aula
                            String id = raiz.push().getKey();
                            idAula = id;
                            String idSala = intent.getStringExtra(ProfControlePresencaStart.ID_SALA);
                            String item = intent.getStringExtra(ProfControlePresencaStart.SELECTED_ITEM);
                            String idAlocacao = "alocacao" + item.split("  ")[4];
                            Aula aula = new Aula(id, idSala, idAlocacao, getDataActual(), false);
                            assert id != null;
                            raiz.child("aula").child(id).setValue(aula);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


        swSelarMarcacao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    builder.setTitle("Aviso");
                    builder.setMessage("Deseja mesmo selar a sessão para confirmar a presença desses estudantes?");
                    builder.setNegativeButton("Não ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            swSelarMarcacao.setChecked(false);
                        }
                    });

                    builder.setPositiveButton("Sim ", new DialogInterface.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            swSelarMarcacao.setChecked(true);
                            raiz.child("aula").child(idAula).child("is_selado").setValue(true);
                            EstPresencasMarcacaoFragment.sessionClosed(idAula);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private String getDataActual() {
        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        int dia = c.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = c.get(GregorianCalendar.MONTH) + 1;
        int ano = c.get(GregorianCalendar.YEAR);

        return dia + "-" + mes + "-" + ano;
    }

}
