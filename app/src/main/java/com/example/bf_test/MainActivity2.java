package com.example.bf_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.example.bf_test.adapter.FragmentViewPagerAdapter;
import com.example.bf_test.databinding.ActivityMain2Binding;
import com.example.bf_test.fragment.InfantryDataFragment;
import com.example.bf_test.fragment.PlayerDataFragment;
import com.example.bf_test.fragment.VehiclesDataFragment;
import com.example.bf_test.other.MainActivity_Classes;
import com.example.bf_test.other.MainActivity_Map;
import com.example.bf_test.other.MainActivity_Tank;
import com.example.bf_test.other.MainActivity_Weapons;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    String game_id;
    ArrayList<String> arrayList = new ArrayList<>();
    ActivityMain2Binding binding;
    private List<Map<String, Object>> mList;
    private  int[] imgs = {
            R.drawable.amry1,
            R.drawable.gun,
            R.drawable.tank,
            R.drawable.map
    };
    private PlayerDataFragment playerDataFragment;
    private InfantryDataFragment infantryDataFragment;
    private VehiclesDataFragment vehiclesDataFragment;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;
    private List<Fragment> fragmentList;
    private SimpleAdapter simpleAdapter;
    private String text1;
    private Map<String,Object> PlayerData = new HashMap<>(),InfantryData=new HashMap<>() ,VehiclesData = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent =getIntent();
        text1 = intent.getStringExtra("json-data");
        VPFragment2();
        try {

            JSONObject jsonObject = new JSONObject(text1);
            binding.welcome.setText(intent.getStringExtra("name").toString());
            CollectID(intent.getStringExtra("name").toString());
            game_id = intent.getStringExtra("name").toString();
            Glide.with(this).load(jsonObject.getString("avatar").toString()).into(binding.playerImg);
//            binding.xp.setText(jsonObject.getString("XP").toString());
            Log.d("xp", jsonObject.getString("XP").toString());
//            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("XP").toString());
            JSONObject jsonObject1 = new JSONObject(new JSONArray(jsonObject.getString("XP").toString()).get(0).toString());
//            Log.d("xp", jsonObject1.toString());
//            binding.xp.setText(jsonObject1.getString("total").toString());
            binding.mvp.setText(jsonObject.getString("mvp").toString());
            int xp_total = jsonObject1.getInt("total");
            setXP(xp_total);




        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

//        binding.data.setText(text);
        String[] title={"专家","武器","载具","地图"};
        mList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String,Object> map = new HashMap();
            map.put("img", imgs[i]);
            map.put("title", title[i]);
            mList.add(map);
        }


        simpleAdapter = new SimpleAdapter(this,
                mList,
                R.layout.list,
                new String[]{"img","title"},
                new int[]{R.id.image,R.id.text3}
        );
        Log.d("map", mList.get(1).toString());
        binding.listview.setAdapter(simpleAdapter);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        try {
                            JSONObject jsonObject = new JSONObject(text1);
                            Intent intent1 = new Intent();
                            intent1.putExtra("classes", jsonObject.getString("classes"));
                            intent1.setClass(MainActivity2.this, MainActivity_Classes.class);
                            startActivity(intent1);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }break;
                    case 1:{
                        try {
                            JSONObject jsonObject = new JSONObject(text1);
                            Intent intent1 = new Intent();
                            intent1.putExtra("weapons", jsonObject.getString("weapons"));
                            intent1.setClass(MainActivity2.this, MainActivity_Weapons.class);
                            startActivity(intent1);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }break;
                    case 2:{
                        try {
                            JSONObject jsonObject=new JSONObject(text1);
                            Intent intent1 = new Intent();
                            intent1.putExtra("tank", jsonObject.getString("vehicles"));
                            intent1.setClass(MainActivity2.this, MainActivity_Tank.class);
                            startActivity(intent1);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }break;
                    case 3:{
                        try {
                            JSONObject jsonObject = new JSONObject(text1);
                            Intent intent1 = new Intent();
                            intent1.putExtra("maps", jsonObject.getString("maps"));
                            intent1.setClass(MainActivity2.this, MainActivity_Map.class);
                            startActivity(intent1);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }break;
                }
            }
        });
        binding.sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.sc.isChecked()){
                    Toast.makeText(MainActivity2.this, "已收藏", Toast.LENGTH_SHORT).show();
                    CollectID(game_id,true);
                }else {
                    Toast.makeText(MainActivity2.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    CollectID(game_id,false);
                }
            }
        });
        ImageButton btn = findViewById(R.id.title_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Lban(game_id);
        binding.PlayerData.setOnClickListener(this);
        binding.InfantryData.setOnClickListener(this);
        binding.VehiclesData.setOnClickListener(this);
        binding.playerData.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

            @Override
            public void onPageSelected(int position) {
                ViewColor();
                switch (position){
                    case 0:{
                        binding.InfantryDataView.setBackgroundColor(getResources().getColor(R.color.blue1));
                    }break;
                    case 1:{
                        binding.PlayerDataView.setBackgroundColor(getResources().getColor(R.color.blue1));
                    }break;
                    case 2:{
                        binding.VehiclesDataView.setBackgroundColor(getResources().getColor(R.color.blue1));
                    }break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void VehiclesUpData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Map<String,Object>> mapList;
                    JSONObject jsonObject = new JSONObject(text1);
                    int kills = 0;
                    int roadkills =0;
                    int destroyed = 0;
                    double time = 0;
                    int callIns = 0;
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("vehicles").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = new JSONObject(jsonArray.getString(i));
                        kills+=Integer.parseInt(jsonObject1.getString("kills").toString());
                        roadkills+=Integer.parseInt(jsonObject1.getString("roadKills").toString());
                        destroyed+=Integer.parseInt(jsonObject1.getString("destroyed").toString());
                        time+=Double.parseDouble(jsonObject1.getString("timeIn").toString());
                        callIns+=Integer.parseInt(jsonObject1.getString("callIns").toString());

                    }
                    VehiclesData.put("kills", kills+"");
                    VehiclesData.put("roadkills", roadkills+"");
                    VehiclesData.put("destroyed", destroyed+"");
                    time/=3600;
                    BigDecimal b = new BigDecimal(time);
                    time = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    double kpm = 0;
                    kpm=kills/(time*60);
                    b = new BigDecimal(kpm);
                    kpm = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    VehiclesData.put("time", time+"");
                    VehiclesData.put("KPM", kpm+"");
                    Log.d("v", kills+" | "+roadkills+" | "+destroyed);
                    VehiclesData.put("calls", callIns+"");
                    JSONArray jsonArray1 = new JSONArray(jsonObject.getString("vehicleGroups").toString());
                    String[] name = {"Helicopter","Plane","Land","Amphibious"};
                    for (int i = 0; i < name.length; i++) {
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject1 = new JSONObject(jsonArray1.getString(j).toString());
                            if (jsonObject1.getString("groupName").toString().equals(name[i])){
                                VehiclesData.put(name[i]+"kills",jsonObject1.getString("kills").toString()==null?0:jsonObject1.getString("kills").toString());
                                VehiclesData.put(name[i]+"kpm",jsonObject1.getString("killsPerMinute").toString()==null?0:jsonObject1.getString("killsPerMinute").toString());
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vehiclesDataFragment.setData(VehiclesData);
                            Log.d("z", VehiclesData.toString());
                        }
                    });
                    Log.d("v", VehiclesData.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void Infantryupdata() throws JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(text1);
                    InfantryData.put("KD", jsonObject.getString("infantryKillDeath"));//步兵KD
                    InfantryData.put("accuracy", jsonObject.getString("accuracy"));//正确率
                    InfantryData.put("resupplies", jsonObject.getString("resupplies"));//补给数
                    InfantryData.put("squadmateRevive", jsonObject.getString("squadmateRevive"));//复活队友数
                    InfantryData.put("shots", jsonObject.getString("shotsFired"));//射击次数
                    InfantryData.put("revives", jsonObject.getString("revives"));//急救次数
                    InfantryData.put("heals", jsonObject.getString("heals"));//治疗次数
                    InfantryData.put("vehiclesDestroyed", jsonObject.getString("vehiclesDestroyed"));//摧毁载具
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("weapons").toString());
                    int kill= 0;
                    int bkills=0;
                    double time =0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = new JSONObject(jsonArray.getString(i).toString());
                        kill+=Integer.parseInt(jsonObject1.getString("kills").toString());
                        time+=Integer.parseInt(jsonObject1.getString("timeEquipped").toString());
                    }
                    InfantryData.put("wkills", kill+"");
                    time/=3600;
                    BigDecimal b = new BigDecimal(time);
                    time = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    Log.d("k", time+"");
                    InfantryData.put("time", time+"");
                    jsonArray = new JSONArray(jsonObject.getString("gadgets").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = new JSONObject(jsonArray.getString(i).toString());
                        bkills+= Integer.parseInt(jsonObject1.getString("kills").toString());
                    }
                    InfantryData.put("bkills",bkills+"");
                    Log.d("k", kill+"");
                    InfantryData.put("kills", (kill+bkills)+"");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            infantryDataFragment.setData(InfantryData);
                            Log.d("z", InfantryData.toString());

                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void PlayeUpData() throws JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(text1);
                    PlayerData.put("kills", jsonObject.getString("kills"));//击杀数
                    PlayerData.put("deaths", jsonObject.getString("deaths"));//死亡数
                    PlayerData.put("killDeath", jsonObject.getString("killDeath"));//KD
                    PlayerData.put("killsPerMatch", jsonObject.getString("killsPerMatch"));//KPM
                    PlayerData.put("headShots", jsonObject.getString("headShots"));//爆头数
                    PlayerData.put("headshots", jsonObject.getString("headshots"));//爆头率
                    PlayerData.put("killsPerMinute", jsonObject.getString("killsPerMinute"));//场均击杀
                    PlayerData.put("damagePerMinute", jsonObject.getString("damagePerMinute"));//分钟伤害
                    PlayerData.put("accuracy", jsonObject.getString("accuracy"));//命中率
                    PlayerData.put("damagePerMatch", jsonObject.getString("damagePerMatch"));//场均伤害
                    PlayerData.put("bestClass", jsonObject.getString("bestClass"));//最佳专家
                    PlayerData.put("repairs", jsonObject.getString("repairs"));//维修数
                    PlayerData.put("winPercent", jsonObject.getString("winPercent"));//胜率
                    String str = jsonObject.getString("timePlayed");
                    if (str.indexOf("days")!=-1){
                        int D = Integer.parseInt(str.substring(0,str.indexOf(" days,")));
                        int H = Integer.parseInt(str.substring(str.indexOf(", ")+2,str.indexOf(":")));
                        String m = str.substring(str.indexOf(":")+1);
                        int M = Integer.parseInt(m.substring(0,m.indexOf(":")));
                        Log.d("z", m+"|"+str.indexOf(H+"")+"|"+H+"|"+M);
                        String tmie=String.valueOf(M>=30?D*24+H+1:D*24+H);
                        PlayerData.put("time", tmie);//time
                    }else {
                        int H =Integer.parseInt(str.substring(0,str.indexOf(":")));
                        String m = str.substring(str.indexOf(":")+1);
                        int M = Integer.parseInt(m.substring(0,m.indexOf(":")));
                        Log.d("z", H+"|"+M);
                        String tmie=String.valueOf(M>=30?H+1:H);
                        PlayerData.put("time", tmie);//time
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playerDataFragment.setData(PlayerData);
                            Log.d("z", PlayerData.toString());
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void VPFragment2() {
        fragmentList = new ArrayList<>();
        infantryDataFragment = new InfantryDataFragment();
        playerDataFragment = new PlayerDataFragment();
        vehiclesDataFragment = new VehiclesDataFragment();
        fragmentList.add(infantryDataFragment);
        fragmentList.add(playerDataFragment);
        fragmentList.add(vehiclesDataFragment);
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        binding.playerData.setAdapter(fragmentViewPagerAdapter);
        binding.playerData.setCurrentItem(1);
        binding.playerData.setOffscreenPageLimit(2);
    }

    public void setXP(int xp_total){
        int[] xp_b = {5415750,5281250,5147750,5014500,4882250,4750500,4619500,4489000,4359750,4231500,4104250,3981750,3863750,3750250,3641250,3536500,3436500,3340500,3249250,3162000,3079250,2998500,2920000,2843750,2769750,2698000,2628250,2560500,2495250,2431750,2370500,2310500,2251500,2194000,2137750,2082500,2028500,1976000,1924250,1924250
        ,1874000,1824750,1776250,1684250,1634500,1588500,1543000,1498250,1454000,1410250,1367250,1324750,1282750,1241250,1200250,1159750,1120000,1080500,1041500,1003000,965000,927500,890500,854250,818500,783250,748500,714250,680750,647500,615000,582750,551000,519750,489000,458500,428500,398750,369500,340750,312250,284250,256750,229750,203500,
                177500,152000,129000,108000,89000,72000,57000,44000,33000,24000,16000,9000,3000,0};
        Log.d("xp", xp_b.length+"");
        if (xp_total>5415750) {
            binding.xpD.setText("PRO");
        }
        else {
            for (int i = 0;i<xp_b.length;i++){
                if (xp_total>xp_b[i]) {
                    binding.xpD.setText((99 - i) + "");
                    break;
                }
            }
        }
    }
    public void CollectID(String gameid){
        SharedPreferences preferences = getSharedPreferences("Collect", MODE_PRIVATE);
        for (int i = 0; i < 10; i++) {
            if (!preferences.getString("sc"+i,"").equals("")) arrayList.add(preferences.getString("sc"+i,"").toString());
        }
        if (arrayList.contains(gameid)) binding.sc.setChecked(true);
        else binding.sc.setChecked(false);
    }
    public void CollectID(String gameid ,boolean isCollet){
        SharedPreferences preferences = getSharedPreferences("Collect", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (isCollet){
            arrayList.add(gameid);
            if (arrayList.size()==11) arrayList.remove(10);
            editor.clear();
            for (int i = 0; i < arrayList.size(); i++) {
                editor.putString("sc"+i,arrayList.get(i));
            }
            editor.commit();
            MainActivity.mainActivity.collectFragment.up_CollectDate(arrayList);
        }else {
            arrayList.remove(gameid);
            editor.clear();
            for (int i = 0; i < arrayList.size(); i++) {
                editor.putString("sc"+i,arrayList.get(i));
            }
            editor.commit();
        }
        MainActivity.mainActivity.collectFragment.up_CollectDate(arrayList);
        Log.d("t2", arrayList.toString());
    }
    public void Lban(String gameid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient = new OkHttpClient.Builder().build();
                Request request = new Request.Builder()
                        .url("https://api.gametools.network/bfban/checkban?names="+gameid)
                        .get()
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.code() ==200){
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        jsonObject = new JSONObject(jsonObject.getString("names").toString());
                        jsonObject = new JSONObject(jsonObject.getString(gameid.toLowerCase().toString()).toString());
                        Log.d("lb", jsonObject.getString("hacker").toString());
                        if (jsonObject.getString("hacker").toString().equals("false")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.lb.setText("无结果");
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.lb.setText("实锤");
                                }
                            });
                        }
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.lb.setText("连接错误");
                            }
                        });
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.PlayerData:{
                binding.playerData.setCurrentItem(1);
            }break;
            case R.id.InfantryData:{
                binding.playerData.setCurrentItem(0);
            }break;
            case R.id.VehiclesData:{
                binding.playerData.setCurrentItem(2);
            }break;
        }
    }

    private void ViewColor() {
        binding.PlayerDataView.setBackgroundColor(getResources().getColor(R.color.black));
        binding.InfantryDataView.setBackgroundColor(getResources().getColor(R.color.black));
        binding.VehiclesDataView.setBackgroundColor(getResources().getColor(R.color.black));
    }
}