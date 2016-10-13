package com.example.hp.pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private List<Product> mProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        try{
            List<Product> mProductList = ShoppingCartHelper.getCatalog(getResources());

            // Create the list
            ListView listViewCatalog = (ListView) findViewById(R.id.listView1);
            listViewCatalog.setAdapter(new ProductAdapter(this,mProductList, getLayoutInflater(), false));

            listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetails.class);
                    productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                    startActivity(productDetailsIntent);
                }
            });

            Button b1=(Button)findViewById(R.id.button1);
            b1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
                    startActivity(viewShoppingCartIntent);
                }
            });
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}






