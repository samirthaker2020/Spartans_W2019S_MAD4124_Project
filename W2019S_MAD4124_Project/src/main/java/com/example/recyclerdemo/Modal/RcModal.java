package com.example.recyclerdemo.Modal;

public class RcModal {

    String Category;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @Override
    public String toString() {
        return "RcModal{" +
                "Category='" + Category + '\'' +
                '}';
    }

    public RcModal(String category) {
        Category = category;
    }
}
