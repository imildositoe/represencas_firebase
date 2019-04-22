package com.example.root.re_presencas.all.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.model.Estudante;
import com.example.root.re_presencas.model.Inscricao;
import com.example.root.re_presencas.model.Marcacao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListViewMarcacao extends ArrayAdapter<Marcacao> {

    private Activity context;
    private List<Marcacao> mData;
    private DatabaseReference raiz;
    private Intent intent;

    public ListViewMarcacao(Activity context, List<Marcacao> mData) {
        super(context, R.layout.row_sala, mData);
        this.context = context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_marcacao, null, true);

        raiz = FirebaseDatabase.getInstance().getReference();
        intent = context.getIntent();
        final TextView tvNome = view.findViewById(R.id.tv_nome_estudante_marcacao);
        final TextView tvIsPresente = view.findViewById(R.id.tv_is_presente_marcacao);
        final CheckBox cbIsPresente = view.findViewById(R.id.cb_is_presente);

        String idInscricao = mData.get(position).getId_inscricao();

        //Joining tables to show the exactly name in each listview row
        Query query = raiz.child("inscricao").orderByChild("id").equalTo(idInscricao);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Inscricao inscricao = d.getValue(Inscricao.class);
                    assert inscricao != null;
                    Query query1 = raiz.child("estudante").orderByChild("id").equalTo(inscricao.getId_estudante());
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Estudante estudante = d.getValue(Estudante.class);
                                assert estudante != null;
                                tvNome.setText(estudante.getNome());

                                cbIsPresente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (buttonView.isChecked()) {
                                            tvIsPresente.setText("Presente");
                                        } else if (!buttonView.isChecked()) {
                                            tvIsPresente.setText("Não Presente");
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Error", databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });

        return view;
    }
}
