package edu.uph.m23si1.eunoia.ui.histori;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uph.m23si1.eunoia.databinding.FragmentHistoriBinding;

public class HistoriFragment extends Fragment {

    private FragmentHistoriBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoriViewModel historiViewModel =
                new ViewModelProvider(this).get(HistoriViewModel.class);

        binding = FragmentHistoriBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHistori;
        historiViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}