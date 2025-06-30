package edu.uph.m23si1.eunoia.ui.konsul;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.uph.m23si1.eunoia.ui.konsul.Psikolog;
import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.databinding.FragmentKonsulBinding;

public class KonsulFragment extends Fragment {

    EditText edtSearch;
    LinearLayout containerPsikolog;
    List<Psikolog> semuaPsikolog = new ArrayList<>();

    private FragmentKonsulBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKonsulBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        containerPsikolog = view.findViewById(R.id.containerPsikolog);
        edtSearch = view.findViewById(R.id.edtSearch); // ganti sesuai ID EditText kamu

        // Tambahkan data dummy
        semuaPsikolog.add(new Psikolog("Dr. John Doe, S.Psi., M.Psi.", R.drawable.psikolog));
        semuaPsikolog.add(new Psikolog("Dr. Dinda Meli, S.Psi., M.Psi.", R.drawable.psikolog));
        semuaPsikolog.add(new Psikolog("Dr. Ezra Adita, S.Psi., M.Psi.", R.drawable.psikolog));
        // dst...

        tampilkanPsikolog(semuaPsikolog);

        // Fitur Search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                List<Psikolog> hasil = new ArrayList<>();
                for (Psikolog p : semuaPsikolog) {
                    if (p.getNama().toLowerCase().contains(query)) {
                        hasil.add(p);
                    }
                }
                tampilkanPsikolog(hasil);
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void tampilkanPsikolog(List<Psikolog> daftar) {
        containerPsikolog.removeAllViews(); // Bersihkan dulu
        for (Psikolog p : daftar) {
            View item = getLayoutInflater().inflate(R.layout.list_psikolog, null);

            ImageView imgFoto = item.findViewById(R.id.imgFoto);
            TextView txtNama = item.findViewById(R.id.txtNama);

            imgFoto.setImageResource(p.getFotoResId());
            txtNama.setText(p.getNama());

            // Event klik
            item.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Kamu pilih: " + p.getNama(), Toast.LENGTH_SHORT).show();
            });

            containerPsikolog.addView(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
