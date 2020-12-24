package com.example.photogallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photogallery.db.PhotosDB;
import com.example.photogallery.model.Photo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class FullPhoto extends AppCompatActivity {
    private Button add_db_btn, del_db_btn;
    private ImageView image_view;
    Photo photo;
    PhotosDB photos_db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_photo_activity);

        image_view = findViewById(R.id.full_photo);
        add_db_btn = findViewById(R.id.add_db_btn);
        del_db_btn = findViewById(R.id.del_db_btn);

        //Получить ссылку на БД
        photos_db = PhotosDB.getDatabase(this);

        intent = getIntent();

        Gson gson = new Gson();
        String gson_photo = intent.getStringExtra("photo");

        photo = gson.fromJson(gson_photo, Photo.class);

        //Если открыто фото из БД, то скрыть кнопку "Добавить в БД"
        if (photo.getIsDb()) {
            add_db_btn.setVisibility(View.INVISIBLE);
        }
        //Иначе скрыть кнопку "Убрать из БД"
        else {
            del_db_btn.setVisibility(View.INVISIBLE);
        }

        //Получить асинхронно изображение по url'у
        Picasso.with(this)
                .load(photo.getPhotoUrl())
                .into(image_view);

        add_db_btn.setOnClickListener(view -> {
            addPhotoToDB();
        });

        del_db_btn.setOnClickListener(view -> {
            removePhotoFromDB();

            //Сообщить в главное activity о том, что было удалено фото
            intent.putExtra("is_removed", true);

            //Принудительно закрыть текущее окно
            finish();
        });
    }

    @Override
    public void finish(){
        setResult(RESULT_OK, intent);
        super.finish();
    }

    //Добавить изображение в БД
    void addPhotoToDB() {
        photos_db.request(
                () -> {
                    photo.setIsDb(true);
                    photos_db.photoDao().insertPhoto(photo);
                },
                () -> showMessage("Добавлено")
        );
    }

    //Удалить изображение из БД
    void removePhotoFromDB() {
        photos_db.request(
                () -> photos_db.photoDao().deletePhoto(photo),
                () -> showMessage("Удалено")
        );
    }

    private void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
