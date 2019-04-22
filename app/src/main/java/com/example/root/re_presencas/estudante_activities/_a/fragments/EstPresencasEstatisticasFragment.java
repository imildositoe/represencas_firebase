package com.example.root.re_presencas.estudante_activities._a.fragments;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.menus.EstudanteMenu;
import com.example.root.re_presencas.model.Alocacao;
import com.example.root.re_presencas.model.Disciplina;
import com.example.root.re_presencas.model.Inscricao;
import com.example.root.re_presencas.model.Marcacao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstPresencasEstatisticasFragment extends Fragment {

    private DatabaseReference raiz;
    private PieChart pieChart;
    private TextView tvNome, tvAulasTotal, tvFaltasReman, tvFaltasCom;
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
    private Intent intent;
    private static int aulasTotal;
    private int[] cores = new int[]{
            Color.rgb(255, 65, 151),
            Color.rgb(2, 187, 169)
    };

    public EstPresencasEstatisticasFragment() {
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_est_presencas_estatisticas, container, false);

        raiz = FirebaseDatabase.getInstance().getReference();
        pieChart = view.findViewById(R.id.pie_presencas_estat);
        tvNome = view.findViewById(R.id.tv_nome_part_est);
        tvAulasTotal = view.findViewById(R.id.tv_aulas_total_est);
        tvFaltasReman = view.findViewById(R.id.tv_faltas_remanescentes_est);
        tvFaltasCom = view.findViewById(R.id.tv_faltas_cometidas_est);
        intent = Objects.requireNonNull(getActivity()).getIntent();
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);

        this.clickButtons();

        return view;
    }

    private void clickButtons() {

    }

    @Override
    public void onStart() {
        super.onStart();
        this.grafico();
    }

    /**
     * [estudante + inscricao + marcacao] to produce piechart
     */
    private void grafico() {
        tvNome.setText(intent.getStringArrayExtra(EstudanteMenu.EST_LOGADO)[1]);

        String idEstudante = intent.getStringArrayExtra(EstudanteMenu.EST_LOGADO)[0];
        Query query = raiz.child("inscricao").orderByChild("id_estudante").equalTo(idEstudante);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Inscricao inscricao = d.getValue(Inscricao.class);
                    assert inscricao != null;

                    Query queryDisc = raiz.child("disciplina").orderByChild("id").equalTo(inscricao.getId_disciplina());
                    final LinkedList<String> buttonHelper = new LinkedList<>();
                    queryDisc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Disciplina disciplina = d.getValue(Disciplina.class);
                                assert disciplina != null;

                                // Preencher as disciplinas nos botoes(sao disciplinas em que o estudante logado foi inscrito)
                                buttonHelper.add(disciplina.getDesignacao());
                            }

                            try {
                                btn1.setText(buttonHelper.get(0));
                                btn2.setText(buttonHelper.get(1));
                                btn3.setText(buttonHelper.get(2));
                                btn4.setText(buttonHelper.get(3));
                                btn5.setText(buttonHelper.get(4));
                                btn6.setText(buttonHelper.get(5));
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                Log.e("Botoes", e.toString());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    Query queryMarc = raiz.child("alocacao").orderByChild("id_disciplina").equalTo(inscricao.getId_disciplina());
                    queryMarc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Alocacao alocacao = d.getValue(Alocacao.class);
                                assert alocacao != null;

                                String nr_aulas = Integer.toString(alocacao.getNr_aulas());
                                tvAulasTotal.setText(nr_aulas);
                                aulasTotal = alocacao.getNr_aulas();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    Query query1 = raiz.child("marcacao").orderByChild("id_inscricao").equalTo(inscricao.getId());
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int faltas = 0;
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Marcacao marcacao = d.getValue(Marcacao.class);
                                assert marcacao != null;

                                //Pega-se o numero de faltas do estudante logado aqui
                                if (!marcacao.isIs_presente()) {
                                    faltas++;
                                }
                            }

                            String textFaltas = Integer.toString(faltas);
                            String textFaltasRem = Integer.toString(aulasTotal - faltas);
                            tvFaltasCom.setText(textFaltas);
                            tvFaltasReman.setText(textFaltasRem);

                            //Grafico aqui

                            ArrayList<PieEntry> yValues = new ArrayList<>();
                            yValues.add(new PieEntry(faltas, "Faltas Cometidas"));
                            yValues.add(new PieEntry(aulasTotal - faltas, "Faltas Remanescentes"));

                            final PieDataSet dataSet = new PieDataSet(yValues, "");
                            dataSet.setLabel("");
                            dataSet.setValueTextColor(Color.WHITE);
                            dataSet.setColors(cores);

                            PieData pieData = new PieData(dataSet);
                            pieChart.setData(pieData);
                            Description description = new Description();
                            description.setText("");
                            pieChart.setDescription(description);
                            pieChart.setDrawEntryLabels(false);
                            pieChart.setUsePercentValues(true);
                            pieChart.setCenterText("Faltas (%)");
                            pieChart.setCenterTextSize(8);
                            pieChart.animateXY(1400, 1400);
                            pieChart.invalidate();
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
