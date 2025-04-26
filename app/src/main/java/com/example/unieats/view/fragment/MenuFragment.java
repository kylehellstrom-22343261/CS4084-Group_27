package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.MenuController;
import com.example.unieats.view.adapter.MenuAdapter;

public class MenuFragment extends Fragment {

    private static final String ARG_BUSINESS_NAME = "business_name";
    private String businessName;

    public static MenuFragment newInstance(String businessName) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUSINESS_NAME, businessName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            businessName = getArguments().getString(ARG_BUSINESS_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        MenuController.getMenu(businessName, menu -> {
            MenuAdapter adapter = new MenuAdapter(menu);
            recyclerView.setAdapter(adapter);
        });


        return view;
    }
}
