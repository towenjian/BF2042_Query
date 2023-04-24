package com.example.bf_test.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bf_test.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyWeaponsAdpeter extends BaseExpandableListAdapter {
    private List<Map<String,Object>> mapList_group;
    private ArrayList<List<Map<String,Object>>> arrayList;
    private Context mContext;
    public MyWeaponsAdpeter(List<Map<String,Object>> mapList_group,ArrayList<List<Map<String,Object>>> arrayList,Context mContext){
        this.mapList_group=mapList_group;
        this.arrayList=arrayList;
        this.mContext=mContext;
    }
    @Override
    public int getGroupCount() {
        return mapList_group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mapList_group.get(groupPosition);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.list_group_weapons, parent,false);
        }
        TextView weapons_name = (TextView) convertView.findViewById(R.id.weapons_group_text);
        ImageView weapons_images = (ImageView) convertView.findViewById(R.id.weapons_images);
        weapons_name.setText(mapList_group.get(groupPosition).get("gun").toString());
//        weapons_images.setImageDrawable((Drawable) mapList_group.get(groupPosition).get("images"));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(R.layout.weapons_layout, parent,false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.weapons_name);
        TextView kills = (TextView) convertView.findViewById(R.id.weapons_kill);
        TextView KPM = (TextView) convertView.findViewById(R.id.weapons_kpm);
        TextView head = (TextView) convertView.findViewById(R.id.weapons_headkills);
        TextView acc = (TextView) convertView.findViewById(R.id.weapons_accuracy);
        TextView damage = (TextView) convertView.findViewById(R.id.weapons_damage);
        TextView headshotKills = (TextView) convertView.findViewById(R.id.weapons_headshotKill);
        TextView shotf = (TextView) convertView.findViewById(R.id.weapons_shotsFired);
        TextView time = (TextView) convertView.findViewById(R.id.weapons_time);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.weapons_img);
        acc.setText(arrayList.get(groupPosition).get(childPosition).get("acc").toString());
        name.setText(arrayList.get(groupPosition).get(childPosition).get("name").toString());
        kills.setText(arrayList.get(groupPosition).get(childPosition).get("kills").toString());
        KPM.setText(arrayList.get(groupPosition).get(childPosition).get("KPM").toString());
        head.setText(arrayList.get(groupPosition).get(childPosition).get("headshots").toString());
        damage.setText(arrayList.get(groupPosition).get(childPosition).get("damage").toString());
        headshotKills.setText(arrayList.get(groupPosition).get(childPosition).get("headshotKills").toString());
        shotf.setText(arrayList.get(groupPosition).get(childPosition).get("shotsFired").toString());
        double time_1 = Float.parseFloat(arrayList.get(groupPosition).get(childPosition).get("time").toString());
        time_1/=3600;
        BigDecimal b = new BigDecimal(time_1);
        time_1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        time.setText((time_1)+"小时");
        String imgurl = arrayList.get(groupPosition).get(childPosition).get("img").toString();
        Glide.with(mContext)
                .load(imgurl)
                .into(imageView);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
