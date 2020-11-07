package com.avarice.webservice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    final String urlGetData = "http://192.168.0.101/androidwebservice/getdata.php";
    final String urlDelete = "http://192.168.0.101/androidwebservice/delete.php";
    public static final int REQUEST_CODE = 123;

    ListView listStudent;
    ArrayList<Student> students;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        listStudent = findViewById(R.id.listViewStudent);
        students = new ArrayList<>();
        adapter = new StudentAdapter(this, R.layout.line_student, students);

        listStudent.setAdapter(adapter);
    }

    private void getData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                response -> {
                    students.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            students.add(new Student(
                                    object.getInt("id"),
                                    object.getString("name"),
                                    object.getInt("born"),
                                    object.getString("address")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonArrayRequest);
    }

    public void clickBtnGet(View view) {
        getData(urlGetData);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            startActivityForResult(new Intent(MainActivity.this, AddStudent.class), REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            getData(urlGetData);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void deleteStudent(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                response -> {
                    if (response.trim().equals("succeeded")) {
                        Toast.makeText(this, "Succeeded!", Toast.LENGTH_SHORT).show();
                        getData(urlGetData);
                    }
                },
                error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    Log.d("RETURN", "Error");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("inputId", String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_student, menu);
        return super.onCreateOptionsMenu(menu);
    }
}