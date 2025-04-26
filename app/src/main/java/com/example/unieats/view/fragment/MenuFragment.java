package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.MenuController;
import com.example.unieats.model.Menu;
import com.example.unieats.model.RestaurantData;
import com.example.unieats.view.adapter.MenuAdapter;
import com.example.unieats.view.adapter.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

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

        TextView header = view.findViewById(R.id.menu_header);
        header.setText("Menu for " + businessName);

        RecyclerView recyclerView = view.findViewById(R.id.menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MenuController menuController = new MenuController();
        RestaurantData restaurantData = new RestaurantData();
        restaurantData.getMenu(businessName, new RestaurantData.MenuCallback() {
            @Override
            public void onMenuLoaded(List<Menu.MenuItem> menu) {
                MenuAdapter adapter = new MenuAdapter(menu);
                recyclerView.setAdapter(adapter);
            }
        });

//        MenuAdapter adapter = new MenuAdapter(menuController.getMenuBad(businessName));
//        recyclerView.setAdapter(adapter);

        return view;
    }
}
