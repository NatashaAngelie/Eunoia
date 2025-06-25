package edu.uph.m23si1.eunoia.ui.beranda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BerandaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BerandaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Beranda fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}