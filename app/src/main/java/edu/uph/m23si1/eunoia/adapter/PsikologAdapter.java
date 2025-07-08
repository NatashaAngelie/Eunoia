package edu.uph.m23si1.eunoia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.model.Psikolog;
import edu.uph.m23si1.eunoia.chat.ChatActivity;
public class PsikologAdapter extends RecyclerView.Adapter<PsikologAdapter.PsikologViewHolder> {

    private Context context;
    private List<Psikolog> listPsikolog;

    public PsikologAdapter(Context context, List<Psikolog> listPsikolog) {
        this.context = context;
        this.listPsikolog = listPsikolog;
    }

    @NonNull
    @Override
    public PsikologViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_psikolog, parent, false);
        return new PsikologViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PsikologViewHolder holder, int position) {
        Psikolog psikolog = listPsikolog.get(position);
        holder.txtNama.setText(psikolog.getNama());
        holder.imgFoto.setImageResource(psikolog.getFotoResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("namaPsikolog", psikolog.getNama());
            intent.putExtra("fotoPsikolog", psikolog.getFotoResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listPsikolog.size();
    }

    public void updateList(List<Psikolog> newList) {
        listPsikolog = newList;
        notifyDataSetChanged();
    }

    public static class PsikologViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFoto;
        TextView txtNama;

        public PsikologViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgFoto);
            txtNama = itemView.findViewById(R.id.txtNama);
        }
    }

}
