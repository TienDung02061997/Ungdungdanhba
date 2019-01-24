package com.example.nguyentiendung.ungdungdanhba;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.nguyentiendung.ungdungdanhba.Adapter.ContactAdapter;
import com.example.nguyentiendung.ungdungdanhba.model.contact;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<contact> contactArray;
    EditText edname;
    EditText ednumber;
    RadioButton rdbtnmale;
    RadioButton rbbtnfemale;
    Button btnaddcontact;
    ListView lvcontact;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkpermission();
        setwiget();
        setClicks();
        contactArray =new ArrayList<>();
        contactAdapter = new ContactAdapter(this, R.layout.item_contact_listview, contactArray);
        //add adapter vao listview

        lvcontact.setAdapter(contactAdapter);
    }

    public void setwiget() {
        edname = (EditText) findViewById(R.id.ed_name);
        ednumber = (EditText) findViewById(R.id.ed_number);
        rdbtnmale = (RadioButton) findViewById(R.id.rdtn_male);
        rbbtnfemale = (RadioButton) findViewById(R.id.rbtn_female);
        btnaddcontact = (Button) findViewById(R.id.btn_add_contact);
        lvcontact = (ListView) findViewById(R.id.lv_contact);
        lvcontact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showcomfirmdialog(position);
            }
        });
    }

    public void setClicks() {
        btnaddcontact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_contact) {
            //trim bỏ khoảng trắng
            String name = edname.getText().toString().trim();
            String number = ednumber.getText().toString().trim();

            boolean isMale = true;
            if (rdbtnmale.isChecked()) {
                isMale = true;
            } else {
                isMale = false;
            }
            //TextUtils.isEmpty(name) = name.equals("")==true (rong)
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Please Input name or number", Toast.LENGTH_SHORT).show();
            } else {
                contact ct = new contact(isMale, name, number);
                contactArray.add(ct);

            }

            //cap nhat adapter
            contactAdapter.notifyDataSetChanged();
        }

    }
  public void showcomfirmdialog(final int position){
        Dialog dialog =new Dialog(MainActivity.this);
        //setview dialog se hien thi
      dialog.setContentView(R.layout.customer_alterdialog_listview);
      Button call= (Button) dialog.findViewById(R.id.btn_call);
      Button sendmessger= (Button) dialog.findViewById(R.id.btn_sendmesseger);
      call.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              // position dung de lay ra so dien thoai tai vi tri position
              intentCall(position);
          }
      });
      sendmessger.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             intentsendmesseger(position);
          }
      });
      dialog.show();
  }
    public  void intentCall(int position){
        Intent intent =new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+contactArray.get(position).getmNumber()));
        startActivity(intent);
    }
  public  void intentsendmesseger(int position){
      Intent intent =new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+contactArray.get(position).getmNumber()));
      startActivity(intent);
  }

    //xin quyen truy cap van phai xin quyen ben AndroidManifest

    private  void checkpermission(){
        String[] permissions =new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,

        };
        List<String> listPermission=new ArrayList<String>();
        for(String  permission :permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermission.add(permission);
            }
        }
        //
        if (!listPermission.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermission.toArray(new String[listPermission.size()]),1);
        }
    }

}
