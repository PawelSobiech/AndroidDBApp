package com.example.app2_android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementViewModel extends AndroidViewModel {

    private final ElementRepository mRepository;
    private final LiveData<List<Element>> mAllElements;

    public ElementViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ElementRepository(application);
        mAllElements = mRepository.getAllElements();
    }

    LiveData<List<Element>> getAllElements() {
        return mAllElements;
    }

    public void insert(Element element) {
        mRepository.insert(element);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void update(Element element) {
        mRepository.update(element);
    }
    public void delete(Element element) {
        mRepository.delete(element);
    }

}
