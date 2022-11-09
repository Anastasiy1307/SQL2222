package com.example.sql;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Adapter pAdapter;
    private final List<mask> ListMotos=new ArrayList<>();
    Spinner spinnerFilter;
    String [] Filter={"Без фильтрации","По возрастанию","По убыванию"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView motoView=findViewById(R.id.ListMotos);
        pAdapter=new Adapter(MainActivity.this,ListMotos);
        motoView.setAdapter(pAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Filter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        configurationNextButton();
        spinnerFilter=findViewById(R.id.filter);
        spinnerFilter.setAdapter(adapter);



        new GetMoto().execute();
    }

    private void configurationNextButton()
    {
        ImageButton addData = findViewById(R.id.btadd);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ADD.class));
            }
        });
    }

    private class GetMoto extends AsyncTask<Void,Void,String>
    {


        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL ( "https://ngknn.ru:5001/NGKNN/ВласоваАС/api/motoes");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result=new StringBuilder();
                String line="";

                while ((line=reader.readLine())!=null){
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                {
                    JSONArray tempArray= new JSONArray(s);
                    for (int i=0;i<tempArray.length();i++)
                    {
                        JSONObject productJson=tempArray.getJSONObject(i);
                        mask tempMoto = new mask(
                                productJson.getInt("ID"),
                                productJson.getString("Image"),
                                productJson.getString("Name"),
                                productJson.getString("Speed"),
                                productJson.getString("Power")
                        );
                        ListMotos.add(tempMoto);
                        pAdapter.notifyDataSetInvalidated();
                    }
                }
            }
            catch (Exception ignored)
            {

            }
        }
    }


}