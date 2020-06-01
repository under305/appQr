package com.example.qrapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ConstancyReuest extends StringRequest {

    public static String url = "http://192.168.1.75/constancias-tec/Controllers/mainController.php?opt=consultConstancy";
    private Map<String, String> params;
    public ConstancyReuest(String cadena, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("identifier", "identificadordepruebas");
        params.put("id", cadena);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
