package edu.uph.m23si1.eunoia.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.ui.konsul.KonsulFragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    LinearLayout chatContainer, llySend, llyBack;
    EditText edtChat;
    ScrollView chatScroll;
    TextView txvNamaPsi;
    DatabaseReference chatRef;
    String namaPasien;
    String nama;

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

        nama = getIntent().getStringExtra("namaPsikolog");
        namaPasien = getSharedPreferences("user_session", MODE_PRIVATE).getString("namaPasien", null);


        txvNamaPsi.setText(nama);

        llyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toKonsul();
            }
        });

        llySend.setOnClickListener(v -> {
            String pesan = edtChat.getText().toString().trim();
            if (!pesan.isEmpty()) {
                String key = chatRef.push().getKey();
                Map<String, Object> data = new HashMap<>();
                data.put("sender", namaPasien);
                data.put("message", pesan);
                chatRef.child(key).setValue(data);

                edtChat.setText(""); // kosongkan input
                scrollKeBawah();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        chatRef = database.getReference("chat_rooms").child(simpleSanitize(namaPasien) + "_" + simpleSanitize(nama));

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                String sender = snapshot.child("sender").getValue(String.class);
                String pesan = snapshot.child("message").getValue(String.class);
                tambahPesanUser(pesan, sender);
                scrollKeBawah();
            }

            @Override public void onChildChanged(DataSnapshot snapshot, String previousChildName) {}
            @Override public void onChildRemoved(DataSnapshot snapshot) {}
            @Override public void onChildMoved(DataSnapshot snapshot, String previousChildName) {}
            @Override public void onCancelled(DatabaseError error) {}
        });

    }
    private String simpleSanitize(String input) {
        return input.replace(".", "_");
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
    public void toKonsul() {
        finish();
    }
    private void scrollKeBawah() {
        chatScroll.post(() -> chatScroll.fullScroll(View.FOCUS_DOWN));
    }
}