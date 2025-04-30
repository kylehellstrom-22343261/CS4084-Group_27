package com.example.unieats.view.fragment;

import android.os.Bundle;
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
import com.example.unieats.model.Menu;
import com.example.unieats.view.adapter.ConfirmOrderAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class ConfirmOrderFragment extends Fragment implements ConfirmOrderAdapter.OnBasketEmptyListener {

    private final List<Menu.MenuItem> basketItems;

    public ConfirmOrderFragment(List<Menu.MenuItem> basketItems) {
        this.basketItems = basketItems;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_order, container, false);

        Button confirmOrderButton = view.findViewById(R.id.confirm_order_button);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_confirm_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(basketItems, getContext(), this);
        recyclerView.setAdapter(adapter);


        confirmOrderButton.setOnClickListener(v -> {
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

}