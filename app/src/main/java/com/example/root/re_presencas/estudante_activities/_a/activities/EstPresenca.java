package com.example.root.re_presencas.estudante_activities._a.activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.all.adapters.MyPagerAdapter;
import com.example.root.re_presencas.all.adapters.ViewPagerSwipe;
import com.example.root.re_presencas.estudante_activities._a.fragments.EstPresencasEstatisticasFragment;
import com.example.root.re_presencas.estudante_activities._a.fragments.EstPresencasMarcacaoFragment;

public class EstPresenca extends AppCompatActivity {

    public static TabLayout tabLayout;
    private static ViewPagerSwipe viewPager;
    private MyPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_presenca);
        setTitle("Presenças");
        tabLayout = findViewById(R.id.est_pres_tab_layout);
        viewPager = findViewById(R.id.est_pres_view_pager);

        this.addTabs();
    }

    private void addTabs() {
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EstPresencasMarcacaoFragment(), "Marcacão");
        pagerAdapter.addFragment(new EstPresencasEstatisticasFragment(), "Estatísticas");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        disableTab(0, false, 1, false);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setElevation(0);
    }

    public static void disableTab(int tabPosition, boolean isEnabled, int currentTab, boolean isSwippable) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        ViewGroup vgTab = (ViewGroup) vg.getChildAt(tabPosition);
        vgTab.setEnabled(isEnabled);
        viewPager.setCurrentItem(currentTab);
        viewPager.setSwippingEnabled(isSwippable);
    }
}
