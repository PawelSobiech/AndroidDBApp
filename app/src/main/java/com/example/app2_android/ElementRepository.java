package com.example.app2_android;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementRepository {
    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;
    ElementRepository(Application application) {
        ElementRoomDatabase elementRoomDatabase = ElementRoomDatabase.getDatabase(application);
        mElementDao = elementRoomDatabase.elementDao();
        mAllElements = mElementDao.getAlphabetizedElements();
    }
    LiveData<List<Element>> getAllElements() {
        return mAllElements;
    }

    void insert(Element element) {
        ElementRoomDatabase.databaseWriteExecutor.execute(()
                -> {
            mElementDao.insert(element);
        });
    }
    void deleteAll() {
        ElementRoomDatabase.databaseWriteExecutor.execute(()
                -> {
            mElementDao.deleteAll();
        });
    }
    void update(Element element)
    {
        ElementRoomDatabase.databaseWriteExecutor.execute(() ->{
            mElementDao.update(element);
        });
    }
    void delete(Element element)
    {
        ElementRoomDatabase.databaseWriteExecutor.execute(() ->{
            mElementDao.delete(element);
        });
    }
}