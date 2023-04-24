package com.example.bf_test.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

public class CollectFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ArrayList<String> arrayList;
    private GridView gridView;
    private GridviewAdapter gridviewAdapter;
    private AutoCompleteTextView autoCompleteTextView;

    public CollectFragment() {
        // Required empty public constructor
    }
    public CollectFragment(ArrayList<String> arrayList){
        this.arrayList = arrayList;
    }
    public void up_CollectDate(ArrayList<String> arrayList){
        this.arrayList=arrayList;
        gridviewAdapter = new GridviewAdapter(getContext(), arrayList);
        gridView.setAdapter(gridviewAdapter);
    }


    public static CollectFragment newInstance(String param1, String param2) {
        CollectFragment fragment = new CollectFragment();
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
        return inflater.inflate(R.layout.fragment_collect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        gridView = view.findViewById(R.id.collect_lv);
        gridviewAdapter = new GridviewAdapter(getContext(), arrayList);
        Log.d("co", arrayList.toString());
        gridView.setAdapter(gridviewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView = mainActivity.findViewById(R.id.edit);
                autoCompleteTextView.setText(arrayList.get(position));
                mainActivity.submit();
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                    .setTitle("是否取消收藏？")
                                    .setMessage("这会使你失去这个id很长时间")
                                    .setPositiveButton("是的我确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
                                            mainActivity.reCollectID(arrayList, arrayList.get(position).toString());
                                        }
                                    })
                                    .setNegativeButton("我后悔了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                return true;
            }
        });
    }
}