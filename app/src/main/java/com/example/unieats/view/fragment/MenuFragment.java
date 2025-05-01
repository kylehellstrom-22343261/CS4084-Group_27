package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.unieats.R;
import com.example.unieats.controller.BasketController;
import com.example.unieats.controller.MenuController;
import com.example.unieats.model.Menu;
import com.example.unieats.view.adapter.MenuAdapter;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements MenuAdapter.BasketUpdateListener {

    private static final String ARG_BUSINESS_NAME = "business_name";
    private String businessName;
    private Button basketButton;
    private ImageView menuHeaderImage;
    private MenuAdapter menuAdapter;

    private TextView totalPrice;

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

        AppBarLayout appBarLayout = view.findViewById(R.id.fragment_appbar);
        menuHeaderImage = view.findViewById(R.id.menu_header_image);
        menuHeaderImage.setImageResource(R.drawable.stables_club); // TODO: dynamic image

        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            int totalScrollRange = appBarLayout1.getTotalScrollRange();
            float scrollFactor = (float) -verticalOffset / totalScrollRange;
            float alpha = 1f - scrollFactor;
            alpha = Math.max(0.5f, Math.min(1f, alpha));
            menuHeaderImage.setAlpha(alpha);
        });


        MenuController.getMenu(businessName, menu -> {
            BasketController.getInstance().setMenuItems(menu);
            menuAdapter = new MenuAdapter(menu, this, getContext());
            recyclerView.setAdapter(menuAdapter);
        });
//        totalPrice = view.findViewById(R.id.total_price_text);

        basketButton = view.findViewById(R.id.basketButton);
        basketButton.setOnClickListener(v -> {
            List<Menu.MenuItem> allItems = BasketController.getInstance().getBasketItems();
            List<Menu.MenuItem> filteredItems = new ArrayList<>();
            double total = BasketController.getInstance().getTotalPrice();
//            totalPrice.setText(String.format("Total: €%.2f", total));


            for (Menu.MenuItem item : allItems) {
                if (item.getCount() > 0) {
                    filteredItems.add(item);
                }
            }

            ConfirmOrderFragment confirmOrderFragment = new ConfirmOrderFragment(filteredItems);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, confirmOrderFragment)
                    .addToBackStack(null)
                    .commit();
        });

        updateBasketButton();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }

        updateBasketButton();
    }

    public void onBasketUpdated() {
        updateBasketButton();
    }

    private void updateBasketButton() {
        int count = BasketController.getInstance().getTotalItemCount();
        double total = BasketController.getInstance().getTotalPrice();
        basketButton.setText((String.format("Total: €%.0f", total) + String.format("   \uF291 %d", count)));        basketButton.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
//        totalPrice.setText(String.format("Total: €%.2f", total));

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
