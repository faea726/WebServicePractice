package com.avarice.webservice;

import android.content.Intent;
import android.os.Bundle;
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

public class UpdateStudentActivity extends AppCompatActivity {

    String urlUpdate = "http://192.168.0.101/androidwebservice/update.php";
    EditText edtName, edtBorn, edtAddress;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("dataStudent");
        id = student.getId();
        edtName = findViewById(R.id.edtNameUpdate);
        edtBorn = findViewById(R.id.edtBornUpdate);
        edtAddress = findViewById(R.id.edtAddressUpdate);
        edtName.setText(student.getName());
        edtBorn.setText(String.valueOf(student.getBorn()));
        edtAddress.setText(student.getAddress());
    }

    public void clickBtnCancelUpdate(View view) {
        finish();
    }

    public void clickBtnUpdate(View view) {
        String name = edtName.getText().toString().trim();
        String born = edtBorn.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        if (name.equals("") || born.equals("") || address.equals("")) {
            Toast.makeText(this, "Please fill the text", Toast.LENGTH_SHORT).show();
        } else {
            updateData(name, born, address);
        }
    }

    private void updateData(String name, String born, String address) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate,
                response -> {
                    if (response.trim().equals("succeeded")) {
                        Toast.makeText(this, "Succeeded!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }, error -> Toast.makeText(this, "Error! Please try again.", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("inputId", String.valueOf(id));
                params.put("inputName", name);
                params.put("inputBorn", born);
                params.put("inputAddress", address);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}