package edu.uph.m23si1.eunoia.ui.beranda;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uph.m23si1.eunoia.R;
import edu.uph.m23si1.eunoia.databinding.FragmentBerandaBinding;

public class BerandaFragment extends Fragment {
    TextView txtHello, txtLink1, txtLink2, txtLink3;
    LinearLayout llyMood1, llyMood2, llyMood3, llyMood4, llyMood5;

    private FragmentBerandaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BerandaViewModel berandaViewModel =
                new ViewModelProvider(this).get(BerandaViewModel.class);

        binding = FragmentBerandaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String username = getActivity().getIntent().getStringExtra("username");

        txtHello = root.findViewById(R.id.txtHello);
        txtLink1 = root.findViewById(R.id.txtLink1);
        txtLink2 = root.findViewById(R.id.txtLink2);
        txtLink3 = root.findViewById(R.id.txtLink3);
        llyMood1 = root.findViewById(R.id.llyMood1);
        llyMood2 = root.findViewById(R.id.llyMood2);
        llyMood3 = root.findViewById(R.id.llyMood3);
        llyMood4 = root.findViewById(R.id.llyMood4);
        llyMood5 = root.findViewById(R.id.llyMood5);

        if (username != null) {
            txtHello.setText("Hello, " + username + "!");
        }

        LinearLayout[] moods = new LinearLayout[] {
                binding.llyMood1, binding.llyMood2, binding.llyMood3, binding.llyMood4, binding.llyMood5
        };

        for (int i = 0; i < moods.length; i++) {
            int finalI = i;
            moods[i].setOnClickListener(v -> {
                for (int j = 0; j < moods.length; j++) {
                    if (j == finalI) {
                        moods[j].setElevation(12f); // tambahkan efek elevate
                        moods[j].setAlpha(1f);      // highlight
                    } else {
                        moods[j].setElevation(0f);  // hilangkan elevation
                        moods[j].setAlpha(0.5f);    // greyed out
                    }
                }
            });
        }

        binding.txtLink1.setOnClickListener(v -> {
            String url = "https://www.alodokter.com/kenali-macam-macam-depresi-dan-cara-menanganinya";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        binding.txtLink2.setOnClickListener(v -> {
            String url = "https://www.klikdokter.com/psikologi/kesehatan-mental/ini-tanda-dan-gejala-depresi-yang-harus-anda-waspadai?utm_source=chatgpt.com#google_vignette";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        binding.txtLink3.setOnClickListener(v -> {
            String url = "https://www.alodokter.com/ketahui-6-penyebab-depresi-dan-cara-penanganannya?utm_source=chatgpt.com";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}