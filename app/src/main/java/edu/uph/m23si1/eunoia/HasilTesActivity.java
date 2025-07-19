package edu.uph.m23si1.eunoia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import edu.uph.m23si1.eunoia.model.Tes;
import edu.uph.m23si1.eunoia.ui.histori.HistoriFragment;
import io.realm.Realm;

public class HasilTesActivity extends AppCompatActivity {

    private Realm realm;
    private TextView txtSkor, txtStatus;
    EditText edtUmur, edtPerasaan, edtJamTidur, edtJamBangun;
    RadioButton rdbLaki, rdbPerempuan, rdbDesa, rdbKota, rdbTinggi, rdbSedang, rdbRendah;
    RadioButton rdbUangSignifikan, rdbUangSedang, rdbUangRingan, rdbYDiriSendiri, rdbNDiriSendiri, rdbYDukungan, rdbNDukungan;
    RadioButton rdbYOlahraga, rdbNOlahraga, rdbYTidur, rdbNTidur, rdbYMakan, rdbNMakan;
    SeekBar skbTidakDihargai, skbDiabaikan, skbTidakPenting, skbNyamanBicara, skbLewatMakan;

    private LinearLayout llyBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_tes);

        llyBack = findViewById(R.id.llyBack);
        llyBack.setOnClickListener(v -> finish());

        txtSkor = findViewById(R.id.txtSkor);
        txtStatus = findViewById(R.id.txtStatus);

        realm = Realm.getDefaultInstance();

        int idTes = getIntent().getIntExtra("idTes", -1);
        Tes tes = realm.where(Tes.class).equalTo("id", idTes).findFirst();

        initViews();

        if (tes != null) {
            txtSkor.setText("Skor: " + tes.getSkor());
            txtStatus.setText("Status: " + tes.getStatus());

            edtUmur.setText(String.valueOf(tes.getUmur()));
            edtPerasaan.setText(tes.getPerasaan());
            edtJamTidur.setText(tes.getJamTidur());
            edtJamBangun.setText(tes.getJamBangun());

            if ("Laki-laki".equals(tes.getGender())) rdbLaki.setChecked(true);
            else rdbPerempuan.setChecked(true);

            if ("Pedesaan".equals(tes.getDemografi())) rdbDesa.setChecked(true);
            else rdbKota.setChecked(true);

            if ("3.50 - 4.00".equals(tes.getCgpa())) rdbTinggi.setChecked(true);
            else if ("2.50 - 3.49".equals(tes.getCgpa())) rdbSedang.setChecked(true);
            else rdbRendah.setChecked(true);

            switch (tes.getMasalahUang()) {
                case "Signifikan": rdbUangSignifikan.setChecked(true); break;
                case "Sedang": rdbUangSedang.setChecked(true); break;
                case "Ringan": rdbUangRingan.setChecked(true); break;
            }

            if (tes.isJadiDiriSendiri()) rdbYDiriSendiri.setChecked(true); else rdbNDiriSendiri.setChecked(true);
            if (tes.isDukunganKeluarga()) rdbYDukungan.setChecked(true); else rdbNDukungan.setChecked(true);
            if (tes.isOlahraga()) rdbYOlahraga.setChecked(true); else rdbNOlahraga.setChecked(true);
            if (tes.isSusahTidur()) rdbYTidur.setChecked(true); else rdbNTidur.setChecked(true);
            if (tes.isNafsuMakan()) rdbYMakan.setChecked(true); else rdbNMakan.setChecked(true);

            skbTidakDihargai.setMax(4);
            skbDiabaikan.setMax(4);
            skbTidakPenting.setMax(4);
            skbNyamanBicara.setMax(4);
            skbLewatMakan.setMax(4);

            skbTidakDihargai.setProgress(tes.getSkorTidakDihargai());
            skbDiabaikan.setProgress(tes.getSkorDiabaikan());
            skbTidakPenting.setProgress(tes.getSkorTidakPenting());
            skbNyamanBicara.setProgress(tes.getSkorNyamanBicara());
            skbLewatMakan.setProgress(tes.getSkorLewatMakan());
        }
    }
    private void initViews() {
        edtUmur = findViewById(R.id.edtUmur);
        edtPerasaan = findViewById(R.id.edtPerasaan);
        edtJamTidur = findViewById(R.id.edtJamTidur);
        edtJamBangun = findViewById(R.id.edtJamBangun);

        rdbLaki = findViewById(R.id.rdbLaki);
        rdbPerempuan = findViewById(R.id.rdbPerempuan);
        rdbDesa = findViewById(R.id.rdbDesa);
        rdbKota = findViewById(R.id.rdbKota);
        rdbTinggi = findViewById(R.id.rdbTinggi);
        rdbSedang = findViewById(R.id.rdbSedang);
        rdbRendah = findViewById(R.id.rdbRendah);
        rdbUangSignifikan = findViewById(R.id.rdbUangSignifikan);
        rdbUangSedang = findViewById(R.id.rdbUangSedang);
        rdbUangRingan = findViewById(R.id.rdbUangRingan);
        rdbYDiriSendiri = findViewById(R.id.rdbYDiriSendiri);
        rdbNDiriSendiri = findViewById(R.id.rdbNDiriSendiri);
        rdbYDukungan = findViewById(R.id.rdbYDukungan);
        rdbNDukungan = findViewById(R.id.rdbNDukungan);
        rdbYOlahraga = findViewById(R.id.rdbYOlahraga);
        rdbNOlahraga = findViewById(R.id.rdbNOlahraga);
        rdbYTidur = findViewById(R.id.rdbYTidur);
        rdbNTidur = findViewById(R.id.rdbNTidur);
        rdbYMakan = findViewById(R.id.rdbYMakan);
        rdbNMakan = findViewById(R.id.rdbNMakan);

        skbTidakDihargai = findViewById(R.id.skbTidakDihargai);
        skbDiabaikan = findViewById(R.id.skbDiabaikan);
        skbTidakPenting = findViewById(R.id.skbTidakPenting);
        skbNyamanBicara = findViewById(R.id.skbNyamanBicara);
        skbLewatMakan = findViewById(R.id.skbLewatMakan);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}