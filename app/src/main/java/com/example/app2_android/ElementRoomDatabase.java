package com.example.app2_android;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Element.class}, version = 1, exportSchema = false)
public abstract class ElementRoomDatabase extends RoomDatabase {
    public abstract ElementDao elementDao();
    private static volatile ElementRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static ElementRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ElementRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ElementRoomDatabase.class, "element_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }}}
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ElementDao dao = INSTANCE.elementDao();
                Element[] elements = {
                    new Element("DyszakCorp", "Dyszak00", "6.0","dyszak.com/dyszak00"),
                    new Element("GhettoMobile","Trapfon","brak","pdw.pl/pdk"),
                    new Element("CipciaPhone","Cipcifon","21.37","cipcia.pl/enterprise")};
                    for (Element e : elements)
                        dao.insert(e);
            });
        }
    };
}

