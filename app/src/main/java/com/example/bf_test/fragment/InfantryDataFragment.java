package com.example.bf_test.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfantryDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfantryDataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Map<String,Object> map;
    private TextView KD,kills,wkills,bkills,shots,revives,heals,accuracy,time,vehiclesDestroyed,KPM;
    private View view1;

    public InfantryDataFragment() {
        // Required empty public constructor
    }
    public InfantryDataFragment(Map<String,Object> map){
        this.map=map;
    }
    public void setData(Map<String,Object> map1){
        this.map = map1;
//        findID(view1);
        setData();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfantryDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfantryDataFragment newInstance(String param1, String param2) {
        InfantryDataFragment fragment = new InfantryDataFragment();
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
        return inflater.inflate(R.layout.fragment_infantry_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view1 = view;
        findID(view);
        MainActivity2 mainActivity2 = (MainActivity2) getActivity();
        try {
            if (mainActivity2 != null) {
                mainActivity2.Infantryupdata();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        KD.setText(Objects.requireNonNull(map.get("KD")).toString());
        kills.setText(Objects.requireNonNull(map.get("kills")).toString());
        wkills.setText(Objects.requireNonNull(map.get("wkills")).toString());
        bkills.setText(Objects.requireNonNull(map.get("bkills")).toString());
        shots.setText(Objects.requireNonNull(map.get("shots")).toString());
        revives.setText(Objects.requireNonNull(map.get("revives")).toString());
        heals.setText(Objects.requireNonNull(map.get("heals")).toString());
        accuracy.setText(Objects.requireNonNull(map.get("accuracy")).toString());
        time.setText(Objects.requireNonNull(map.get("time"))+"小时");
        vehiclesDestroyed.setText(Objects.requireNonNull(map.get("vehiclesDestroyed")).toString());
        double tiemM = Double.parseDouble(Objects.requireNonNull(map.get("time")).toString()),kpm,kills = Double.parseDouble(map.get("kills").toString());
        kpm = kills/(tiemM*60);
        BigDecimal b =new BigDecimal(kpm);
        kpm = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        KPM.setText(String.valueOf(kpm));
    }

    private void findID(View view) {
//        wkills,bkills,shots,revives,heals,accuracy,time,vehiclesDestroyed;
        if (view==null)return;
        KD = view.findViewById(R.id.pKD);
        kills = view.findViewById(R.id.kill);
        wkills = view.findViewById(R.id.wkills);
        bkills = view.findViewById(R.id.bkills);
        shots = view.findViewById(R.id.shots);
        revives = view.findViewById(R.id.revives);
        heals = view.findViewById(R.id.heals);
        accuracy = view.findViewById(R.id.accuracy);
        time = view.findViewById(R.id.time);
        vehiclesDestroyed = view.findViewById(R.id.vehiclesDestroyed);
        KPM = view.findViewById(R.id.KPM);
    }
}