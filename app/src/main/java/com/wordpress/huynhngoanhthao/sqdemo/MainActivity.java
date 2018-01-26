package com.wordpress.huynhngoanhthao.sqdemo;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    Database mDatabase;

    ListView mLvCongViec;
    ArrayList<CongViec> mCongViecs;
    CongViecAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLvCongViec = (ListView) findViewById(R.id.listView);
        mCongViecs = new ArrayList<>();

        adapter = new CongViecAdapter(mCongViecs, R.layout.dong_layout, this);
        mLvCongViec.setAdapter(adapter);

        // tao database Ghi chu
        mDatabase = new Database(this, "ghichu.sqlite", null, 1);

        //tao bang Cong viec
        mDatabase.
                queryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");
        //insert data
        //mDatabase.
       //        queryData("INSERT INTO CongViec VALUES(null, 'Làm bài tập Android')");

        //select data
        selectData();


    }

    private void selectData() {
        Cursor dataCongViec = mDatabase.GetData("select * from CongViec");
        mCongViecs.clear();
        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            mCongViecs.add(new CongViec(id, ten));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.them) {
            dialogAdd();
        }

        return super.onOptionsItemSelected(item);
    }

    private void dialogAdd(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);
        dialog.setCanceledOnTouchOutside(false);

        final EditText editText = (EditText) dialog.findViewById(R.id.themMoTa);
        Button btnThem = (Button) dialog.findViewById(R.id.add);
        Button btnHuy = (Button) dialog.findViewById(R.id.cancel);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = editText.getText().toString();
                if(tencv.equals("")) {
                    Toast.makeText(MainActivity.this, "Blank field is not accept", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabase.
                            queryData("INSERT INTO CongViec VALUES(null, '" + tencv + "')");
                    Toast.makeText(MainActivity.this, "Insert Success !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    selectData();
                }
            }
        });

        dialog.show();
    }

    public void updateWork(String cv, final int position){
        // init dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        Button btnHuy = (Button) dialog.findViewById(R.id.cancel);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText edtUpdate = (EditText) dialog.findViewById(R.id.themMoTa);
        edtUpdate.setText(cv);
        // place cursor end of line
        edtUpdate.setSelection(edtUpdate.getText().length());

        final EditText edtCapNhat = (EditText) dialog.findViewById(R.id.themMoTa);
        Button btnCapNhat = (Button) dialog.findViewById(R.id.update);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateText = edtCapNhat.getText().toString();
                mDatabase.queryData("update CongViec set TenCV = '"+updateText+"' where Id = " + String.valueOf(position));

                selectData();
                dialog.dismiss();
            }
        });
    }

    public void removeRow(final int pos, String tenCv){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.show();

        Button btnDelete = (Button) dialog.findViewById(R.id.delete);
        Button btnNoDelete = (Button) dialog.findViewById(R.id.nodelete);
        TextView textView = (TextView) dialog.findViewById(R.id.txtMoTa);

        textView.setText("You really want to remove \""+ tenCv +"\" memo ?");

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.queryData("delete from CongViec where Id = " + String.valueOf(pos));
                selectData();
                dialog.dismiss();
            }
        });

        btnNoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
