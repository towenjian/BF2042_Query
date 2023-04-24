package com.example.bf_test.other;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bf_test.R;
import com.example.bf_test.adapter.MyWeaponsAdpeter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_Weapons extends AppCompatActivity {
    private MyWeaponsAdpeter myWeaponsAdpeter;
    private List<Map<String,Object>> mapList_group;
    private ArrayList<List<Map<String,Object>>> arrayList = new ArrayList<>();
    private List<Map<String,Object>> maps1;
    private int[] images = {
            R.drawable.weapons_cf,
            R.drawable.weapon_tj,
            R.drawable.weapon_jq,
            R.drawable.weapon_jj,
            R.drawable.weapon_bzd,
            R.drawable.weapon_dgn,
            R.drawable.weapon_sq
    };
    com.example.bf_test.databinding.ActivityMainWeaponsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.bf_test.databinding.ActivityMainWeaponsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String weapons_datas_temp = intent.getStringExtra("weapons");
        mapList_group = new ArrayList<>();
        String[] weapons_title = {"冲锋枪","突击步枪","大机枪","狙击步枪","半自动","多功能","手枪"};
        String[] weapons_name = {"PDW","Assault Rifles","LMG","Bolt Action","DMR","Shotguns","Sidearm"};
        for (int i = 0; i < weapons_title.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("gun",weapons_title[i]);
            map.put("images", images[i]);
            mapList_group.add(map);
        }

        try {
            JSONArray weapons_data = new JSONArray(intent.getStringExtra("weapons"));
            for (int i = 0; i < weapons_name.length; i++) {
                maps1 = new ArrayList<>();
                for (int j = 0; j < weapons_data.length(); j++) {
                    Map<String,Object> maps = new HashMap<>();
                    JSONObject jsonObject = (JSONObject) weapons_data.get(j);
                    switch (i){
                        case 5:{
                            if (jsonObject.getString("type").equals("Railguns")){
                                maps.put("name", jsonObject.getString("weaponName"));
                                maps.put("kills", jsonObject.getString("kills"));
                                maps.put("KPM", jsonObject.getString("killsPerMinute"));
                                maps.put("headshots", jsonObject.getString("headshots"));
                                maps.put("img", jsonObject.getString("image"));
                                maps.put("acc", jsonObject.getString("accuracy"));
                                maps.put("damage", jsonObject.getString("damage"));
                                maps.put("headshotKills", jsonObject.getString("headshotKills"));
                                maps.put("shotsFired", jsonObject.getString("shotsFired"));
                                maps.put("time", jsonObject.getString("timeEquipped"));
                                maps1.add(maps);break;
                            }
                        }
                    }
                    if(jsonObject.getString("type").equals(weapons_name[i])&Integer.parseInt(jsonObject.getString("timeEquipped"))>0){
                        maps.put("name", jsonObject.getString("weaponName"));
                        maps.put("kills", jsonObject.getString("kills"));
                        maps.put("KPM", jsonObject.getString("killsPerMinute"));
                        maps.put("headshots", jsonObject.getString("headshots"));
                        maps.put("img", jsonObject.getString("image"));
                        maps.put("acc", jsonObject.getString("accuracy"));
                        maps.put("damage", jsonObject.getString("damage"));
                        maps.put("headshotKills", jsonObject.getString("headshotKills"));
                        maps.put("shotsFired", jsonObject.getString("shotsFired"));
                        maps.put("time", jsonObject.getString("timeEquipped"));
                        maps1.add(maps);
                    }
                }
                Log.d("add", maps1.toString());
                //排序 简单排序
                for (int k = 0;k<maps1.size();k++){
                    for (int j = 0; j < maps1.size()-1; j++) {
                        if (Integer.parseInt(maps1.get(j).get("kills").toString())<Integer.parseInt(maps1.get(j+1).get("kills").toString())){
                            maps1.add(j, maps1.get(j+1));
                            maps1.remove(j+2);
                        }
                    }
                }

                arrayList.add(maps1);
                Log.d("add", arrayList.get(i).toString());
            }
            Log.d("add", arrayList.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        myWeaponsAdpeter = new MyWeaponsAdpeter(mapList_group,arrayList, MainActivity_Weapons.this);
        binding.weaponsView.setAdapter(myWeaponsAdpeter);
        Log.d("weapons", arrayList.toString());
        ImageButton imageButton = findViewById(R.id.title_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}