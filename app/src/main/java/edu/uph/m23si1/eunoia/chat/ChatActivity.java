package edu.uph.m23si1.eunoia.chat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.uph.m23si1.eunoia.HasilTesActivity;
import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.model.Chat;
import edu.uph.m23si1.eunoia.model.Tes;
import io.realm.Realm;
import io.realm.Sort;

public class ChatActivity extends AppCompatActivity {

    LinearLayout chatContainer, llySend, llyBack, llyChatHasilTes, llyBoxHasil;
    EditText edtChat;
    ScrollView chatScroll;
    TextView txvNamaPsi, txvChatPembuka, txvSkorStatus, txvDetail;
    String namaPasien;
    String nama;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // Pastikan nama filenya sesuai

        txvNamaPsi = findViewById(R.id.txvNamaPsi);
        chatContainer = findViewById(R.id.chatContainer);
        edtChat = findViewById(R.id.edtChat);
        chatScroll = findViewById(R.id.chatScroll);
        llySend = findViewById(R.id.llySend);
        llyBack = findViewById(R.id.llyBack);
        txvChatPembuka = findViewById(R.id.txvChatPembuka);
        txvDetail = findViewById(R.id.txvDetail);
        llyChatHasilTes = findViewById(R.id.llyChatHasilTes);
        llyBoxHasil = findViewById(R.id.llyBoxHasil);
        txvSkorStatus = findViewById(R.id.txvSkorStatus);

        nama = getIntent().getStringExtra("namaPsikolog");
        namaPasien = getSharedPreferences("user_session", MODE_PRIVATE).getString("namaPasien", null);

        realm = Realm.getDefaultInstance();

        // Cek hasil tes terbaru
        Tes hasilTerakhir = realm.where(Tes.class)
                .sort("id", Sort.DESCENDING)
                .findFirst();

        if (hasilTerakhir == null) {
            // kalau tidak ada hasil tes
            llyChatHasilTes.setVisibility(View.GONE);
            txvChatPembuka.setText("Halo dok, saya ingin konsultasi sebentar untuk menjaga supaya tidak sampai mengalami depresi. Boleh bantu saya, ya?");
        } else {
            llyChatHasilTes.setVisibility(View.VISIBLE);
            txvChatPembuka.setText("Halo dok, saya baru saja melihat hasil tes saya. Jujur saya agak bingung dan ingin tahu maksudnya serta apa yang harus saya lakukan. Bisa bantu jelaskan?");

            String teks = "Skor Pasien : " + hasilTerakhir.getSkor() +
                    "\nStatus: " + hasilTerakhir.getStatus();
            txvSkorStatus.setText(teks);

            if (hasilTerakhir.getSkor() <= 33) {
                // Tidak Depresi
                llyBoxHasil.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CEEDC0")) // hijau
                );
            } else if (hasilTerakhir.getSkor() <= 66) {
                // Waspada
                llyBoxHasil.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD98D")) // kuning
                );
            } else {
                // Depresi
                llyBoxHasil.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD7D7")) // merah muda
                );
            }
        }

        txvNamaPsi.setText(nama);

        llyBack.setOnClickListener(v -> finish());

        llySend.setOnClickListener(v -> {
            String pesan = edtChat.getText().toString().trim();
            if (!pesan.isEmpty()) {
                tambahPesanUser(pesan, namaPasien);
                simpanPesan(pesan, namaPasien);
                edtChat.setText("");
                scrollKeBawah();
            }
        });
        muatHistoriChat();

        txvDetail.setOnClickListener(v -> {
            Tes hasilAkhir = realm.where(Tes.class)
                    .sort("id", Sort.DESCENDING)
                    .findFirst();

            if (hasilAkhir != null) {
                Intent intent = new Intent(ChatActivity.this, HasilTesActivity.class);
                intent.putExtra("idTes", hasilAkhir.getId());
                startActivity(intent);
            }
        });
    }
    private void muatHistoriChat() {
        for (Chat chat : realm.where(Chat.class).findAll()) {
            tambahPesanUser(chat.getMessage(), chat.getSender());
        }
        scrollKeBawah();
    }
    private void simpanPesan(String pesan, String sender) {
        new Thread(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                Number currentId = r.where(Chat.class).max("id");
                long nextId = (currentId == null) ? 1 : currentId.longValue() + 1;

                Chat chat = r.createObject(Chat.class, nextId);
                chat.setSender(sender);
                chat.setMessage(pesan);
                chat.setTimestamp(System.currentTimeMillis());
            });
            realm.close();
        }).start();
    }
    private void tambahPesanUser(String teks, String sender) {
        // Buat TextView baru untuk pesan
        TextView pesanView = new TextView(this);
        pesanView.setText(teks);
        pesanView.setTextColor(getResources().getColor(R.color.white));
        pesanView.setBackgroundResource(R.drawable.bubble); // bubble user
        pesanView.setPadding(25, 18, 25, 18);

        // Atur layout params supaya align ke kanan
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 10);
        if (sender.equals(namaPasien)) {
            params.gravity = android.view.Gravity.END; // kalau user sendiri
        } else {
            params.gravity = android.view.Gravity.START; // kalau psikolog
        }
        pesanView.setLayoutParams(params);

        chatContainer.addView(pesanView);
    }
    private void scrollKeBawah() {
        chatScroll.post(() -> chatScroll.fullScroll(View.FOCUS_DOWN));
    }
}