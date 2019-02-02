package com.example.jack.samost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class ItemAdapter  extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> itemlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price, cdate;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.name);
            price =  view.findViewById(R.id.price);
            cdate =  view.findViewById(R.id.creation_date);
            thumbnail =  view.findViewById(R.id.thumbnail);
        }
    }


    public ItemAdapter(Context mContext, List<Product> itemlist) {
        this.mContext = mContext;
        this.itemlist = itemlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Product product = itemlist.get(position);
        holder.title.setText(product.getName());
        holder.price.setText("Τιμή :" + String.valueOf(product.getPrice()));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String creationDate = dateFormat.format(product.getCreationDate());
        holder.cdate.setText(creationDate );
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.thumbnail.setImageBitmap(bitmap);
        // loading album cover using Glide library
       // Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }


    @Override
    public int getItemCount() {
        return itemlist.size();
    }
}