package com.example.root.re_presencas.professor_activities.fragments;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.all.adapters.ListViewParticipantes;
import com.example.root.re_presencas.model.Inscricao;
import com.example.root.re_presencas.professor_activities.activities.ProfControlePresencaStart;
import com.example.root.re_presencas.professor_activities.activities.ProfEstPresencaSelecionado;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfEstPresencaParticipantesFragment extends Fragment {

    private DatabaseReference raiz;
    private TextView tvDisciplina, tvPeriodoEAno;
    private ListView listViewEstudantes;
    private Intent intent;
    private LinkedList<Inscricao> estudantesList;
    public static final String NOME_ESTUDANTE = "nome_estudante";
    public static final String IS_PARTICIPANTE = "is_participante";
    public static final String NR_FALTAS = "nr_faltas";
    public static final String PERCENTAGEM = "percentagem";

    public ProfEstPresencaParticipantesFragment() {
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prof_est_presenca_participantes, container, false);

        raiz = FirebaseDatabase.getInstance().getReference();
        tvDisciplina = view.findViewById(R.id.tv_disciplina);
        tvPeriodoEAno = view.findViewById(R.id.tv_periodo_ano);
        listViewEstudantes = view.findViewById(R.id.recycler_estudantes);
        estudantesList = new LinkedList<>();
        intent = Objects.requireNonNull(getActivity()).getIntent();

        this.clickItem();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.setTextViews();
        this.fillListView();
    }

    private void fillListView() {
        String turma = intent.getStringExtra(ProfControlePresencaStart.SELECTED_ITEM);
        String turmaSemId = turma.substring(0, turma.length() - 1);
        String idDisciplinaPeriodo = turma.charAt(turma.length() - 1) + "_" + turmaSemId.split("  ")[2];


        Query query = raiz.child("inscricao").orderByChild("id_disciplina_periodo").equalTo(idDisciplinaPeriodo);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                estudantesList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Inscricao inscricao = d.getValue(Inscricao.class);
                    estudantesList.add(inscricao);
                }
                ListViewParticipantes adapter = new ListViewParticipantes(getActivity(), estudantesList);
                listViewEstudantes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Cancel", "Process cancelled");
            }
        });
    }

    private void clickItem() {
        listViewEstudantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView imgPhotoEst = view.findViewById(R.id.img_photo_est_marcacao);
                TextView tvNomeEst = view.findViewById(R.id.tv_nome_estudante);
                TextView tvIsParticipante = view.findViewById(R.id.tv_is_participante);
                TextView tvNrFaltas = view.findViewById(R.id.tv_nr_faltas);
                TextView tvPercentagemFaltas = view.findViewById(R.id.tv_percentagem_faltas);

                Intent intent = new Intent(getContext(), ProfEstPresencaSelecionado.class);
                intent.putExtra(NOME_ESTUDANTE, tvNomeEst.getText());
                intent.putExtra(IS_PARTICIPANTE, tvIsParticipante.getText());
                intent.putExtra(NR_FALTAS, tvNrFaltas.getText().subSequence(0, 2).toString().trim());
                intent.putExtra(PERCENTAGEM, tvPercentagemFaltas.getText());
                startActivity(intent);
            }
        });
    }

    private void setTextViews() {
        String turma = intent.getStringExtra(ProfControlePresencaStart.SELECTED_ITEM);
        String turmaSemId = turma.substring(0, turma.length() - 1);
        String disciplina = turma.split("  ")[0];
        String periodoEAno = turmaSemId.split("  ")[2] + " " + turma.split("  ")[1];

        tvDisciplina.setText(disciplina);
        tvPeriodoEAno.setText(periodoEAno);
    }
}
