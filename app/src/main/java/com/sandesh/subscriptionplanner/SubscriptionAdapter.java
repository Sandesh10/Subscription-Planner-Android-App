package com.sandesh.subscriptionplanner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder> {

    private ArrayList<SubscriptionClass> mySubscriptionList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class SubscriptionViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewDesc;
        public TextView textViewAmount;
        public TextView textViewDate;
        public ImageView deleteImage;
        public TextView textViewDayCount;

        public SubscriptionViewHolder(View itemView, final OnItemClickListener mListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textViewAmount = itemView.findViewById(R.id.textView_amount);
            textViewDate = itemView.findViewById(R.id.textView_date);
            deleteImage = itemView.findViewById(R.id.image_delete);
            textViewDayCount = itemView.findViewById(R.id.textView_daysCount);

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public SubscriptionAdapter(ArrayList<SubscriptionClass> subscriptionList) {
        this.mySubscriptionList = subscriptionList;
    }
    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_entry_row, parent, false);
        SubscriptionViewHolder svh = new SubscriptionViewHolder(v, listener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        SubscriptionClass currentItem = mySubscriptionList.get(position);
        holder.textViewName.setText(currentItem.getSubsName());
        holder.textViewDesc.setText(currentItem.getSubsDescription());
        holder.textViewAmount.setText(currentItem.getSubsAmount());
        holder.textViewDate.setText(currentItem.getSubsDate());
        int remainingDays = getNetPaymentDate(currentItem.getSubsDate());
        holder.textViewDayCount.setText(remainingDays+" days");
    }

    @Override
    public int getItemCount() {
        return mySubscriptionList.size();
    }

    public int getNetPaymentDate(String startDate) {
        try {
            SimpleDateFormat customFormat = new SimpleDateFormat("MM/dd/yyyy");
            Calendar enteredDate = Calendar.getInstance();
            enteredDate.setTime(customFormat.parse(startDate));
            Log.i("MyApp", customFormat.format(enteredDate.getTime()));
            Calendar today = Calendar.getInstance();

            if (today.compareTo(enteredDate)==1) {
                while (enteredDate.compareTo(today) <= 0) {
                    enteredDate.add(Calendar.DATE, 30);
                }
            }
            Log.i("MyApp", customFormat.format(today.getTime()));
            Log.i("MyApp", customFormat.format(enteredDate.getTime()));
            Log.i("MyApp", "*********************");
            int remainingDays = (int) Math.ceil((float) (enteredDate.getTimeInMillis() - today.getTimeInMillis()) / (24 * 60 * 60 * 1000));
            return remainingDays;

        } catch (ParseException e) {
            Log.i("MyApp", "Calendar date parsing error");
        }
        return 0;
    }

}
