package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ConfirmOrderFragment extends Fragment implements ConfirmOrderAdapter.OnBasketEmptyListener {

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

        TextView emptyBasketMessage = view.findViewById(R.id.empty_basket_message);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_confirm_order);
        Button confirmOrderButton = view.findViewById(R.id.confirm_order_button);

        boolean isEmpty = isBasketEmpty();
        emptyBasketMessage.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        confirmOrderButton.setVisibility(isEmpty ? View.GONE : View.VISIBLE);

        if (!isEmpty) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(basketItems, getContext(), this);
            recyclerView.setAdapter(adapter);
        }

        List<Menu.MenuItem> allItems = BasketController.getInstance().getBasketItems();
        List<Menu.MenuItem> filteredItems = new ArrayList<>();

        for (Menu.MenuItem item : allItems) {
            if (item.getCount() > 0) {
                filteredItems.add(item);
            }

        }

        confirmOrderButton.setOnClickListener(v -> {

            OrderConfirmedFragment orderConfirmedFragment = new OrderConfirmedFragment(filteredItems);
            Log.d("MenuFragment", "newInstance: " + BasketController.getInstance().getBasketItems().size());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, orderConfirmedFragment)
                    .addToBackStack(null)
                    .commit();

            // Handle placing the order
            FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

            DatabaseReference dbRef = db.getReference("Order/data");

            dbRef.push().setValue(basketItems);

        });

        return view;
    }

    @Override
    public void onBasketEmpty() {
        // When the basket is empty, finish the activity (or fragment)
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private boolean isBasketEmpty() {
        for (Menu.MenuItem item : basketItems) {
            if (item.getCount() > 0) {
                return false; // At least one item has a count > 0
            }
        }
        return true; // All items have count = 0
    }
}