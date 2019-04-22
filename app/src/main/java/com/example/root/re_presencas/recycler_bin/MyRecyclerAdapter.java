package com.example.root.re_presencas.recycler_bin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.re_presencas.R;
import com.example.root.re_presencas.model.Sala;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {


    private List<Sala> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerAdapter(Context context, List<Sala> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_sala, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String sala = "Sala  " + mData.get(position).getDesignacao();
        String bloco = "Bloco  " + mData.get(position).getDesignacao().charAt(0);

        holder.tvSala.setText(sala);
        holder.tvBloco.setText(bloco);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Sala getItem(int id) {
        return mData.get(id);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView tvSala;
        private TextView tvBloco;
//        private ConstraintLayout item_sala;

        ViewHolder(View itemView) {
            super(itemView);
            tvSala = itemView.findViewById(R.id.tv_nome_estudante_marcacao);
            tvBloco = itemView.findViewById(R.id.tv_is_presente_marcacao);
//            item_sala = itemView.findViewById(R.id.sala_item_id);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}