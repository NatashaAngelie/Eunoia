package edu.uph.m23si1.eunoia.ui.test;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.databinding.FragmentTestBinding;
import edu.uph.m23si1.eunoia.model.Tes;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TestFragment extends Fragment {
    LinearLayout llyAwal, llyHasil, llyHasilBG;
    ScrollView llyForm;
    Button btnMulai, btnKirim;
    ImageView imvHasil;
    TextView txvH1, txvH2, txvStatus, txvDeskripsi, txvSkor;
    EditText edtJurusan, edtUmur, edtPerasaan, edtJamTidur, edtJamBangun;
    RadioButton rdbLaki, rdbPerempuan, rdbDesa, rdbKota, rdbTinggi, rdbSedang, rdbRendah, rdbUangSignifikan, rdbUangSedang, rdbUangRingan, rdbYDiriSendiri, rdbYDukungan, rdbYOlahraga, rdbYTidur, rdbYMakan;
    SeekBar skbTidakDihargai, skbDiabaikan, skbTidakPenting, skbNyamanBicara, skbLewatMakan;
    private FragmentTestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TestViewModel testViewModel =
                new ViewModelProvider(this).get(TestViewModel.class);

        Realm.init(requireContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);


        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        llyAwal = root.findViewById(R.id.llyAwal);
        llyForm = root.findViewById(R.id.llyForm);
        llyHasil = root.findViewById(R.id.llyHasil);
        llyHasilBG = root.findViewById(R.id.llyHasilBG);
        btnMulai = root.findViewById(R.id.btnMulai);
        btnKirim = root.findViewById(R.id.btnKirim);
        txvH1 = root.findViewById(R.id.txvH1);
        txvH2 = root.findViewById(R.id.txvH2);
        txvStatus = root.findViewById(R.id.txvStatus);
        txvDeskripsi = root.findViewById(R.id.txvDeskripsi);
        txvSkor = root.findViewById(R.id.txvSkor);
        imvHasil = root.findViewById(R.id.imvHasil);

        edtJurusan = root.findViewById(R.id.edtJurusan);
        edtUmur = root.findViewById(R.id.edtUmur);
        edtPerasaan = root.findViewById(R.id.edtPerasaan);
        edtJamTidur = root.findViewById(R.id.edtJamTidur);
        edtJamBangun = root.findViewById(R.id.edtJamBangun);

        rdbLaki = root.findViewById(R.id.rdbLaki);
        rdbPerempuan = root.findViewById(R.id.rdbPerempuan);
        rdbDesa = root.findViewById(R.id.rdbDesa);
        rdbKota = root.findViewById(R.id.rdbKota);
        rdbTinggi = root.findViewById(R.id.rdbTinggi);
        rdbSedang = root.findViewById(R.id.rdbSedang);
        rdbRendah = root.findViewById(R.id.rdbRendah);
        rdbUangSignifikan = root.findViewById(R.id.rdbUangSignifikan);
        rdbUangSedang = root.findViewById(R.id.rdbUangSedang);
        rdbUangRingan = root.findViewById(R.id.rdbUangRingan);

        rdbYDiriSendiri = root.findViewById(R.id.rdbYDiriSendiri);
        rdbYDukungan = root.findViewById(R.id.rdbYDukungan);
        rdbYOlahraga = root.findViewById(R.id.rdbYOlahraga);
        rdbYTidur = root.findViewById(R.id.rdbYTidur);
        rdbYMakan = root.findViewById(R.id.rdbYMakan);

        skbTidakDihargai = root.findViewById(R.id.skbTidakDihargai);
        skbDiabaikan = root.findViewById(R.id.skbDiabaikan);
        skbTidakPenting = root.findViewById(R.id.skbTidakPenting);
        skbNyamanBicara = root.findViewById(R.id.skbNyamanBicara);
        skbLewatMakan = root.findViewById(R.id.skbLewatMakan);

        btnMulai.setOnClickListener(v -> {
            llyAwal.setVisibility(View.GONE);
            llyForm.setVisibility(View.VISIBLE);
            txvH1.setText("Semangat!");
            txvH2.setText("Jawablah pertanyaan ini dengan benar!");
        });

        btnKirim.setOnClickListener(v -> {
            new Thread(() -> {
                Realm realm = Realm.getDefaultInstance();

                realm.executeTransaction(r -> {
                    Number currentIdNum = realm.where(Tes.class).max("id");
                    int nextId = (currentIdNum == null) ? 1 : currentIdNum.intValue() + 1;

                    Tes data = realm.createObject(Tes.class, nextId);

                    data.setJurusan(edtJurusan.getText().toString());
                    data.setUmur(Integer.parseInt(edtUmur.getText().toString()));
                    data.setGender(rdbLaki.isChecked() ? "Laki-laki" : "Perempuan");
                    data.setDemografi(rdbDesa.isChecked() ? "Pedesaan" : "Perkotaan");

                    if (rdbTinggi.isChecked()) {
                        data.setCgpa("3.50 - 4.00");
                    } else if (rdbSedang.isChecked()) {
                        data.setCgpa("2.50 - 3.49");
                    } else if (rdbRendah.isChecked()) {
                        data.setCgpa("0.00 - 2.49");
                    }

                    if (rdbUangSignifikan.isChecked()) {
                        data.setMasalahUang("Signifikan");
                    } else if (rdbUangSedang.isChecked()) {
                        data.setMasalahUang("Sedang");
                    } else if (rdbUangRingan.isChecked()) {
                        data.setMasalahUang("Ringan");
                    }

                    data.setSeringTidakDihargai(skbTidakDihargai.getProgress());
                    data.setSeringDiabaikan(skbDiabaikan.getProgress());
                    data.setMerasaTidakPenting(skbTidakPenting.getProgress());
                    data.setPerasaan(edtPerasaan.getText().toString());
                    data.setNyamanBicara(skbNyamanBicara.getProgress());
                    data.setJadiDiriSendiri(rdbYDiriSendiri.isChecked());
                    data.setDukunganKeluarga(rdbYDukungan.isChecked());
                    data.setOlahraga(rdbYOlahraga.isChecked());
                    data.setSusahTidur(rdbYTidur.isChecked());
                    data.setJamTidur(edtJamTidur.getText().toString());
                    data.setJamBangun(edtJamBangun.getText().toString());
                    data.setNafsuMakan(rdbYMakan.isChecked());
                    data.setSeringLewatMakan(skbLewatMakan.getProgress());

                    int skor = hitungSkor();
                    data.setSkor(skor);

                    // Update UI setelah transaksi selesai
                    requireActivity().runOnUiThread(() -> {
                        tampilkanHasil(skor);
                        Toast.makeText(getContext(), "Jawaban tersimpan!", Toast.LENGTH_SHORT).show();
                    });
                });

                realm.close();
            }).start();
        });

        edtJamTidur.setOnClickListener(v -> {
            TimePickerDialog picker = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
                String waktu = String.format("%02d:%02d", hourOfDay, minute);
                edtJamTidur.setText(waktu);
            }, 0, 0, true); // default jam tidur jam 00:00
            picker.show();
        });

        edtJamBangun.setOnClickListener(v -> {
            TimePickerDialog picker = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
                String waktu = String.format("%02d:%02d", hourOfDay, minute);
                edtJamBangun.setText(waktu);
            }, 0, 0, true); // default jam bangun jam 00:00
            picker.show();
        });

        return root;
    }

    private int hitungSkor() {
        int skor = 0;
        int umur = Integer.parseInt(edtUmur.getText().toString());
        if (umur <= 17) {
            skor += 4;
        } else if (umur <= 19) {
            skor += 3;
        } else if (umur <= 23) {
            skor += 2;
        } else if (umur <= 27) {
            skor += 1;
        } else {
            skor += 0;
        }

        if (rdbDesa.isChecked()) {
            skor += 4;
        } else if (rdbKota.isChecked()) {
            skor += 0;
        }

        if (rdbUangSignifikan.isChecked()) {
            skor += 9;
        } else if (rdbUangSedang.isChecked()) {
            skor += 4;
        } else if (rdbUangRingan.isChecked()) {
            skor += 0;
        }

        if (rdbTinggi.isChecked()) {
            skor += 4;
        } else if (rdbSedang.isChecked()) {
            skor += 2;
        } else if (rdbRendah.isChecked()) {
            skor += 0;
        }

        String jamTidurStr = edtJamTidur.getText().toString(); // misal: "23:30"
        String jamBangunStr = edtJamBangun.getText().toString(); // misal: "06:30"

        String[] tidurSplit = jamTidurStr.split(":");
        String[] bangunSplit = jamBangunStr.split(":");

        int jamTidurHour = Integer.parseInt(tidurSplit[0]);
        int jamTidurMinute = Integer.parseInt(tidurSplit[1]);

        int jamBangunHour = Integer.parseInt(bangunSplit[0]);
        int jamBangunMinute = Integer.parseInt(bangunSplit[1]);

        int totalTidurMinutes = (jamBangunHour * 60 + jamBangunMinute) - (jamTidurHour * 60 + jamTidurMinute);
        if (totalTidurMinutes <= 0) {
            totalTidurMinutes += 24 * 60; // lewat tengah malam
        }

        int totalTidurJam = totalTidurMinutes / 60;
        int skorTidur = 0;
        if (totalTidurJam >= 8) {
            skorTidur = 0;
        } else if (totalTidurJam >= 7) {
            skorTidur = 2;
        } else if (totalTidurJam >= 6) {
            skorTidur = 3;
        } else if (totalTidurJam >= 5) {
            skorTidur = 4;
        } else if (totalTidurJam >= 4) {
            skorTidur = 5;
        } else {
            skorTidur = 9;
        }

        skor += skorTidur;

        skor += konversiSeekBar(skbTidakDihargai);
        skor += konversiSeekBar(skbDiabaikan);
        skor += konversiSeekBar(skbTidakPenting);
        skor += 4 - konversiSeekBar(skbNyamanBicara);
        skor += konversiSeekBar(skbLewatMakan);
        if (rdbYTidur.isChecked()) skor += 10;
        if (rdbYMakan.isChecked()) skor += 10;
        if (!rdbYDukungan.isChecked()) skor += 10;
        if (!rdbYOlahraga.isChecked()) skor += 10;
        if (!rdbYDiriSendiri.isChecked()) skor += 10;
        return Math.min(skor, 100);
    }
    private int konversiSeekBar(SeekBar skb) {
        double progress = skb.getProgress();
        double value = (progress / 100.0) * 4.0;
        return (int) Math.round(value);
    }
    private void tampilkanHasil(int skor){
        llyForm.setVisibility(View.GONE);
        llyHasil.setVisibility(View.VISIBLE);
        txvH1.setText("Hasil");
        txvH2.setText("Berikut adalah hasil test Anda");
        txvSkor.setText("Skor kamu: " + skor);


        if (skor <= 33) {
            // Tidak Depresi
            llyHasilBG.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CEEDC0")));
            imvHasil.setImageResource(R.drawable.tidak_depresi);
            txvStatus.setText("Anda Tidak Mengalami Depresi");
            txvDeskripsi.setText("Berdasarkan jawaban yang Anda berikan, sistem kami menemukan bahwa Anda tidak mengalami depresi. Hal ini menunjukkan bahwa penting untuk segera mengambil langkah dan berkonsultasi dengan profesional kesehatan mental.\n" +
                    "\n" +
                    "Beberapa faktor yang mungkin meningkatkan risiko Anda meliputi tekanan emosional yang berkepanjangan, riwayat gangguan kesehatan mental dalam keluarga, dan gejala tertentu seperti merasa sedih terus-menerus, kehilangan minat pada aktivitas, atau kesulitan tidur.\n" +
                    "Hasil ini bukanlah diagnosis medis, melainkan panduan untuk membantu Anda lebih memahami kondisi kesehatan mental Anda.\n" +
                    "\n" +
                    "Kami menyarankan Anda untuk berbicara dengan psikolog yang dapat membantu Anda memahami situasi ini lebih dalam dan memberikan langkah-langkah penanganan yang sesuai. ");
        } else if (skor <= 66) {
            // Waspada
            llyHasilBG.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD98D")));
            imvHasil.setImageResource(R.drawable.waspada);
            txvStatus.setText("Anda Perlu Waspada");
            txvDeskripsi.setText("Berdasarkan jawaban yang Anda berikan, sistem kami menemukan bahwa Anda tidak mengalami depresi. Hal ini menunjukkan bahwa penting untuk segera mengambil langkah dan berkonsultasi dengan profesional kesehatan mental.\n" +
                    "\n" +
                    "Beberapa faktor yang mungkin meningkatkan risiko Anda meliputi tekanan emosional yang berkepanjangan, riwayat gangguan kesehatan mental dalam keluarga, dan gejala tertentu seperti merasa sedih terus-menerus, kehilangan minat pada aktivitas, atau kesulitan tidur.\n" +
                    "Hasil ini bukanlah diagnosis medis, melainkan panduan untuk membantu Anda lebih memahami kondisi kesehatan mental Anda.\n" +
                    "\n" +
                    "Kami menyarankan Anda untuk berbicara dengan psikolog yang dapat membantu Anda memahami situasi ini lebih dalam dan memberikan langkah-langkah penanganan yang sesuai. ");
        } else {
            // Depresi
            llyHasilBG.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD7D7")));
            imvHasil.setImageResource(R.drawable.depresi);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}