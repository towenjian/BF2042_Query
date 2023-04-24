package com.example.bf_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.bf_test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyclassesAdapter extends BaseExpandableListAdapter {
    private String[] classes_name;
    private ArrayList<List<Map<String,Object>>> arrayList;
    private Context context;
    public MyclassesAdapter(String[] classes_name,ArrayList<List<Map<String,Object>>> arrayList,Context context){
        this.classes_name=classes_name;
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public int getGroupCount() {
        return classes_name.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classes_name[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group_weapons, null);
        }
        TextView classes_setname = convertView.findViewById(R.id.weapons_group_text);
        classes_setname.setText(classes_name[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.classer_layout, null);
        }
        TextView classes_Name = convertView.findViewById(R.id.classer_name);
        TextView classes_kill = convertView.findViewById(R.id.classer_kills);
        TextView classes_KPM = convertView.findViewById(R.id.classer_kpm);
        TextView classes_time = convertView.findViewById(R.id.classer_time);
        TextView classes_kd = convertView.findViewById(R.id.classer_kd);
        classes_Name.setText(arrayList.get(groupPosition).get(childPosition).get("name").toString());
        classes_kill.setText(arrayList.get(groupPosition).get(childPosition).get("kills").toString());
        classes_KPM.setText(arrayList.get(groupPosition).get(childPosition).get("KPM").toString());
        classes_kd.setText(arrayList.get(groupPosition).get(childPosition).get("kd").toString());
        int time = (int) arrayList.get(groupPosition).get(childPosition).get("time");
        if (time/60>=1) classes_time.setText((time/60)+" 时");
        else if (time>1&time/60<1)classes_time.setText((time)+" 分");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
