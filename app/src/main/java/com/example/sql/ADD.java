package com.example.sql;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ADD extends AppCompatActivity {

    private EditText Name, Speed, Power;
    private Button Add;
    private ImageView imageButton;
    String Img = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        configureBackButton();
        Name = findViewById(R.id.edtMarka);
        Speed = findViewById(R.id.edtSpeed);
        Power = findViewById(R.id.edtPower);
        Add = findViewById(R.id.AddAdd);
        imageButton = findViewById(R.id.ImgBut);

    }

    public void Add(View v) {
        if (Speed.getText().length() == 0 || Power.getText().length() == 0 || Name.getText().length() == 0) {
            Toast.makeText(ADD.this, "Не заполненны обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (Img == "") {
                Img = null;
                postData(Img, Name.getText().toString(), Speed.getText().toString(), Power.getText().toString());
            } else {
                postData(Img, Name.getText().toString(), Speed.getText().toString(), Power.getText().toString());
            }
            finish();
        }

    }

    public void Next() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void onClickChooseImage(View view) {
        getImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {
                Log.d("MyLog", "Image URI : " + data.getData());
                imageButton.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage() {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Img = Base64.getEncoder().encodeToString(bytes);
            return Img;
        }
        return "";
    }

    private void configureBackButton() {
        Button back = (Button) findViewById(R.id.addBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void postData(String name, String speed, String power, String Image) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ВласоваАС/api/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        DataModal modal = new DataModal(name, speed, power, Image);

        Call<DataModal> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(ADD.this, "Данные добавлены", Toast.LENGTH_SHORT).show();

                Name.setText("");
                Speed.setText("");
                Power.setText("");
                DataModal responseFromAPI = response.body();

            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
            }
        });
    }
}