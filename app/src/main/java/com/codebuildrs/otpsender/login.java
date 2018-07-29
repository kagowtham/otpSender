package com.codebuildrs.otpsender;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class login extends AppCompatActivity {
    int count;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences=getSharedPreferences("status",Context.MODE_PRIVATE);
        if(preferences.getBoolean("isEnter",false)){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        final EditText editText=findViewById(R.id.name_et);
        button=findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=0;
                execute(editText.getText().toString());
                if(editText.getText().length()>0)
                button.setEnabled(false);
            }
        });
    }

    void execute(final String token){
        String URL = "https://famousfoodnearby.herokuapp.com/Token";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                switch (response) {
                    case "true":
                        SharedPreferences preferences = getSharedPreferences("status", Context.MODE_PRIVATE);
                        preferences.edit().putBoolean("switch", true).apply();
                        preferences.edit().putBoolean("isEnter",true).apply();
                        preferences.edit().putString("name",token.trim()).apply();
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                        if (TextUtils.equals(Build.MANUFACTURER, "Xiaomi")){
                            Intent intent1 = new Intent();
                            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                            try {
                                startActivity(intent1);
                            }catch (ActivityNotFoundException e){
                                e.printStackTrace();
                            }
                        }
                        finish();
                        break;
                    case "available":
                        Toast.makeText(getApplicationContext(), "token name already available", Toast.LENGTH_SHORT).show();
                        button.setEnabled(true);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "network response error", Toast.LENGTH_SHORT).show();
                        button.setEnabled(true);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                count++;
                Log.e("VOLLEY", error.toString());
                Toast.makeText(getApplicationContext(),"connection error and retrying "+count,Toast.LENGTH_SHORT).show();
                if(count<5){
                    execute(token);
                }else{
                    button.setEnabled(true);
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> m=new HashMap<>();
                m.put("id",FirebaseInstanceId.getInstance().getToken());
                m.put("name",token);
                return m;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                 1500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(FirebaseInstanceId.getInstance().getToken()!=null&&!token.trim().isEmpty())
            MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
