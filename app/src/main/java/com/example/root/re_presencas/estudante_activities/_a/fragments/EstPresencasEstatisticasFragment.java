package com.example.root.re_presencas.estudante_activities._a.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.re_presencas.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstPresencasEstatisticasFragment extends Fragment {


    public EstPresencasEstatisticasFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_est_presencas_estatisticas, container, false);

        PieChart pieChart = view.findViewById(R.id.pie_presencas_estat);

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

        return view;
    }


}
