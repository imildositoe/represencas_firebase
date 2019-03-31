package com.example.root.re_presencas.professor_activities.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.professor_activities.fragments.ProfEstPresencaParticipantesFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 *
 * Will appear when some student been selected in {@link ProfEstPresencaParticipantesFragment}
 *
 * */
public class ProfEstPresencaSelecionado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_est_presenca_selecionado);

        this.grafico();
    }

    private void grafico() {
        PieChart pieChart = findViewById(R.id.prof_pie_presencas_selec_estat);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(20, "Faltas Cometidas"));
        yValues.add(new PieEntry(50 - 20, "Faltas Remanescentes"));

        PieDataSet dataSet = new PieDataSet(yValues, "Faltas");

        dataSet.setColors(ColorTemplate.createColors(new int[]{
                R.color.colorAccent,
                R.color.colorPrimary
        }));
//        dataSet.setValueTextColor(Color.BLACK);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
//        pieChart.animateXY(1400, 1400);
        pieChart.invalidate();
    }
}
