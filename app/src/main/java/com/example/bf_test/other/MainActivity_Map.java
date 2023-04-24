package com.example.bf_test.other;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.bf_test.R;
import com.example.bf_test.databinding.ActivityMainMapBinding;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_Map extends AppCompatActivity {
    ActivityMainMapBinding binding;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> list;
    private Map<String,Object> map_name = new HashMap<>();
    private ArrayList<List<Map<String,Object>>> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String maps = intent.getStringExtra("maps");
        Log.d("map", maps);
        setMap_name();
        try {
            JSONArray jsonArray = new JSONArray(maps);
            list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String,Object> map = new HashMap<>();
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                if (map_name.containsKey(jsonObject.getString("mapName"))) map.put("mapName", map_name.get(jsonObject.getString("mapName")).toString());
                else map.put("mapName", jsonObject.getString("mapName"));
                map.put("mapimg", jsonObject.getString("image"));
                map.put("wins", jsonObject.getString("wins"));
                map.put("losses", jsonObject.getString("losses"));
                map.put("winPercent", jsonObject.getString("winPercent"));
                int time = Integer.parseInt(jsonObject.getString("secondsPlayed"));
                if (time/60<1)map.put("time", time+"秒");
                else if (time/60>=1&time/60/60<1)map.put("time", (time/60)+"分");
                else if (time/60/60>=1&time/60/60/24<1)map.put("time", (time/60/60)+"时");
                else map.put("time", (time/60/60/24)+"天");
                map.put("matches", jsonObject.getString("matches"));
                list.add(map);
            }
            Log.d("map",list.toString());
            //排序
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size() - 1; j++) {
                    if (Integer.parseInt(list.get(j).get("matches").toString())<Integer.parseInt(list.get(j+1).get("matches").toString())){
                        list.add(j, list.get(j+1));
                        list.remove(j+2);
                    }
                }
            }
            simpleAdapter = new SimpleAdapter(this,list,R.layout.map_listview,new String[]{"mapName","wins","losses","winPercent","matches","time","mapimg"},new int[]{R.id.map_name,R.id.map_win,R.id.map_lose,R.id.map_winparet,R.id.map_match,R.id.map_time,R.id.map_img}){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    ImageView imageView = view.findViewById(R.id.map_img);
                    String imgurl = list.get(position).get("mapimg").toString();
                    Glide.with(MainActivity_Map.this)
                            .load(imgurl)
                            .into(imageView);
                    return view;
                }
            };
            binding.Map.setAdapter(simpleAdapter);
            Log.d("map",list.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ImageButton imageButton = findViewById(R.id.title_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setMap_name(){
        map_name.put("Spearhead", "急先锋");
        map_name.put("Stranded", "搁浅");
        map_name.put("Renewal","涅槃");
        map_name.put("Orbital","航天发射中心");
        map_name.put("Manifest", "货物仓单");
        map_name.put("Discarded", "废弃之地");
        map_name.put("Kaleidescope", "万花筒");
        map_name.put("Breakaway", "分崩离析");
        map_name.put("Hourglass", "沙漏");
        map_name.put("Exposure","曝光");
        map_name.put("Noshahr Canals", "诺沙运河");
        map_name.put("Caspian Border", "里海边境");
        map_name.put("Valparaiso", "瓦尔帕莱索");
        map_name.put("Arica Harbor", "阿里卡港");
        map_name.put("Battle of the Bulge", "突出部战役");
        map_name.put("El Alamein", "阿拉曼");
        map_name.put("Flashpoint", "焦点");
        Log.d("map", map_name.toString());
        Log.d("map", map_name.size()+"");
    }
}