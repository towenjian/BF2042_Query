package com.example.bf_test.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bf_test.MainActivity;
import com.example.bf_test.R;
import com.example.bf_test.adapter.GridviewAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HisIDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HisIDFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> arrayList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gridView;
    private GridviewAdapter gridviewAdapter;
    private AutoCompleteTextView edit;

    public HisIDFragment() {
        // Required empty public constructor
    }
    public HisIDFragment(ArrayList<String> arrayList){
        this.arrayList=arrayList;
    }
    public void GridView_HistoryID(ArrayList<String> arrayList){
        gridviewAdapter = new GridviewAdapter(getContext(),arrayList);
        gridView.setAdapter(gridviewAdapter);
        this.arrayList=arrayList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HisIDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HisIDFragment newInstance(String param1, String param2) {
        HisIDFragment fragment = new HisIDFragment();
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
        return inflater.inflate(R.layout.fragment_his_i_d, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.fragment_hisid);
        gridviewAdapter = new GridviewAdapter(getContext(),arrayList);
        gridView.setAdapter(gridviewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity mainActivity = (MainActivity) getActivity();
                edit = mainActivity.findViewById(R.id.edit);
                edit.setText(arrayList.get(position));
                mainActivity.submit();
            }
        });
    }
}