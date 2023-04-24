package com.example.bf_test.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.bf_test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MytankAdapter extends BaseExpandableListAdapter {
    private String[] veh;
    private ArrayList<List<Map<String,Object>>> arrayList;
    private Context context;
    public MytankAdapter(String[] veh,ArrayList<List<Map<String,Object>>> arrayList,Context context){
        this.veh=veh;
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public int getGroupCount() {
        return veh.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return veh[groupPosition];
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
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group_weapons, null);
        }
        TextView textView = convertView.findViewById(R.id.weapons_group_text);
        textView.setText(veh[groupPosition]);
        return convertView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_weapons, null);
        }
        TextView name = convertView.findViewById(R.id.veh_name);
        TextView kills = convertView.findViewById(R.id.veh_kills);
        TextView KPM = convertView.findViewById(R.id.veh_kpm);
        TextView time = convertView.findViewById(R.id.veh_time);
        TextView damage = convertView.findViewById(R.id.veh_damage);
        TextView zg = convertView.findViewById(R.id.veh_assists);
        TextView juli = convertView.findViewById(R.id.veh_distance);
        TextView ch = convertView.findViewById(R.id.veh_ch);
        TextView road = convertView.findViewById(R.id.veh_roadkill);
        ImageView imageView = convertView.findViewById(R.id.veh_img);
        juli.setText(arrayList.get(groupPosition).get(childPosition).get("distance").toString());
        ch.setText(arrayList.get(groupPosition).get(childPosition).get("ch").toString());
        road.setText(arrayList.get(groupPosition).get(childPosition).get("roadKills").toString());
        zg.setText(arrayList.get(groupPosition).get(childPosition).get("assists").toString());
        damage.setText(arrayList.get(groupPosition).get(childPosition).get("damage").toString());
        name.setText(arrayList.get(groupPosition).get(childPosition).get("name").toString());
        kills.setText(arrayList.get(groupPosition).get(childPosition).get("kills").toString());
        KPM.setText(arrayList.get(groupPosition).get(childPosition).get("KPM").toString());
        int time1 =  Integer.valueOf(arrayList.get(groupPosition).get(childPosition).get("time").toString());
        if (time1/60/60>=1) time.setText((time1/60/60)+" 时");
        else if (time1/60>=1&time1/60/60<1) time.setText((time1/60)+" 分");
        else if (time1/60<1) time.setText((time1)+" 秒");
        else time.setText("无");
        String url = arrayList.get(groupPosition).get(childPosition).get("img").toString();
        Glide.with(context)
                .load(url)
                .into(imageView);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
