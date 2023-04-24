package com.example.bf_test.other;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bf_test.R;
import com.example.bf_test.adapter.MyclassesAdapter;
import com.example.bf_test.databinding.ActivityMainClassesBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_Classes extends AppCompatActivity {
    private String[] class_name;
    private ArrayList<List<Map<String,Object>>> arrayList = new ArrayList<>();
    private MyclassesAdapter myclassesAdapter;
    private List<Map<String,Object>> maps;
    private Map<String,Object> Classes_chinese = new HashMap<>();
    ActivityMainClassesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainClassesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String classes = intent.getStringExtra("classes");
        Log.d("classes", classes);
        class_name = new String[]{"突击","支援","医疗","斟茶"};
        String[] Assault_class = {"麦凯","日舞","推土机","只因哥"};
        String[] Enginner_class = {"莉丝","爱尔兰佬","鲍里斯"};
        String[] Support_class = {"天使","法兰克","查理·克劳福德"};
        String[] Recon_class = {"白智秀","拉奥","卡斯帕","卡米拉·布拉斯科"};
        String[] classes_type = {"Assault","Engineer","Support","Recon"};
        try {
            JSONArray classes_data = new JSONArray(classes);
            for (int i = 0; i < classes_type.length; i++) {
                maps = new ArrayList<>();
                int k = 0;
                Log.d("c", String.valueOf(k));
                for (int j = 0; j < classes_data.length(); j++) {
                    Map<String,Object> map1 = new HashMap<>();
                    JSONObject jsonObject = (JSONObject) classes_data.get(j);
                    if(jsonObject.getString("className").equals(classes_type[i])&jsonObject.getString("characterName").indexOf(" ")==-1){
                        switch (i)
                        {
                            case 0:map1.put("name", Assault_class[k]);k++;break;
                            case 1:map1.put("name", Enginner_class[k]);k++;break;
                            case 2:map1.put("name", Support_class[k]);k++;break;
                            case 3:map1.put("name", Recon_class[k]);k++;break;
                        }
                        Log.d("c", String.valueOf(k));
                        map1.put("kills", jsonObject.getString("kills"));
                        map1.put("KPM", jsonObject.getString("kpm"));
                        map1.put("time", Integer.parseInt(jsonObject.getString("secondsPlayed"))/60);
                        map1.put("kd",jsonObject.getString("killDeath"));
                        maps.add(map1);
                    }
                }
                //排序
                for (int kz = 0;kz<maps.size();kz++){
                    for (int j = 0; j < maps.size()-1; j++) {
                        if (Integer.parseInt(maps.get(j).get("kills").toString())<Integer.parseInt(maps.get(j+1).get("kills").toString())){
                            maps.add(j, maps.get(j+1));
                            maps.remove(j+2);
                        }
                    }
                }
                arrayList.add(maps);
            }
            Log.d("class", arrayList.toString());
            Log.d("class", classes_data.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        myclassesAdapter = new MyclassesAdapter(class_name,arrayList, MainActivity_Classes.this);
        binding.classesMain.setAdapter(myclassesAdapter);
        ImageButton imageButton = findViewById(R.id.title_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}