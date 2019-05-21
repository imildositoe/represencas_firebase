package com.example.root.re_presencas.professor_activities.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.model.Estudante;
import com.example.root.re_presencas.professor_activities.fragments.ProfEstPresencaParticipantesFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Will appear when some student been selected in {@link ProfEstPresencaParticipantesFragment}
 */
public class ProfEstPresencaSelecionado extends AppCompatActivity {

    private ImageView imgFotoParticipante;
    private TextView tvNomePart, tvIsPart, tvAulasTotalPart, tvFaltasCometidasPart, tvFaltasRemanescentesPart;
    private Intent intent;
    private PieChart pieChart;
    private DatabaseReference raiz;
    private int[] cores = new int[]{
            Color.rgb(255, 65, 151),
            Color.rgb(2, 187, 169)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_est_presenca_selecionado);

        raiz = FirebaseDatabase.getInstance().getReference();
        imgFotoParticipante = findViewById(R.id.img_foto_participante);
        tvNomePart = findViewById(R.id.tv_nome_participante);
        tvIsPart = findViewById(R.id.tv_is_presente_marcacao);
        tvAulasTotalPart = findViewById(R.id.tv_aulas_total_part);
        tvFaltasCometidasPart = findViewById(R.id.tv_faltas_cometidas_part);
        tvFaltasRemanescentesPart = findViewById(R.id.tv_faltas_remanescentes_part);
        intent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setTextsAndChart();
    }

    private void setTextsAndChart() {

        String nome = intent.getStringExtra(ProfEstPresencaParticipantesFragment.NOME_ESTUDANTE);
        String isParticipante = intent.getStringExtra(ProfEstPresencaParticipantesFragment.IS_PARTICIPANTE);
        String nrFaltas = intent.getStringExtra(ProfEstPresencaParticipantesFragment.NR_FALTAS);
        String percent = intent.getStringExtra(ProfEstPresencaParticipantesFragment.PERCENTAGEM);
        String idEstSel = intent.getStringExtra(ProfEstPresencaParticipantesFragment.ID_EST_SELECIONADO);
        int faltas = Integer.parseInt(nrFaltas);
        int percentagem = Integer.parseInt(percent);
        //int aulas = (faltas * 100) / percentagem; //Tratar a divisao por zero, para estudantes sem faltas(percentagem=0)
        int aulas = Integer.parseInt(intent.getStringExtra(ProfEstPresencaParticipantesFragment.AULAS_TOTAL));
        String faltasRemanText = Integer.toString(aulas - faltas);
        String aulasTotalText = Integer.toString(aulas);

        tvNomePart.setText(nome);
        tvIsPart.setText(isParticipante);
        tvAulasTotalPart.setText(aulasTotalText);
        tvFaltasCometidasPart.setText(nrFaltas);
        tvFaltasRemanescentesPart.setText(faltasRemanText);

        Query queryEstudantes = raiz.child("estudante").orderByChild("id").equalTo(idEstSel);
        queryEstudantes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Estudante estudante = d.getValue(Estudante.class);
                    assert estudante != null;
                    Picasso.with(ProfEstPresencaSelecionado.this)
                            .load(estudante.getFoto())
                            .placeholder(R.drawable.ic_launcher_background)
                            .fit()
                            .centerCrop()
                            .into(imgFotoParticipante);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.toString());
            }
        });


        //Grafico
        pieChart = findViewById(R.id.prof_pie_presencas_selec_estat);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        int faltasPerc = (faltas * 100) / aulas;
        yValues.add(new PieEntry(faltasPerc, "Faltas Cometidas"));
        yValues.add(new PieEntry(100 - faltasPerc, "Faltas Remanescentes"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setLabel("");
        dataSet.setColors(cores);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawEntryLabels(false);
        //pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Faltas (%)");
        pieChart.setCenterTextSize(8);
        pieChart.animateXY(1400, 1400);
        pieChart.invalidate();
    }
}
