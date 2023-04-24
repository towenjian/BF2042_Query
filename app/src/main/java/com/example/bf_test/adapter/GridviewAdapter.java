package com.example.bf_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bf_test.R;

import java.util.ArrayList;

public class GridviewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> arrayList;
    public GridviewAdapter(Context context,ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.gridview_item, null);
        }
        TextView textView = convertView.findViewById(R.id.grid_text);
        textView.setText(arrayList.get(position));
        return convertView;
    }
}
