package com.example.root.re_presencas.professor_activities.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.professor_activities.fragments.ProfEstPresencaParticipantesFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;


/**
 * Will appear when some student been selected in {@link ProfEstPresencaParticipantesFragment}
 */
public class ProfEstPresencaSelecionado extends AppCompatActivity {

    private ImageView imgFotoParticipante;
    private TextView tvNomePart, tvIsPart, tvAulasTotalPart, tvFaltasCometidasPart, tvFaltasRemanescentesPart;
    private Intent intent;
    PieChart pieChart;
    //private int[] cores = new int[]{Color.rgb(215, 27, 96), Color.rgb(0, 191, 255)};
    private int[] cores = new int[]{
            Color.rgb(255, 65, 151),
            Color.rgb(2, 187, 169)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_est_presenca_selecionado);

//        imgFotoParticipante = findViewById(R.id.img_foto_participante);
        tvNomePart = findViewById(R.id.tv_nome_participante);
        tvIsPart = findViewById(R.id.tv_is_presente_marcacao);
        tvAulasTotalPart = findViewById(R.id.tv_aulas_total_part);
        tvFaltasCometidasPart = findViewById(R.id.tv_faltas_cometidas_part);
        tvFaltasRemanescentesPart = findViewById(R.id.tv_faltas_remanescentes_part);
        intent = getIntent();

        this.setTexts();
        this.grafico();
    }

    private void setTexts() {
        String nome = intent.getStringExtra(ProfEstPresencaParticipantesFragment.NOME_ESTUDANTE);
        String isParticipantes = intent.getStringExtra(ProfEstPresencaParticipantesFragment.IS_PARTICIPANTE);
        String nrFaltas = intent.getStringExtra(ProfEstPresencaParticipantesFragment.NR_FALTAS);
        String percentagem = intent.getStringExtra(ProfEstPresencaParticipantesFragment.PERCENTAGEM);

        tvNomePart.setText(nome);
        tvIsPart.setText(isParticipantes);
        /*tvAulasTotalPart.setText();*/
        tvFaltasCometidasPart.setText(nrFaltas);
        /*tvFaltasRemanescentesPart.setText();*/
    }

    private void grafico() {
        pieChart = findViewById(R.id.prof_pie_presencas_selec_estat);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(20, "Faltas Cometidas"));
        yValues.add(new PieEntry(50 - 20, "Faltas Remanescentes"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setLabel("");
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
}
