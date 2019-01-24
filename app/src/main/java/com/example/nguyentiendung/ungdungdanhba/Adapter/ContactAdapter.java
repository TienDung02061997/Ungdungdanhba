package com.example.nguyentiendung.ungdungdanhba.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyentiendung.ungdungdanhba.R;
import com.example.nguyentiendung.ungdungdanhba.model.contact;

import java.util.ArrayList;


public class ContactAdapter extends ArrayAdapter<contact> {
    ///activity hien tai
    private  Context context;
    private  int resource;
    private ArrayList<contact> contactList;

    public ContactAdapter( Context context, int resource,  ArrayList<contact> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.contactList=objects;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Viewhoder viewhoder;
        if(convertView ==null){
            viewhoder =new Viewhoder();
            //convertView tuong trung cho item_contact_listview
            convertView=LayoutInflater.from(context).inflate(R.layout.item_contact_listview,parent,false);
            viewhoder.imageAvatar= (ImageView) convertView.findViewById(R.id.img_avatar);
            viewhoder.tvName= (TextView) convertView.findViewById(R.id.tv_name);
            viewhoder.tvNumber= (TextView) convertView.findViewById(R.id.tv_number);
           //lan dau thi viewhoder se giu cac view con roi convertView giư view hoder
            convertView.setTag(viewhoder);
        }
        else{
             //viewhoder lấy ra view con từ convert view đã khởi tạo phía tren
            viewhoder= (Viewhoder) convertView.getTag();
        }
        //lay  ra gia tri tai vi tri position
        contact contacts=contactList.get(position);

        viewhoder.tvName.setText(contacts.getmName());
        viewhoder.tvNumber.setText(contacts.getmNumber());
        if (contacts.isMale()){
            viewhoder.imageAvatar.setBackgroundResource(R.drawable.male);
        }
        else {
            viewhoder.imageAvatar.setBackgroundResource(R.drawable.female);
        }

        return convertView;

    }
//chua cac thành phần của view item
    private static class Viewhoder {
          ImageView imageAvatar;
          TextView tvName;
          TextView tvNumber;
    }
}
