package com.example.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtName, edtPhone;
    ListView listView;
    ArrayList<User> arrayList;
    adapterUser adt;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        createDatabase();
         arrayList = new ArrayList<>();
         adt = new adapterUser(MainActivity.this,R.layout.item,arrayList);
         listView.setAdapter(adt);
         showData();
        // showItem();
    }

//    public void showItem() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                User temp =
//            }
//        });
//    }

    public void showData() {
        arrayList.clear();
        String sql = "SELECT * FROM tbUser";
        Cursor cs = database.rawQuery(sql,null);
        cs.moveToFirst();
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String phone = cs.getString(2);
            arrayList.add(new User(id,name,phone));
        }
        adt.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.iconSave:
                insert();
                showData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void insert() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();

        if(name.isEmpty() || phone.isEmpty()){
            Toast.makeText(MainActivity.this,"Du liệu k hợp lệ",Toast.LENGTH_LONG).show();
        }else {
            String sql = "INSERT INTO tbUser(name,phone) VALUES ('"+name+"', '"+phone+"')";
            database.execSQL(sql);
            Toast.makeText(MainActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
        }
    }

    public void createDatabase(){
        database = openOrCreateDatabase("DanhBa",MODE_PRIVATE,null);
        String sql = "CREATE TABLE IF NOT EXISTS tbUser(id integer primary key autoincrement, name text, phone text)";
        database.execSQL(sql);
    }

    private void AnhXa() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        listView = findViewById(R.id.lvTT);
    }

    public void LoadItem(int id){
        User temp = arrayList.get(id);
        // String n = temp.getName();
        //temp.setPhone();
        edtName.setText(temp.getName());
        edtPhone.setText(temp.getPhone());
    }
    public void Update(int id) {

        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        if(name.isEmpty() || phone.isEmpty()){
            Toast.makeText(MainActivity.this,"Du liệu k hợp lệ",Toast.LENGTH_LONG).show();
        }else {
            String sql = " UPDATE tbUser SET name = '"+name+"',phone = '"+phone+"' where id =" + id;
            database.execSQL(sql);
            //database.update("tbUser",)
            Toast.makeText(MainActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
        }
    }

    public void Delete(int id) {
        String sql = " DELETE FROM tbUser where id =" + id;
        database.execSQL(sql);
    }
}