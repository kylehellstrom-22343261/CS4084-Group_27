package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.BasketController;
import com.example.unieats.controller.MenuController;
import com.example.unieats.view.adapter.MenuAdapter;

public class MenuFragment extends Fragment implements MenuAdapter.BasketUpdateListener {

    private static final String ARG_BUSINESS_NAME = "business_name";
    private String businessName;
    private Button basketButton;


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

        basketButton = view.findViewById(R.id.basketButton);

        MenuController.getMenu(businessName, menu -> {
            MenuAdapter adapter = new MenuAdapter(menu, this);
            recyclerView.setAdapter(adapter);
        });

        updateBasketButton();

        return view;
    }

    public void onBasketUpdated() {
        updateBasketButton();
    }

    private void updateBasketButton() {
        int count = BasketController.getInstance().getTotalItemCount();
        basketButton.setText("\uf291 " + count);
        basketButton.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        if (count > 0) {
            basketButton.animate()
                    .scaleX(1.05f)
                    .scaleY(1.05f)
                    .setDuration(100)
                    .withEndAction(() -> basketButton.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100))
                    .start();
        }
    }
}
