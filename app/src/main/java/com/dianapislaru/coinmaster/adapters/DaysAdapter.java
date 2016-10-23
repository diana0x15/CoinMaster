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
import com.dianapislaru.coinmaster.fragments.TodayFragment;

import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created by Diana on 05/10/2016.
 */

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysViewHolder> {

    private Activity mContext;
    private ArrayList<LocalDate> mDays;;

    private int lastSelectedPosition = -1;

    public DaysAdapter(Activity context, ArrayList<LocalDate> days) {
        mContext = context;
        mDays = days;

        lastSelectedPosition = getCurrentDayPosition();
    }

    @Override
    public DaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bottom_recyclerview_item, parent, false);
        return new DaysViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DaysViewHolder holder, final int position) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.getDisplayWidth(mContext)/7, LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.container.setLayoutParams(params);

        LocalDate day = mDays.get(position);
        holder.dayTextView.setText("" + day.getDayOfMonth());
        holder.weekDayTextView.setText(getWeekday(day));

        if (position == lastSelectedPosition) {
            holder.dayTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.weekDayTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.dayTextView.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.weekDayTextView.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(lastSelectedPosition);
                lastSelectedPosition = position;
                holder.dayTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.weekDayTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

                TodayFragment.setSelectedDay(mDays.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    public class DaysViewHolder extends RecyclerView.ViewHolder {

        public TextView dayTextView, weekDayTextView;
        public LinearLayout container;

        public DaysViewHolder(View view) {
            super(view);
            weekDayTextView = (TextView) view.findViewById(R.id.layout_bottom_recyclerview_item_textView_up);
            dayTextView = (TextView) view.findViewById(R.id.layout_bottom_recyclerview_item_textView_down);
            container = (LinearLayout) view.findViewById(R.id.layout_bottom_recyclerview_item_container);
        }
    }

    private String getWeekday(LocalDate day) {
        switch (day.getDayOfWeek()) {
            case 1: return "M";
            case 2: return "T";
            case 3: return "W";
            case 4: return "T";
            case 5: return "F";
            case 6: return "S";
            case 7: return "S";
        }
        return "M";
    }

    private int getCurrentDayPosition() {
        LocalDate today = new LocalDate();
        return today.getDayOfWeek() - 1;
    }


}
