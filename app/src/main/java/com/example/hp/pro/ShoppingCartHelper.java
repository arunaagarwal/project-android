package com.example.hp.pro;

import android.content.res.Resources;

import java.util.List;
import java.util.Vector;

/**
 * Created by hp on 13-10-2016.
 */
public class ShoppingCartHelper {


    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static List<Product> cart;

    public static List<Product> getCatalog(Resources res) {
        if (catalog == null) {
            catalog = new Vector<Product>();
            catalog.add(new Product("LED", res.getDrawable(R.drawable.tv), "Sony", 20000));
            catalog.add(new Product("Fully Automatic Machine", res.getDrawable(R.drawable.machine), "Panasonic", 35000));
            catalog.add(new Product("Fridge", res.getDrawable(R.drawable.fridge), "Whirpool", 25000));
            catalog.add(new Product("Window Ac", res.getDrawable(R.drawable.ac1), "Hitachi", 40000));
            catalog.add(new Product("boot", res.getDrawable(R.drawable.dangeler), "Lavie", 29.9));
            catalog.add(new Product("pro", res.getDrawable(R.drawable.dangeler), "Lavie", 29.9));
            catalog.add(new Product("bracelet", res.getDrawable(R.drawable.dangeler), "Lavie", 29.9));
            catalog.add(new Product("hanky", res.getDrawable(R.drawable.dangeler), "Lavie", 29.9));
            catalog.add(new Product("White", res.getDrawable(R.drawable.dangeler), "Lavie", 29.9));
            catalog.add(new Product("Cos", res.getDrawable(R.drawable.dangeler), "Lavie", 29.9));
            catalog.add(new Product("Hais", res.getDrawable(R.drawable.dangeler), "Lavie", 29.9));
        }
        return catalog;
    }

    public static List<Product> getCart() {
        if(cart == null) {
            cart = new Vector<Product>();
        }

        return cart;
    }

}



