package edu.uph.m23si1.eunoia.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import edu.uph.m23si1.eunoia.HasilTesActivity;
import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.model.Tes;

public class HistoriAdapter extends BaseAdapter {
    private Context context;
    private List<Tes> historiList;
    private LayoutInflater inflater;

    public HistoriAdapter(Context context, List<Tes> historiList) {
        this.context = context;
        this.historiList = historiList;
        this.inflater = LayoutInflater.from(context);
    }

//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.list_histori, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Tes tes = historiList.get(position);
//        holder.txvSkor.setText("Skor: " + tes.getSkor());
//        holder.txvStatus.setText("Status: " + tes.getStatus());
//    }

    @Override
    public int getCount() {
        return historiList.size();
    }

    @Override
    public Object getItem(int position) {
        return historiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView txvSkor, txvStatus;
        Button btnLihatDetail;
        ImageView imvEmoji;
        LinearLayout llyHistori;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_histori, null);
            holder = new ViewHolder();
            holder.llyHistori = convertView.findViewById(R.id.llyHistori);
            holder.txvSkor = convertView.findViewById(R.id.txvSkor);
            holder.txvStatus = convertView.findViewById(R.id.txvStatus);
            holder.btnLihatDetail = convertView.findViewById(R.id.btnLihatDetail);
            holder.imvEmoji = convertView.findViewById(R.id.imvEmoji);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Tes item = historiList.get(position);
        holder.txvSkor.setText("Skor Anda: " + item.getSkor());
        int skor = item.getSkor(); // ambil skor dari model

        if (skor <= 33) {
            holder.txvStatus.setText("Status: Tidak Depresi");
            holder.llyHistori.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CEEDC0")));
            holder.imvEmoji.setImageResource(R.drawable.senang);
        } else if (skor <= 66) {
            holder.txvStatus.setText("Status: Waspada");
            holder.llyHistori.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD98D")));
            holder.imvEmoji.setImageResource(R.drawable.biasa_aja);
        } else {
            holder.txvStatus.setText("Status: Depresi");
            holder.llyHistori.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD7D7")));
            holder.imvEmoji.setImageResource(R.drawable.murung);
        }

        holder.btnLihatDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, HasilTesActivity.class);
            intent.putExtra("idTes", historiList.get(position).getId());
            context.startActivity(intent);
        });

        return convertView;
    }
}

