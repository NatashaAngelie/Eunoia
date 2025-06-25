package edu.uph.m23si1.eunoia.ui.konsul;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KonsulViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public KonsulViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Konsultasi fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}