package com.example.jack.samost;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<Product> itemlist;
    private List<Product> filteredlist;
    private Product product;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, price, cdate;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            cdate = view.findViewById(R.id.creation_date);
            thumbnail = view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((RecyclerViewClickListener)mContext).recyclerViewListClicked(v, this.getAdapterPosition());
        }
    }


    public ItemAdapter(Context mContext, List<Product> itemlist) {
        this.mContext = mContext;
        this.itemlist = itemlist;
        this.filteredlist = itemlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        product = itemlist.get(position);
        holder.title.setText(product.getName());
        holder.price.setText("Τιμή :" + String.valueOf(product.getPrice()) + "€");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String creationDate = dateFormat.format(product.getCreationDate());
        holder.cdate.setText(creationDate);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    Log.d("EMPTY", "empty");
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = MainCatalogue.getInitList();
                    return filterResults;
                } else {
                    List<Product> filteredList2 = new ArrayList<>();
                    for (Product row : itemlist) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getDescription().contains(charSequence)) {
                            filteredList2.add(row);
                        }
                    }

                    filteredlist = filteredList2;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemlist = (ArrayList<Product>) filterResults.values;
                Log.d("REFRESH" ,"refres");
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}