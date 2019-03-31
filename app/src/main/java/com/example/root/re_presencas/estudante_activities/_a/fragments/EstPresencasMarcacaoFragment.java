package com.example.root.re_presencas.estudante_activities._a.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.re_presencas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstPresencasMarcacaoFragment extends Fragment {


    public EstPresencasMarcacaoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_est_presencas_marcacao, container, false);
    }

}
