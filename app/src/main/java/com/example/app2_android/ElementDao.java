package com.example.app2_android;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ElementDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Element element);
    @Query("DELETE FROM telefony")
    void deleteAll();
    @Query("SELECT * FROM telefony ORDER BY producent ASC")
    LiveData<List<Element>> getAlphabetizedElements();

    @Update
    void update(Element element);
}