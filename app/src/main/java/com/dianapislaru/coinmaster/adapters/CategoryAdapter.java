package com.dianapislaru.coinmaster.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.objects.Category;

import java.util.ArrayList;

/**
 * Created by Diana on 23/10/2016.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<Category> mCategories;

    public CategoryAdapter(ArrayList<Category> categories) {
        mCategories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = mCategories.get(position);
        holder.titleTextView.setText(category.getTitle());
        holder.priceTextView.setText("$" + category.getPrice() + "");
        setCategoryIcon(holder, category);

    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView, priceTextView;
        public ImageView categoryIconImageView;

        public CategoryViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.layout_item_category_title);
            priceTextView = (TextView) view.findViewById(R.id.layout_item_category_price);
            categoryIconImageView = (ImageView) view.findViewById(R.id.layout_item_category_picture);
        }
    }

    private void setCategoryIcon(CategoryAdapter.CategoryViewHolder holder, Category category) {
        switch (category.getTitle()) {
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
