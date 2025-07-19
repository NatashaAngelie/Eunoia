package edu.uph.m23si1.eunoia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import edu.uph.m23si1.eunoia.model.Akun;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnLogin;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("akun.realm")
                .schemaVersion(1)
                .allowWritesOnUiThread(true) // sementara aktifkan untuk demo
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        initData();
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            RealmResults<Akun> semuaAkun = r.where(Akun.class).equalTo("isLoggedIn", true).findAll();
            for (Akun a : semuaAkun) {
                a.setLoggedIn(false);
            }
        });

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // cek di Realm
            Akun akun = realm.where(Akun.class)
                    .equalTo("email", email)
                    .equalTo("password", password)
                    .findFirst();

            if (akun != null) {
                realm.executeTransaction(r -> {
                    akun.setLoggedIn(true);
                });
                // login berhasil
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show();

                String namaLengkap = akun.getNamaLengkap();
                String username = akun.getUsername();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("namaLengkap", namaLengkap);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email atau password tidak terdaftar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initData(){
        Realm realm = Realm.getDefaultInstance();

        // cek dulu apakah sudah ada data
        long count = realm.where(Akun.class).count();
        if (count > 0) {
            return; // sudah ada data, tidak perlu isi lagi
        }

        List<Akun> daftarAkun = new ArrayList<>();

        daftarAkun.add(new Akun("icha.septina@gmail.com", "12345", "", "Icha Septina", "Psikologi", 18, "Perempuan","", false));
        daftarAkun.add(new Akun("yovara.sepp@gmail.com", "12345", "", "Asep Yovara", "Teknik Informatika", 19, "Laki-laki","", false));
        daftarAkun.add(new Akun("seny.caroline@gmail.com", "12345", "", "Seni Caroline", "Ilmu Komunikasi", 20, "Perempuan","", false));
        daftarAkun.add(new Akun("rea.parulian@gmail.com", "12345", "", "Rea Parulian", "Kedokteran", 22, "Perempuan", "", false));
        daftarAkun.add(new Akun("rhoma@gmail.com", "12345", "", "Rhoma", "Hukum", 23, "Laki-laki", "", false));
        daftarAkun.add(new Akun("irham089@gmail.com", "12345", "", "Irham", "Manajemen", 19, "Laki-laki","", false));
        daftarAkun.add(new Akun("teti.supratto@gmail.com", "12345", "", "Teti Supratto", "Akuntansi", 18, "Laki-laki","", false));
        daftarAkun.add(new Akun("susan.min@gmail.com", "12345", "", "Susan Min", "Farmasi", 21, "Perempuan","", false));
        daftarAkun.add(new Akun("mahesa@gmail.com", "12345", "", "Mahesa", "Arsitektur", 22, "Perempuan","", false));
        daftarAkun.add(new Akun("sujan.pasmir@gmail.com", "12345", "", "Sujan Pasmir", "Teknik Mesin", 20, "Laki-laki","", false));
// Masukkan ke Realm
        realm.executeTransaction(r -> {
            r.insert(daftarAkun);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}