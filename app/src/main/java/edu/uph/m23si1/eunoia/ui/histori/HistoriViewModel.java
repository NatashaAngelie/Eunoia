package edu.uph.m23si1.eunoia.ui.histori;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoriViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HistoriViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Histori fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}