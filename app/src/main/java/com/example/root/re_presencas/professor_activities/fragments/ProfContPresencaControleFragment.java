package com.example.root.re_presencas.professor_activities.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.re_presencas.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfContPresencaControleFragment extends Fragment {


    private DatabaseReference raiz;

    public ProfContPresencaControleFragment() {
        
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prof_cont_presenca_controle, container, false);

        raiz = FirebaseDatabase.getInstance().getReference();

        return view;
    }

}
