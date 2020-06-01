package com.example.qrapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private Button boton;
    public static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton = findViewById(R.id.boton);
        thisContext = this;
        boton.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
            if (result.getContents() != null){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.getString("response").equals("OK")){
                                Toast.makeText(thisContext, "Todo bien ", Toast.LENGTH_LONG).show();

                            }else if(jsonResponse.getString("response").equals("IDENT")){
                                Toast.makeText(thisContext, "Todo mal", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(thisContext, "Todo peor", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ConstancyReuest constancyReuest = new ConstancyReuest(result.getContents(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(constancyReuest);
                Toast.makeText(thisContext, "El código es:  "+result.getContents(), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(thisContext, "Error al leer el código", Toast.LENGTH_LONG).show();
            }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.boton:
                    new IntentIntegrator(MainActivity.this).initiateScan();
                    break;
            }
        }
    };
}