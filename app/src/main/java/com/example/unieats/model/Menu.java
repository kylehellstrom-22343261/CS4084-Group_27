package com.example.unieats.model;

import com.example.unieats.R;

public class Menu {
    public static class MenuItem {
        private final String businessName;
        private final String name;
        private final String description;
        private final double price;
        private final String image;

        public MenuItem() {
            this.businessName="";
            this.name = "";
            this.description = "";
            this.price = 0;
            this.image = "";
        }

        public MenuItem(String businessName, String name, String description, double price, String image) {
            this.businessName = businessName;
            this.name = name;
            this.description = description;
            this.price = price;
            this.image = image;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public String getImage() { return image; }
    }
}
