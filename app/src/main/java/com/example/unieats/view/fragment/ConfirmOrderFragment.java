package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.BasketController;
import com.example.unieats.model.Menu;
import com.example.unieats.view.adapter.ConfirmOrderAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderFragment extends Fragment {

    private List<Menu.MenuItem> basketItems;

//    public ConfirmOrderFragment() {
//        // Required empty constructor
//    }

    public ConfirmOrderFragment(List<Menu.MenuItem> basketItems) {
        this.basketItems = basketItems;
    }

//    public static ConfirmOrderFragment newInstance(List<Menu.MenuItem> basketItems) {
//        ConfirmOrderFragment fragment = new ConfirmOrderFragment(basketItems);
//        Bundle args = new Bundle();
//        args.putSerializable("basketItems", new ArrayList<>(basketItems)); // safely cast to ArrayList
//        fragment.setArguments(args);
//        Log.d("MenuFragment", "newInstance: " + BasketController.getInstance().getBasketItems().size());
//        return fragment;
//    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            basketItems = (ArrayList<Menu.MenuItem>) getArguments().getSerializable("basketItems");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_order, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_confirm_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (basketItems != null && !basketItems.isEmpty()) {
            ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(basketItems, getContext());
            recyclerView.setAdapter(adapter);
        }

        List<Menu.MenuItem> allItems = BasketController.getInstance().getBasketItems();
        List<Menu.MenuItem> filteredItems = new ArrayList<>();

        for (Menu.MenuItem item : allItems) {
            if (item.getCount() > 0) {
                filteredItems.add(item);
            }
        }

        Button confirmOrderButton = view.findViewById(R.id.confirm_order_button);
        confirmOrderButton.setOnClickListener(v -> {
            OrderConfirmedFragment orderConfirmedFragment = new OrderConfirmedFragment(filteredItems);
            Log.d("MenuFragment", "newInstance: " + BasketController.getInstance().getBasketItems().size());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, orderConfirmedFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
