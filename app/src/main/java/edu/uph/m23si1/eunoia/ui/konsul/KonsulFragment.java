package edu.uph.m23si1.eunoia.ui.konsul;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uph.m23si1.eunoia.databinding.FragmentKonsulBinding;

public class KonsulFragment extends Fragment {

    private FragmentKonsulBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        KonsulViewModel konsulViewModel =
                new ViewModelProvider(this).get(KonsulViewModel.class);

        binding = FragmentKonsulBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textKonsul;
        konsulViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}