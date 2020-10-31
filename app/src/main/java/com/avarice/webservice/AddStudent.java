package com.avarice.webservice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {

    final String urlInsert = "http://192.168.0.100/androidwebservice/insert.php";

    EditText edtName, edtBorn, edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        edtName = findViewById(R.id.edtName);
        edtBorn = findViewById(R.id.edtBorn);
        edtAddress = findViewById(R.id.edtAddress);
    }

    public void clickButtonAdd(View view) {
        String name = edtName.getText().toString().trim();
        String born = edtBorn.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        if (name.isEmpty() || born.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please add full data", Toast.LENGTH_SHORT).show();
        } else {
            addNewStudent(urlInsert, name, born, address);
        }
    }

    public void clickButtonCancelAdd(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void addNewStudent(String url, String name, String born, String address) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.trim().equals("succeeded")) {
                        Toast.makeText(this, "Succeeded!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    Log.d("RETURN", "Error");
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("inputName", name);
                params.put("inputBorn", born);
                params.put("inputAddress", address);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}