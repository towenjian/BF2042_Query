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
import org.jetbrains.annotations.TestOnly;

import java.util.Map;
import java.util.Objects;

public class VehiclesDataFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Map<String,Object> map;
    private View view1;
    private TextView kill,des,KPM,time,roadkills,lqkills,lzkills,gdykills,zsjkills,lqkpm,lzkpm,gdykpm,zsjkpm;

    public VehiclesDataFragment() {
        // Required empty public constructor
    }
    public VehiclesDataFragment(Map<String,Object> map){
        this.map=map;
    }
    public void setData(Map<String,Object> map){
        this.map=map;
        findID(view1);
        setData();
    }

    public static VehiclesDataFragment newInstance(String param1, String param2) {
        VehiclesDataFragment fragment = new VehiclesDataFragment();
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
        return inflater.inflate(R.layout.fragment_vehicles_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view1 = view;
        findID(view);
        MainActivity2 mainActivity2 = (MainActivity2) getActivity();
        if (mainActivity2 != null) {
            mainActivity2.VehiclesUpData();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (kill==null) return;
        kill.setText(Objects.requireNonNull(map.get("kills")).toString());
        des.setText(Objects.requireNonNull(map.get("destroyed")).toString());
        KPM.setText(Objects.requireNonNull(map.get("KPM")).toString());
        time.setText(map.get("time")+"小时");
        roadkills.setText(Objects.requireNonNull(map.get("roadkills")).toString());
        lqkills.setText(Objects.requireNonNull(map.get("Amphibiouskills")).toString());
        lqkpm.setText(Objects.requireNonNull(map.get("Amphibiouskpm")).toString());
        lzkills.setText(Objects.requireNonNull(map.get("Landkills")).toString());
        lzkpm.setText(Objects.requireNonNull(map.get("Landkpm")).toString());
        gdykills.setText(Objects.requireNonNull(map.get("Planekills")).toString());
        gdykpm.setText(Objects.requireNonNull(map.get("Planekpm")).toString());
        zsjkills.setText(Objects.requireNonNull(map.get("Helicopterkills")).toString());
        zsjkpm.setText(Objects.requireNonNull(map.get("Helicopterkpm")).toString());
    }

    private void findID(View view) {
        if (view ==null) return;
        kill = view.findViewById(R.id.vkill);
        des = view.findViewById(R.id.des);
        KPM = view.findViewById(R.id.KPM);
        roadkills = view.findViewById(R.id.roadkills);
        time = view.findViewById(R.id.time);
        lqkills = view.findViewById(R.id.lqkills);
        lqkpm = view.findViewById(R.id.lqkpm);
        lzkills = view.findViewById(R.id.lzkills);
        lzkpm = view.findViewById(R.id.lzkpm);
        gdykills = view.findViewById(R.id.gdykills);
        gdykpm = view.findViewById(R.id.gdykpm);
        zsjkills = view.findViewById(R.id.zsjkills);
        zsjkpm = view.findViewById(R.id.zsjkpm);

    }
}