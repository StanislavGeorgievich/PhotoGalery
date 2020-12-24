package com.example.photogallery;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOFile {
    //Открыть файл
    public static String readFile(Context context, String file_name) {
        String data = "";

        try {
            FileInputStream fin = context.openFileInput(file_name);
            byte[] bytes = new byte[fin.available()];

            fin.read(bytes);
            data = new String(bytes);

            if (fin != null) fin.close();
        } catch (IOException ex) {
            Toast.makeText(context, file_name + " не найден", Toast.LENGTH_SHORT).show();
        }

        return data;
    }

    //Создать файл
    public static void writeTextToFile(Context context, String file_name, String text) {
        try {
            FileOutputStream fos = context.openFileOutput(file_name, context.MODE_PRIVATE);

            fos.write(text.getBytes());
            Toast.makeText(context, "Файл сохранен", Toast.LENGTH_SHORT).show();

            if(fos!=null) fos.close();
        }
        catch(IOException ex) {
            Toast.makeText(context, "Ошибка: файл не создан", Toast.LENGTH_SHORT).show();
        }
    }
}
