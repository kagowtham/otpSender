package com.codebuildrs.otpsender;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS},
                1);
        SharedPreferences preferences = getSharedPreferences("status", Context.MODE_PRIVATE);
        name=preferences.getString("name","");
        TextView textView=findViewById(R.id.text_show);
      textView.setText(
              "token name is "+name+"");
        TextView textView1=findViewById(R.id.link);
        textView1.setText("https://famousfoodnearby.herokuapp.com/Send?name="+name+"&to=9999999999&msg=some+messages+here");
        Button button=findViewById(R.id.button_example);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Examples.class);
                startActivity(intent);
            }
        });
        Button button1=findViewById(R.id.button_rewrite);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = "https://famousfoodnearby.herokuapp.com/DeleteToken";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        switch (response) {
                            case "true":
                                SharedPreferences preferences = getSharedPreferences("status", Context.MODE_PRIVATE);
                                preferences.edit().putBoolean("isEnter",false).apply();
                                Intent intent=new Intent(getApplicationContext(),login.class);
                                startActivity(intent);
                                finish();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "network response error", Toast.LENGTH_SHORT).show();

                                break;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                        Toast.makeText(getApplicationContext(),"network error",Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> m=new HashMap<>();
                        m.put("id",FirebaseInstanceId.getInstance().getToken());
                        return m;
                    }
                };
                    MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
        Switch switch1=findViewById(R.id.switch1);
        switch1.setChecked(preferences.getBoolean("switch",false));
        final SharedPreferences finalPreferences = preferences;
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                finalPreferences.edit().putBoolean("switch",b).apply();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"permission given",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this," give permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
