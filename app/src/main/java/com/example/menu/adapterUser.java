package com.example.menu;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterUser extends BaseAdapter {
    MainActivity context;
    int layout;
    ArrayList<User> arrayList;

    public adapterUser(MainActivity context, int layout, ArrayList<User> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(layout,null);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtPhone = view.findViewById(R.id.txtPhone);
        ImageView img = view.findViewById(R.id.img_menu);

        User temp = arrayList.get(i);
        txtName.setText(temp.getName());
        txtPhone.setText(temp.getPhone());
        int id = temp.getId();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,img);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.btnSua:
                                //context.LoadItem(id);
                                context.Update(id);
                                context.showData();
                                break;
                            case R.id.btnXoa:
                                context.Delete(id);
                                context.showData();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return view;
    }
}
