package com.example.bf_test.other;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bf_test.R;
import com.example.bf_test.adapter.MytankAdapter;
import com.example.bf_test.databinding.ActivityMainTankBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

public class MainActivity_Tank extends AppCompatActivity {
    private String[] veh = {"直升机","固定翼","陆载","两栖"};
    private ArrayList<List<Map<String,Object>>> arrayList = new ArrayList<>();
    private List<Map<String,Object>> list;
    private MytankAdapter mytankAdapter;
    ActivityMainTankBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainTankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String tanks = intent.getStringExtra("tank");
        Log.d("tank", tanks);
        String[] type={"Helicopter","Plane","Land","Amphibious"};
        String[] veh1 = {"MD540 Nightbird","AH-64GX Apache Warchief","MV-38 Condor","RAH-68 Huron","Mi-240 Super Hind","YG-99 Hannibal","F-35E Panther","SU-57 FELON","M5C Bolte","EBAA Wildcat","M1A5","LATV4 Recon","EBLC-Ram","EMKV90-TOR","T28","LCAA Hovercraft","MAV"};
        try {
            JSONArray jsonArray = new JSONArray(tanks);
            for (int i = 0; i < type.length; i++) {
                list = new ArrayList<>();
                for (int j = 0; j < jsonArray.length(); j++) {
                    Map<String,Object> map = new HashMap<>();
                    JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                    if (jsonObject.getString("type").equals(type[i])&Arrays.asList(veh1).contains(jsonObject.getString("vehicleName").toString())){
                            map.put("name", jsonObject.getString("vehicleName"));
                            map.put("kills", jsonObject.getString("kills"));
                            map.put("KPM", jsonObject.getString("killsPerMinute"));
                            map.put("time", jsonObject.getString("timeIn"));
                            map.put("damage", jsonObject.getString("damage"));
                            map.put("assists", jsonObject.getString("assists"));
                            map.put("distance",jsonObject.getString("distanceTraveled"));
                            map.put("ch", jsonObject.getString("destroyed"));
                            map.put("roadKills", jsonObject.getString("roadKills"));
                            map.put("img", jsonObject.getString("image"));
                            list.add(map);
                    }
                }
                //排序
                for (int k = 0;k<list.size();k++){
                    for (int j = 0; j < list.size()-1; j++) {
                        if (Integer.parseInt(list.get(j).get("kills").toString())<Integer.parseInt(list.get(j+1).get("kills").toString())){
                            list.add(j, list.get(j+1));
                            list.remove(j+2);
                        }
                    }
                }
                arrayList.add(list);
            }
            Log.d("tank", arrayList.get(1).toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        mytankAdapter = new MytankAdapter(veh, arrayList, MainActivity_Tank.this);
        binding.veicle.setAdapter(mytankAdapter);
        ImageButton imageButton = findViewById(R.id.title_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}