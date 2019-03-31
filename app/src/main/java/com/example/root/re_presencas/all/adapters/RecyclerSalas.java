package com.example.root.re_presencas.all.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.components.Components;
import com.example.root.re_presencas.model.Sala;
import com.example.root.re_presencas.professor_activities.activities.ProfControleEstatisticasPresenca;

import java.util.List;

public class RecyclerSalas extends RecyclerView.Adapter<RecyclerSalas.MyViewHolder> {

    private Context mContext;
    private List<Sala> mData;
    private Components components;
    public static final String ID_SALA = "id";

    public RecyclerSalas(){

    }

    public RecyclerSalas(Context mContext, List<Sala> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.components = new Components(mContext);
    }

    @TargetApi(value = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.row_sala, parent, false);

        Log.e("OncreateViewholder", "Layout atached");

        final MyViewHolder vHolder = new MyViewHolder(v);
//
//        vHolder.item_sala.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String idSala = mData.get(vHolder.getAdapterPosition()).getId();
//                Intent intent = new Intent(mContext, ProfControleEstatisticasPresenca.class);
//                intent.putExtra(ID_SALA, idSala);
//                mContext.startActivity(intent);
//            }
//        });
//
//        vHolder.item_sala.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(mContext, "Long click", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        String sala = "Sala  " + mData.get(position).getDesignacao();
        String bloco = "Bloco  " + mData.get(position).getDesignacao().charAt(0);

        myViewHolder.tvSala.setText(sala);
        myViewHolder.tvBloco.setText(bloco);
//        myViewHolder.img.setImageResource(mData.get(position).getPhoto());

        Log.e("BindViewHolder", "passei");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSala;
        private TextView tvBloco;
        private ConstraintLayout item_sala;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSala = itemView.findViewById(R.id.tv_sala);
            tvBloco = itemView.findViewById(R.id.tv_bloco);
            item_sala = itemView.findViewById(R.id.sala_item_id);
        }
    }
}
