package com.dianapislaru.coinmaster.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.objects.Item;

import java.util.ArrayList;

/**
 * Created by Diana on 05/10/2016.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.DaysViewHolder> {

    private ArrayList<Item> mItems;

    public ItemsAdapter(ArrayList<Item> items) {
        mItems = items;
    }

    @Override
    public DaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_day, parent, false);
        return new DaysViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DaysViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.categoryTextView.setText(item.getCategory());
        holder.priceTextView.setText("$" + item.getPrice() + "");
        setCategoryIcon(holder, item);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class DaysViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView, categoryTextView, priceTextView;
        public ImageView categoryIconImageView;

        public DaysViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.layout_item_today_title);
            categoryTextView = (TextView) view.findViewById(R.id.layout_item_today_category);
            priceTextView = (TextView) view.findViewById(R.id.layout_item_today_price);
            categoryIconImageView = (ImageView) view.findViewById(R.id.layout_item_today_picture);
        }
    }

    private void setCategoryIcon(DaysViewHolder holder, Item item) {
        switch (item.getCategory()) {
            case "Groceries":
                holder.categoryIconImageView.setImageResource(R.drawable.ic_local_grocery_store_black_24dp);
                break;
            case "Bills":
                holder.categoryIconImageView.setImageResource(R.drawable.ic_payment_black_24dp);
                break;
            case "Shopping":
                holder.categoryIconImageView.setImageResource(R.drawable.ic_local_offer_black_24dp);
                break;
            case "Food":
                holder.categoryIconImageView.setImageResource(R.drawable.ic_local_bar_black_24dp);
                break;
            case "Entertainment":
                holder.categoryIconImageView.setImageResource(R.drawable.ic_desktop_mac_black_24dp);
                break;
            default: holder.categoryIconImageView.setImageResource(R.drawable.ic_local_grocery_store_black_24dp);
        }
    }
}
