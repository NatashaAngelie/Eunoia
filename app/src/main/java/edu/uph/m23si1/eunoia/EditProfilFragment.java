package edu.uph.m23si1.eunoia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.uph.m23si1.eunoia.model.Akun;
import io.realm.Realm;

public class EditProfilFragment extends DialogFragment {

    private EditText edtNama, edtUsername, edtUmur, edtJurusan, edtNote;
    private RadioGroup rdgGender;
    private RadioButton rdbLaki, rdbPerempuan;
    private Button btnSave;
    private Realm realm;
    private Akun akun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profil, container, false);

        edtNama = view.findViewById(R.id.edtNama);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtUmur = view.findViewById(R.id.edtUmur);
        edtJurusan = view.findViewById(R.id.edtJurusan);
        edtNote = view.findViewById(R.id.edtNote);
        rdgGender = view.findViewById(R.id.rdgGender);
        rdbLaki = view.findViewById(R.id.rdbLaki);
        rdbPerempuan = view.findViewById(R.id.rdbPerempuan);
        btnSave = view.findViewById(R.id.btnSave);

        // init Realm
        Realm.init(requireContext().getApplicationContext());
        realm = Realm.getDefaultInstance();

        // ambil akun yang sedang login
        akun = realm.where(Akun.class).equalTo("isLoggedIn", true).findFirst();

        if (akun != null) {
            isiFormDariAkun(akun);
        }

        btnSave.setOnClickListener(v -> {
            simpanPerubahan();
            dismiss();
        });

        return view;
    }

    private void isiFormDariAkun(Akun akun) {
        edtNama.setText(akun.getNamaLengkap());
        edtUsername.setText(akun.getUsername());
        edtUmur.setText(String.valueOf(akun.getUmur()));
        edtJurusan.setText(akun.getJurusan());
        edtNote.setText(akun.getNote());
        if ("Laki-laki".equals(akun.getJenisKelamin())) {
            rdbLaki.setChecked(true);
        } else {
            rdbPerempuan.setChecked(true);
        }
    }

    private void simpanPerubahan() {
        if (akun == null) return;

        realm.executeTransaction(r -> {
            akun.setNamaLengkap(edtNama.getText().toString().trim());
            akun.setUsername(edtUsername.getText().toString().trim());
            akun.setUmur(Integer.parseInt(edtUmur.getText().toString().trim()));
            akun.setJurusan(edtJurusan.getText().toString().trim());
            akun.setNote(edtNote.getText().toString().trim());
            akun.setJenisKelamin(rdbLaki.isChecked() ? "Laki-laki" : "Perempuan");
        });

        Toast.makeText(getContext(), "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}