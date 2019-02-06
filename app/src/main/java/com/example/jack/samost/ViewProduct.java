package com.example.jack.samost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewProduct extends AppCompatActivity {

    private Product product;
    public TextView title, price, cdate;
    public ImageView thumbnail;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        Intent i = getIntent();
        product = (Product) i.getParcelableExtra("viewproduct");
        title = findViewById(R.id.name);
        price = findViewById(R.id.price);
        cdate = findViewById(R.id.creation_date);
        thumbnail = findViewById(R.id.thumbnail);
        description = findViewById(R.id.description);

        title.setText(product.getName());
        price.setText("Τιμή : " + String.valueOf(product.getPrice()) + "€" );
        cdate.setText("Ημ. Εισαγωγής : " + product.getDateString());
        description.setText(product.getDescription());

        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        thumbnail.setImageBitmap(bitmap);
    }

}
