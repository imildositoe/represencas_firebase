package com.example.root.re_presencas.all.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.model.Alocacao;
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

public class ListViewParticipantes extends ArrayAdapter<Inscricao> {

    private Activity context;
    private List<Inscricao> mData;
    private DatabaseReference raiz;
    private Intent intent;
    public static int aulasTotal;

    public ListViewParticipantes(Activity context, List<Inscricao> mData) {
        super(context, R.layout.row_sala, mData);
        this.context = context;
        this.mData = mData;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_estudante, null, true);

        raiz = FirebaseDatabase.getInstance().getReference();
        intent = context.getIntent();
        ImageView imgPhotoEst = view.findViewById(R.id.img_photo_est);
        final TextView tvNomeEst = view.findViewById(R.id.tv_nome_estudante);
        TextView tvIsParticipante = view.findViewById(R.id.tv_is_participante);
        final TextView tvNrFaltas = view.findViewById(R.id.tv_nr_faltas);
        final TextView tvPercentagemFaltas = view.findViewById(R.id.tv_percentagem_faltas);

        final Inscricao inscricao = mData.get(position);

        // Join para poder mostrar o nome concreto do estudante (Inscricao + Estudante)
        Query queryEstudantes = raiz.child("estudante");
        queryEstudantes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Estudante estudante = d.getValue(Estudante.class);
                    assert estudante != null;
                    if (estudante.getId().equals(inscricao.getId_estudante())) {
                        tvNomeEst.setText(estudante.getNome());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });


        // Para pegar o numero das aulas da turma da inscricao
        Query queryMarc = raiz.child("alocacao").orderByChild("id_disciplina").equalTo(inscricao.getId_disciplina());
        queryMarc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Alocacao alocacao = d.getValue(Alocacao.class);
                    assert alocacao != null;

                    if (alocacao.getAno() == inscricao.getAno() && alocacao.getPeriodo().equals(inscricao.getPeriodo())) {
                        aulasTotal = alocacao.getNr_aulas();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Para pegar o numero total de faltas de cada inscricao
        Query query = raiz.child("marcacao").orderByChild("id_inscricao").equalTo(inscricao.getId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int faltas = 0;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Marcacao marcacao = d.getValue(Marcacao.class);
                    assert marcacao != null;

                    if (!marcacao.isIs_presente()) {
                        faltas++;
                    }
                }

                // nrAulas  --  100%;
                // nrFaltas --  x
                int percentagem = (faltas * 100) / aulasTotal;

                String textFaltas = faltas + "  Faltas";
                String textPercentagem = percentagem + "  %";

                tvNrFaltas.setText(textFaltas);
                tvPercentagemFaltas.setText(textPercentagem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
