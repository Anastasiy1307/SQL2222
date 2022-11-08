package com.example.sql;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ADD extends AppCompatActivity {

    String img="";
    private ImageView imageButton;
    private EditText Name,Speed, Power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageButton=findViewById(R.id.ImgBut);
        Name=findViewById(R.id.edtMarka);
        Speed=findViewById(R.id.edtSpeed);
        Power=findViewById(R.id.edtPower);

    }

    public void onClickChooseImage(View view)
    {
        getImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageButton.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img= Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return "";
    }


    public  void Add(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(ADD.this);
        builder.setTitle("Добвить")
                .setMessage("Вы уверены что хотите добавить данные")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Name.getText().length()==0 || Speed.getText().length()==0 || Power.getText().length()==0)
                        {
                            Toast.makeText(ADD.this, "Не заполненны обязательные поля", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            if (img=="")
                            {
                                img=null;
                                postAdd(img,Name.getText().toString(),Speed.getText().toString(), Power.getText().toString());
                            }
                            else
                            {
                                postAdd(img,Name.getText().toString(),Speed.getText().toString(),Power.getText().toString());
                            }
                            Next();
                        }

                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void postAdd(String image, String name,String speed, String power)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/ВласоваАС/api/motoes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        DataModal modal = new DataModal(image, name, speed, power);

        Call<DataModal> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(ADD.this, "Запись добавлена", Toast.LENGTH_SHORT).show();
                Name.setText("");
                Speed.setText("");
                Power.setText("");
                imageButton.setImageResource(R.drawable.gluxo);
                DataModal responseFromAPI = response.body();

            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }

    public void Next()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btBack(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}