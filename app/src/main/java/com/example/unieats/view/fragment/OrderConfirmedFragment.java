package com.example.unieats.view.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.unieats.model.Menu;
import com.example.unieats.view.activity.MainActivity;
import com.example.unieats.view.adapter.OrderConfirmedAdapter;

import java.util.List;

public class OrderConfirmedFragment extends Fragment {

    private final List<Menu.MenuItem> basketItems;
    private final double totalAmount;

    public OrderConfirmedFragment(List<Menu.MenuItem> basketItems, double totalAmount) {
        this.basketItems = basketItems;
        this.totalAmount =totalAmount;
    }

//    public static OrderConfirmedFragment newInstance(List<Menu.MenuItem> basketItems) {
//        OrderConfirmedFragment fragment = new OrderConfirmedFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("basketItems", basketItems);
//        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_order_confirm, container, false);

        TextView orderSuccessHeader = view.findViewById(R.id.order_success_header);
        TextView orderID = view.findViewById(R.id.order_id_text);
        TextView collectionTime = view.findViewById(R.id.collection_time_text);
        TextView totalText = view.findViewById(R.id.order_confirmed_total);  // Make sure this ID exists in the layout XML

//        orderSuccessHeader.setText("temp");
//        orderID.setText("temp");
//        collectionTime.setText("temp");
        totalText.setText(String.format("Total: €%.2f", totalAmount));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_confirm_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderConfirmedAdapter adapter = new OrderConfirmedAdapter(basketItems, getContext());
        recyclerView.setAdapter(adapter);

        Button finishOrder = view.findViewById(R.id.return_home_button);
        finishOrder.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class); // replace HomeActivity with your home activity
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }
}
