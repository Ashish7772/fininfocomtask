package com.example.fininfocomtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText email,number;
    Button submit,save;
    RecyclerView recyclerView;
    ArrayList<recyclerModel> itemList = new ArrayList<>();
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        number = findViewById(R.id.phone);
        submit = findViewById(R.id.submit);
        save = findViewById(R.id.save);
        recyclerView = findViewById(R.id.recyclerView);
        loadData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CustomAdapter(itemList);
        recyclerView.setAdapter(adapter);

        keyboardhide();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                itemList.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(recyclerView);
        submit.setOnClickListener(view -> {
            String e =email.getText().toString();
            String s =number.getText().toString();

            if (TextUtils.isEmpty(e))
            {
                email.setError("Email is Required");
                return;
            }
            if (TextUtils.isEmpty(s))
            {
               number.setError("Number is required");
            }
            if (number.length() < 10 || number.length()>10) {

                Toast.makeText(MainActivity.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    String email1 = email.getText().toString();
                    String phone1 = number.getText().toString();
                    recyclerModel rModel = new recyclerModel(email1,phone1);
                    itemList.add(rModel);
                }catch (NumberFormatException ex){

                }
                email.setText("");
                number.setText("");
            }


        });

        save.setOnClickListener(view -> {
            saveData();
        });

    }

    private void keyboardhide() {

        //keyboard hide when click outside the edittext
        findViewById(R.id.layoutkeyboard).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(MainActivity.this.getWindow().getDecorView().getRootView().getWindowToken(), 0);
                return true;
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString("task list",json);
        editor.apply();
        Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show();
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<recyclerModel>>() {}.getType();
        itemList = gson.fromJson(json,type);

        if (itemList == null){
            itemList = new ArrayList<>();
        }
    }
}