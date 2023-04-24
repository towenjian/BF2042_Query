package com.example.bf_test;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.bf_test.adapter.FragmentViewPagerAdapter;
import com.example.bf_test.adapter.GridviewAdapter;
import com.example.bf_test.databinding.ActivityMainBinding;
import com.example.bf_test.databinding.PopupBinding;
import com.example.bf_test.fragment.CollectFragment;
import com.example.bf_test.fragment.HisIDFragment;
import com.example.bf_test.loading.Loadingclass;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    OkHttpClient okHttpClient;
    public String gameID,platform,datas;
    ActivityMainBinding binding;
    private ArrayAdapter<String> arrayAdapter,colloctAdapter;
    private GridviewAdapter gridviewAdapter;
    private ArrayList<String> arrayList = new ArrayList<>(),collectlist;
    private Loadingclass loadingclass;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;
    private List<Fragment> fragmentList;
    private HisIDFragment hisIDFragment;
    public CollectFragment collectFragment;
    public static MainActivity mainActivity;
//    private float startX =
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());//实例化binding，把binding与avtive绑定
        setContentView(binding.getRoot());//绑定
        mainActivity=this;
        okHttpClient = new OkHttpClient.Builder().build();
        if (!httptest()) Toast.makeText(MainActivity.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
        boolean is = httptest();
        History_ID_getid();
        binding.button1.setOnClickListener(v -> submit());
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,arrayList);
        binding.edit.setAdapter(arrayAdapter);//下拉框，自动检索ID
        binding.edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                submit();
            }
        });
        binding.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (collectlist.contains(binding.edit.getText().toString())) binding.sc.setChecked(true);
//                else binding.sc.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loadingclass = new Loadingclass(this);
        CollectID();
        VPgragment();
        binding.viewpagerScwithhis.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(MainActivity.this, "这是第"+position+"页", Toast.LENGTH_SHORT).show();
                textColorStatus();
                switch (position){
                    case 0:{
                        binding.llhisId.setTextColor(getResources().getColor(R.color.blue1));
                        moveblock(0);
                    }break;
                    case 1:{
                        binding.llcolectidId.setTextColor(getResources().getColor(R.color.blue1));
                        moveblock(1);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.llhis.setOnClickListener(this);//初始化历史id，改变颜色
        binding.llcolectid.setOnClickListener(this);

        //设置滑动块进度条为null
    }

    private void textColorStatus() {
        binding.llhisId.setTextColor(getResources().getColor(R.color.black));
        binding.llcolectidId.setTextColor(getResources().getColor(R.color.black));
    }

    public void submit(){//提交程序，检测过程，调用get()函数
        if(binding.edit.getText().length()==0){
            Toast.makeText(this, "ID不能为空！", Toast.LENGTH_LONG).show();
        }else{
            if (httptest()==false) binding.textView2.setText("当前网络不可用");
            else {
                loadingclass.show();
                binding.textView2.setText("查询中！...");
                gameID = binding.edit.getText().toString();
                if (binding.pc.isChecked()) {
                    platform = binding.pc.getText().toString();
                } else if (binding.ps4.isChecked()) {
                    platform = binding.ps4.getText().toString();
                } else if (binding.ps5.isChecked()) {
                    platform = binding.ps5.getText().toString();
                } else if (binding.xboxone.isChecked()) {
                    platform = binding.xboxone.getText().toString();
                }
                get();
                History_ID_setid(gameID);
            }
        }

    }
    public void get(){ //get调用api ，库okhttp//https://api.gametools.network/bf2042/stats/?raw=false&format_values=true&name=Mooonsh1ne&playerid=1004593003760&platform=pc&skip_battlelog=false
        if (httptest()!=false)
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10,TimeUnit.SECONDS)
                            .writeTimeout(10,TimeUnit.SECONDS)
                            .build();
                    Request request_getid = new Request.Builder()//https://api.gametools.network/bf2042/stats/?raw=false&format_values=true&name=hhhh6448&platform=&skip_battlelog=false
                            .url("https://api.gametools.network/bf2042/player/?name="+gameID)
                            .get()
                            .build();
                    Call call = okHttpClient.newCall(request_getid);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingclass.dismiss();
                                    binding.textView2.setText("网络连接超时");
                                    return;
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    Response response_getid=okHttpClient.newCall(request_getid).execute();
                    if (response_getid.code()==200){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textView2.setText("已获取到ID数据");
                            }
                        });
                        String result = null;
                        JSONObject jsonObject = new JSONObject(response_getid.body().string());
                        JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                        String game_id = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = new JSONObject(jsonArray.get(i).toString());
                            if (jsonObject1.getString("name").equals(gameID)) game_id = jsonObject1.getString("personaId");break;
                        }
                        if (game_id==null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.textView2.setText("该ID无法被找到,可能是输入的ID有误，游戏没有打开共享数据，ID已经被弃用或者被封禁");
                                    loadingclass.dismiss();
                                }
                            });
                            return;
                        }
                        Request request = new Request.Builder()
                                .url("https://api.gametools.network/bf2042/stats/?raw=false&format_values=true&playerid="+game_id+"&platform=pc&skip_battlelog=false")
                                .get()
                                .build();
                        Response response = okHttpClient.newCall(request).execute();
                        result = response.body().string();
                        Log.d("id", result.toString());
                        Log.d("id", jsonArray.toString());
                        String finalResult = result;
                        if (response.code()==200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        datas= finalResult;//把数据穿到全局data里面
                                        if (datas.length()>120) {
                                            Setdata();
                                            Log.d("da", datas.toString());
                                        }
                                        else {
                                            loadingclass.dismiss();
                                            Toast.makeText(MainActivity.this, "出错", Toast.LENGTH_SHORT).show();
                                            binding.textView2.setText("出错了！可能是名字错误,系统无法解析数据");
                                        }
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingclass.dismiss();
                                    binding.textView2.setText("查询失败，可能是名字或者平台错误，或者服务器失联，稍后再试");
                                }
                            });
                        }

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingclass.dismiss();
                                binding.textView2.setText("连接错误，请稍后尝试");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        else {
            loadingclass.dismiss();
            Toast.makeText(MainActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
    }
    public void Setdata(){//完毕后闪退.现在不闪退了2023.3.8 懒了2023.3.25 //get查到数据后调用setdata函数解析数据并且跳转页面
        Toast.makeText(this, "查询完毕", Toast.LENGTH_LONG).show();
        binding.textView2.setText("已获取到数据");
        Intent intent = new Intent();
        intent.setClass(this, MainActivity2.class);
        intent.putExtra("json-data", datas);
        intent.putExtra("name", binding.edit.getText().toString());
        startActivity(intent);
//        finish();
        loadingclass.dismiss();
    }
    public void History_ID_getid(){//检测是否有初始数据，如果有就显示出来，没有不做反应
                SharedPreferences preferences = getSharedPreferences("History_ID", MODE_PRIVATE);
                if (preferences.getString("ID0", "").equals("")==false){
                    for (int i = 0; i < 10; i++) {
                        if (preferences.getString("ID"+i,"").equals("")==false){
                            arrayList.add(preferences.getString("ID"+i,""));
                        }
                    }
                }
                Log.d("id", arrayList.toString());
    }
    public void History_ID_setid(String ID){//输入ID，并且存储ID，检测ID是否有重复的，如果有则不做处理，没有就存入第一个，并且重新布局
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("History_ID", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                String[] His_ID = {"","","","",""};
                if(preferences.getString("ID0","").equals("")){
                    editor.putString("ID0", ID);
                    arrayList.add(ID);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hisIDFragment.GridView_HistoryID(arrayList);
                        }
                    });
                    editor.commit();
                }else {
                    int temp = 0;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (ID.equals(arrayList.get(i))) temp++;
                    }//有重复ID则temp+1，下方如果temp!=0则不做处理
                    if (temp==0){
                        Log.d("id", arrayList.get(0).toString());
                        arrayList.add(0,ID);
                        if (arrayList.size()>20) arrayList.remove(19);
                        Log.d("id", arrayList.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hisIDFragment.GridView_HistoryID(arrayList);
                            }
                        });
                        //存入数据
                        for (int i = 0; i < arrayList.size(); i++) {
                            editor.putString("ID"+i,arrayList.get(i));
                        }
                        editor.commit();
                    }else {
                        arrayList.remove(ID);
                        arrayList.add(0,ID);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hisIDFragment.GridView_HistoryID(arrayList);
                            }
                        });
                        editor.clear();
                        for (int i = 0; i < arrayList.size(); i++) {
                            editor.putString("ID"+i,arrayList.get(i));
                        }
                        editor.commit();
                    }
                }
            }
        }).start();
    }
    public void CollectID(){
        SharedPreferences preferences = getSharedPreferences("Collect", MODE_PRIVATE);
        collectlist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (preferences.getString("sc"+i,"").equals("")!=true) collectlist.add(preferences.getString("sc"+i,""));
        }
        Log.d("id1", collectlist.toString());
    }
    public void reCollectID(ArrayList<String> arrayList, String reID){
        Log.d("t1", arrayList.toString());
        arrayList.remove(reID);
        collectFragment.up_CollectDate(arrayList);
        SharedPreferences preferences = getSharedPreferences("Collect", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            editor.putString("sc"+i,arrayList.get(i).toString());
        }
        editor.commit();
        Log.d("t1", arrayList.toString());

    }
    public boolean httptest(){//网络状态检测，可用返回true,不可用则返回false
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        return false;
    }
    public void VPgragment(){
        fragmentList = new ArrayList<>();
        collectFragment = new CollectFragment(collectlist);
        hisIDFragment = new HisIDFragment(arrayList);
        fragmentList.add(hisIDFragment);
        fragmentList.add(collectFragment);
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        binding.viewpagerScwithhis.setAdapter(fragmentViewPagerAdapter);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.llhis:{
                binding.viewpagerScwithhis.setCurrentItem(0);
                moveblock(0);
            }break;
            case R.id.llcolectid:{
                binding.viewpagerScwithhis.setCurrentItem(1);
                moveblock(1);
            }break;
        }
    }
    public void moveblock(int p){
        int weightpx = binding.llmove.getWidth();
        float density = getResources().getDisplayMetrics().density;
        int weightdp = (int) (weightpx/density);
        ViewGroup.LayoutParams params = binding.llmove.getLayoutParams();
        params.width=weightpx;
        binding.llmove.setLayoutParams(params);
        Log.d("w", weightdp+"");
        int [] location = new int[2];
        binding.moveblock.getLocationInWindow(location);
        location[0] = (int) ((int) location[0]/density);
        float weigth_move = 70*density;
        switch (p){
            case 0:{
                float endX = binding.moveblock.getX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(binding.moveblock, "x", endX, weigth_move/2);
                animator.setDuration(500);
                animator.start();
                Log.d("tt", endX+" | "+weightpx+" | "+weigth_move/density);
                Log.d("tt", endX+" | "+(weightpx - weigth_move));
            }break;
            case 1:{
                float startX = binding.moveblock.getX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(binding.moveblock, "x", startX,weightpx-weigth_move);
                animator.setDuration(500);
                animator.start();
                Log.d("tt", startX+" | "+weightpx+" | "+weigth_move);
                Log.d("tt", startX+" | "+(weightpx-weigth_move));
            }break;
        }
    }
}