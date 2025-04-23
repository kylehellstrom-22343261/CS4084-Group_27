package com.example.unieats.model;

public class Menu {
    public static class MenuItem {
        private final String name;
        private final String description;
        private final double price;
        private final int image;

        public MenuItem(String name, String description, double price, int image) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.image = image;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public int getImage() { return image; }
    }
}
