package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.photogallery.api.ServiceAPI;
import com.example.photogallery.db.PhotosDB;
import com.example.photogallery.model.FlickrPhotos;
import com.example.photogallery.model.Photo;
import com.example.photogallery.view.RVAdapter;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoGallery extends AppCompatActivity implements View.OnClickListener
{
    RVAdapter adapter;
    RecyclerView list_view;

    PhotosDB photos_db;

    List<Photo> photos;
    String flickr_api_key;

    //Изменить главное меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        //Прослушивать элемент поиска
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Сработает при отправке введенного текста
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchPhotosFromFlickr(query);
                return true;
            }

            //Сработает при вводе текста
            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        //Прочитать с локального файла api_key для запросов на flickr.com
        flickr_api_key = IOFile.readFile(this, "flickr_api_key.txt");

        //Получить ссылку на БД
        photos_db = PhotosDB.getDatabase(this);

        adapter = new RVAdapter(this, this);
        list_view = findViewById(R.id.recyclerView);

        GridLayoutManager layout_manager = new GridLayoutManager(this, 3);

        list_view.setLayoutManager(layout_manager);
        list_view.setAdapter(adapter);

        //Запросить изображения с flickr.com
        getPhotosFromFlickr();
    }

    //Возврат в главное activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        boolean is_removed = intent.getBooleanExtra("is_removed", false);

        //Если произошло удаление фото из БД, то удалить данное фото из списка
        if (is_removed) {
            int position = intent.getIntExtra("position", 0);

            photos.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    //Вызывается из RVAdapter при нажатии на элемент списка
    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        Intent intent = new Intent(this, FullPhoto.class);
        Gson gson = new Gson();

        intent.putExtra("photo", gson.toJson(photos.get(position)));
        intent.putExtra("position", position);

        startActivityForResult(intent, 1);
    }

    //Вызывается после выбора какого-либо пункта toolbar'а
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String action = item.getTitle().toString();

        if ("Загрузить из БД".equals(action)) {
            getPhotosFromDB();
        }
        else {
            getPhotosFromFlickr();
        }

        return super.onOptionsItemSelected(item);
    }

    //Асинхронный запрос на flickr.com для получения общедоступных изображений
    public void getPhotosFromFlickr() {
        if (flickr_api_key != "") {
            ServiceAPI.getFlickrAPI().getRecent(flickr_api_key).enqueue(new Callback<FlickrPhotos>() {
                @Override
                public void onResponse(Call<FlickrPhotos> call, Response<FlickrPhotos> response) {
                    photos = response.body().getPhotos().getPhoto();
                    adapter.updatePhotoList(photos);
                }

                @Override
                public void onFailure(Call<FlickrPhotos> call, Throwable t) {
                    Toast.makeText(PhotoGallery.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Асинхронный запрос на flickr.com для получения общедоступных изображений, чьи атрибуты совпадают с введенным текстом
    public void getSearchPhotosFromFlickr(String text) {
        if (flickr_api_key != "") {
            ServiceAPI.getFlickrAPI().getSearchPhotos(flickr_api_key, text).enqueue(new Callback<FlickrPhotos>() {
                @Override
                public void onResponse(Call<FlickrPhotos> call, Response<FlickrPhotos> response) {
                    photos = response.body().getPhotos().getPhoto();
                    adapter.updatePhotoList(photos);
                }

                @Override
                public void onFailure(Call<FlickrPhotos> call, Throwable t) {
                    Toast.makeText(PhotoGallery.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Получить изображния из БД
    public void getPhotosFromDB() {
        photos_db.request(
                    () -> photos = photos_db.photoDao().LoadAll(),
                    () -> adapter.updatePhotoList(photos)
                );
    }
}