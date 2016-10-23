package com.dianapislaru.coinmaster.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.Utils;
import com.dianapislaru.coinmaster.fragments.MonthFragment;

import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created by Diana on 08/10/2016.
 */

public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.MonthsViewHolder> {

    private Activity mContext;
    private ArrayList<LocalDate> mMonths;

    private int selectedItem = 24;

    public MonthsAdapter(Activity context, ArrayList<LocalDate> months) {
        mContext = context;
        mMonths = months;
    }

    @Override
    public MonthsAdapter.MonthsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bottom_recyclerview_item, parent, false);
        return new MonthsAdapter.MonthsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MonthsAdapter.MonthsViewHolder holder, int position) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.getDisplayWidth(mContext)/7, LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.container.setLayoutParams(params);

        LocalDate month = mMonths.get(position);
        holder.yearTextView.setText("");
        holder.monthTextView.setText(month.toString("MMM"));

        if (position == selectedItem) {
            MonthFragment.setSelectedDay(month);

            holder.yearTextView.setText(month.toString("yyyy"));
            holder.yearTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.monthTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.yearTextView.setText("");
            holder.yearTextView.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.monthTextView.setTextColor(mContext.getResources().getColor(R.color.white));
        }

    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMonths.size();
    }

    public class MonthsViewHolder extends RecyclerView.ViewHolder {

        public TextView yearTextView, monthTextView;
        public LinearLayout container;

        public MonthsViewHolder(View view) {
            super(view);
            yearTextView = (TextView) view.findViewById(R.id.layout_bottom_recyclerview_item_textView_up);
            monthTextView = (TextView) view.findViewById(R.id.layout_bottom_recyclerview_item_textView_down);
            container = (LinearLayout) view.findViewById(R.id.layout_bottom_recyclerview_item_container);
        }
    }
}
