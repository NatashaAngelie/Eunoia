package edu.uph.m23si1.eunoia.ui.histori;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.adapter.HistoriAdapter;
import edu.uph.m23si1.eunoia.databinding.FragmentHistoriBinding;
import edu.uph.m23si1.eunoia.model.Tes;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class HistoriFragment extends Fragment {

    private FragmentHistoriBinding binding;
    private LinearLayout llyBack;
    private Realm realm;
    private TextView txvJumlah, txvTanggal, txvSkor, txvStatus;
    private List<Tes> historiList;
    private HistoriAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistoriBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inisialisasi Realm
        realm = Realm.getDefaultInstance();

        // Ambil semua data Tes
        RealmResults<Tes> results = realm.where(Tes.class).findAll();

        // Ubah jadi List biasa
        historiList = realm.copyFromRealm(results);

        // Pasang adapter
        adapter = new HistoriAdapter(getContext(), historiList);
        binding.listHistori.setAdapter(adapter);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi Realm
        realm = Realm.getDefaultInstance();

        txvJumlah = view.findViewById(R.id.txvJumlah);
        txvTanggal = view.findViewById(R.id.txvTanggal);
        txvSkor = view.findViewById(R.id.txvSkor);
        txvStatus = view.findViewById(R.id.txvStatus);

        tampilkanDataHistori();
    }

    private void tampilkanDataHistori() {
        RealmResults<Tes> hasilTes = realm.where(Tes.class).sort("id", Sort.DESCENDING).findAll();

        // Jumlah tes
        int jumlahTes = hasilTes.size();
        txvJumlah.setText("Jumlah Test: " + jumlahTes);

        if (!hasilTes.isEmpty()) {
            Tes tesTerakhir = hasilTes.first();

            // Ambil tanggal sekarang untuk sementara (jika belum ada field createdAt di model)
            String tanggalTerakhir = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

            txvTanggal.setText("Tanggal: " + tanggalTerakhir);
            txvSkor.setText("Skor: " + tesTerakhir.getSkor());
            txvStatus.setText("Status: " + tesTerakhir.getStatus());
        } else {
            txvTanggal.setText("Tanggal: -");
            txvSkor.setText("Skor: -");
            txvStatus.setText("Status: -");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null && !realm.isClosed()) {
            realm.close(); // mencegah memory leak
        }
        binding = null;
    }
}
