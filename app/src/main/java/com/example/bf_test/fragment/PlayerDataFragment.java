package com.example.bf_test.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bf_test.MainActivity2;
import com.example.bf_test.R;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayerDataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Map<String,Object> arrayList;
    private  View view1;
    private TextView kills,death,KD,Kdeath,killshots,headshots,KPM,dmg,acuracy,damagePerMinute,classes,repairs,win,time;
    public PlayerDataFragment() {
        // Required empty public constructor
    }

    public  PlayerDataFragment(Map<String,Object> arrayList){
        this.arrayList = arrayList;
    }
    public static PlayerDataFragment newInstance(String param1, String param2) {
        PlayerDataFragment fragment = new PlayerDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findId(view);
        this.view1 = view;
        MainActivity2 mainActivity2 = (MainActivity2) getActivity();
        try {
            if (mainActivity2 != null) {
                mainActivity2.PlayeUpData();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        death.setText(Objects.requireNonNull(arrayList.get("deaths")).toString());
        KD.setText(Objects.requireNonNull(arrayList.get("killDeath")).toString());
        Kdeath.setText(Objects.requireNonNull(arrayList.get("killsPerMatch")).toString());
        killshots.setText(Objects.requireNonNull(arrayList.get("headShots")).toString());
        headshots.setText(Objects.requireNonNull(arrayList.get("headshots")).toString());
        KPM.setText(Objects.requireNonNull(arrayList.get("killsPerMinute")).toString());
        dmg.setText(Objects.requireNonNull(arrayList.get("damagePerMinute")).toString());
        acuracy.setText(Objects.requireNonNull(arrayList.get("accuracy")).toString());
        damagePerMinute.setText(Objects.requireNonNull(arrayList.get("damagePerMatch")).toString());
        classes.setText(Objects.requireNonNull(arrayList.get("bestClass")).toString());
        repairs.setText(Objects.requireNonNull(arrayList.get("repairs")).toString());
        win.setText(Objects.requireNonNull(arrayList.get("winPercent")).toString());
        time.setText(arrayList.get("time")+"小时");
        kills.setText(Objects.requireNonNull(arrayList.get("kills")).toString());
    }

    private void findId(View view) {
        death = view.findViewById(R.id.death);
        KD = view.findViewById(R.id.KD);
        Kdeath = view.findViewById(R.id.Kdeath);
        killshots = view.findViewById(R.id.killshots);
        headshots = view.findViewById(R.id.headshots);
        KPM = view.findViewById(R.id.KPM);
        dmg = view.findViewById(R.id.dmg);
        acuracy = view.findViewById(R.id.acuracy);
        damagePerMinute = view.findViewById(R.id.damagePerMinute);
        classes = view.findViewById(R.id.classes);
        repairs = view.findViewById(R.id.repairs);
        win = view.findViewById(R.id.win);
        time = view.findViewById(R.id.time);
        kills = view.findViewById(R.id.pkill);
    }
    public void setData(Map<String,Object> map){
        this.arrayList=map;
        if (view1==null)return;
        findId(view1);
        setData();
    }

}