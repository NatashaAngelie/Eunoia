package edu.uph.m23si1.eunoia;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChatActivity extends AppCompatActivity {

    LinearLayout chatContainer, llySend;
    EditText edtChat;
    ScrollView chatScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // Pastikan nama filenya sesuai

        chatContainer = findViewById(R.id.chatContainer);
        edtChat = findViewById(R.id.edtChat);
        chatScroll = findViewById(R.id.chatScroll);
        llySend = findViewById(R.id.llySend); // tombol kirim pakai LinearLayout

        llySend.setOnClickListener(v -> {
            String pesan = edtChat.getText().toString().trim();
            if (!pesan.isEmpty()) {
                tambahPesanUser(pesan);
                edtChat.setText(""); // kosongkan input
                scrollKeBawah();
            }
        });
    }

    private void tambahPesanUser(String teks) {
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
        params.gravity = android.view.Gravity.END;
        pesanView.setLayoutParams(params);

        chatContainer.addView(pesanView);
    }

    private void scrollKeBawah() {
        chatScroll.post(() -> chatScroll.fullScroll(View.FOCUS_DOWN));
    }
}