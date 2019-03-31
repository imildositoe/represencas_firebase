package com.example.root.re_presencas.professor_activities.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.re_presencas.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfEstPresencaParticipantesFragment extends Fragment {


    public ProfEstPresencaParticipantesFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prof_est_presenca_participantes, container, false);
    }

}
