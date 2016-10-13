package com.example.hp.pro;

import android.content.Context;
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

public class ShoppingCartActivity extends AppCompatActivity {

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
        setContentView(R.layout.activity_shopping_cart);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shopping_cart);

            Toast.makeText(this, "yes1",Toast.LENGTH_SHORT).show();


            final List<Product> mCartList = ShoppingCartHelper.getCart();

            Toast.makeText(this, "yes2",Toast.LENGTH_SHORT).show();


            for(int i=0; i< mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }


            final ListView listViewCatalog = (ListView) findViewById(R.id.listView1);
            mProductAdapter = new ProductAdapter(this,mCartList, getLayoutInflater(), true);
            listViewCatalog.setAdapter(mProductAdapter);

            Toast.makeText(this, "yes3",Toast.LENGTH_SHORT).show();

            listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Product selectedProduct = mCartList.get(position);
                    if(selectedProduct.selected == true)
                        selectedProduct.selected = false;
                    else
                        selectedProduct.selected = true;

                    mProductAdapter.notifyDataSetInvalidated();

                }
            });

            Button removeButton = (Button) findViewById(R.id.button1);
            removeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    for(int i=mCartList.size()-1; i>=0; i--) {

                        if(mCartList.get(i).selected) {
                            mCartList.remove(i);
                        }
                    }
                    mProductAdapter.notifyDataSetChanged();
                }
            });


        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
