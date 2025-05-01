package com.example.unieats.controller;

import com.example.unieats.model.BusinessUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class BusinessUserController {
    public interface BusinessUserCallback {
        void onBusinessUsersLoaded(List<BusinessUser> businessUsers);
    }

    public void getBusinessUsers(BusinessUserCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://unieats-57c3e-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference dbRef = db.getReference("BusinessUser/data");

        dbRef.get().addOnCompleteListener(task -> {
            List<BusinessUser> result = new ArrayList<>();

            if (task.isSuccessful()) {
                GenericTypeIndicator<HashMap<String, BusinessUser>> typeIndicator = new GenericTypeIndicator<HashMap<String, BusinessUser>>() {
                };

                HashMap<String, BusinessUser> firebaseResult = task.getResult().getValue(typeIndicator);

                if (firebaseResult != null) {
                    for (Map.Entry<String, BusinessUser> e : firebaseResult.entrySet()) {
                        result.add(e.getValue());
                    }
                }

                callback.onBusinessUsersLoaded(result);
            }
        });
    }

    public BusinessUser getBusinessUserByName(String businessName) {
        AtomicReference<BusinessUser> result = new AtomicReference<>(new BusinessUser());
        ArrayList<String> names = new ArrayList<>();

        getBusinessUsers(businessUsers -> {
            for(BusinessUser b : businessUsers) {
                if(Objects.equals(b.getBusinessName(), businessName)) {
                    result.set(b);
                }
            }
        });

        return result.get();
    }

    public boolean isCorrectLogin(String businessName, String password) {
        boolean result = false;

        BusinessUser user = getBusinessUserByName(businessName);
        if(Objects.equals(password, user.getPassword())) {
            result = true;
        }

        return result;
    }
}

