package com.example.photogallery.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.photogallery.model.Photo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotosDB extends RoomDatabase {
    private static volatile PhotosDB INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService dbWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PhotosDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhotosDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhotosDB.class, "database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    //Обертка для запросов к БД, выполняющихся в отдельных потоках
    //request_method - запрос  БД (вставка, удаление и т.п.)
    //result_method - код, который должен выполниться по окончании запроса к БД
    public static void request(PhotosDBRequest request_method, PhotosDBRequest result_method) {
        //Сделать запрос к БД в новом потоке
        Future future = dbWriteExecutor.submit(()-> {
            request_method.execute();
        });

        try {
            future.get();
            result_method.execute();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract PhotosDao photoDao();
}

