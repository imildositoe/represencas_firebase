package com.example.root.re_presencas.professor_activities.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.all.adapters.MyPagerAdapter;
import com.example.root.re_presencas.estudante_activities._a.fragments.EstPresencasEstatisticasFragment;
import com.example.root.re_presencas.estudante_activities._a.fragments.EstPresencasMarcacaoFragment;
import com.example.root.re_presencas.professor_activities.fragments.ProfContPresencaControleFragment;
import com.example.root.re_presencas.professor_activities.fragments.ProfEstPresencaParticipantesFragment;

public class ProfControleEstatisticasPresenca extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_controle_estatisticas_presenca);
        setTitle("Presenças");

        this.addTabs();
    }

    private void addTabs() {
        tabLayout = findViewById(R.id.prof_cont_est__pres_tab_layout);
        viewPager = findViewById(R.id.prof_cont_est__pres_view_pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ProfContPresencaControleFragment(), "Controle");
        pagerAdapter.addFragment(new ProfEstPresencaParticipantesFragment(), "Estatísticas");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setElevation(0);
    }
}
