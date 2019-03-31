package com.example.root.re_presencas.all.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.model.Sala;

import java.util.List;

public class ListViewSalas extends ArrayAdapter<Sala> {

    private Activity context;
    private List<Sala> mData;
    ConstraintLayout item_sala;

    public ListViewSalas(Activity context, List<Sala> mData) {
        super(context, R.layout.row_sala, mData);
        this.context = context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_sala, null, true);

        TextView tvSala = view.findViewById(R.id.tv_sala);
        TextView tvBloco = view.findViewById(R.id.tv_bloco);
        item_sala = view.findViewById(R.id.sala_item_id);

        String sala = "Sala  " + mData.get(position).getDesignacao();
        String bloco = "Bloco  " + mData.get(position).getDesignacao().charAt(0);

        tvSala.setText(sala);
        tvBloco.setText(bloco);

        return view;
    }
}
