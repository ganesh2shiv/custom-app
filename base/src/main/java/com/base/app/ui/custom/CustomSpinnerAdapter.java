package com.base.app.ui.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.app.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private boolean hint;
    private List<String> items;
    private OnClickListener listener;

    public CustomSpinnerAdapter(Context context, boolean hint, List<String> items,
                                OnClickListener listener) {
        super(context, 0, items);
        this.hint = hint;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        SpinnerItemHolder holder;

        if (row == null) {
            holder = new SpinnerItemHolder();
            if (hint) {
                row = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_spinner_hint, parent, false);
                holder.tilTitle = row.findViewById(R.id.til_title);
                holder.etTitle = row.findViewById(R.id.et_title);
                holder.tilTitle.setHintEnabled(false);
            } else {
                row = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_spinner_plain, parent, false);
                holder.tvTitle = row.findViewById(R.id.tv_title);
            }
            row.setTag(holder);
        } else {
            holder = (SpinnerItemHolder) row.getTag();
        }

        if (hint) {
            if (position == 0) {
                if (items.get(position).contains("|")) {
                    holder.etTitle.setHint(items.get(position).split("\\|")[0]);
                } else {
                    holder.etTitle.setHint(items.get(position));
                }
            } else {
                holder.tilTitle.setHintEnabled(true);
                holder.tilTitle.setHint(items.get(0));

                if (items.get(position).contains("|")) {
                    holder.etTitle.setText(items.get(position).split("\\|")[0]);
                } else {
                    holder.etTitle.setText(items.get(position));
                }
            }
        } else {
            if (items.get(position).contains("|")) {
                holder.tvTitle.setText(items.get(position).split("\\|")[0]);
            } else {
                holder.tvTitle.setText(items.get(position));
            }
        }

        if (hint) {
            holder.etTitle.setOnClickListener(listener);
        } else {
            holder.tvTitle.setOnClickListener(listener);
        }

        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if (hint && position == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_spinner_dropdown, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            TextView textview = view.findViewById(android.R.id.text1);
            if (items.get(position) != null) {
                if (items.get(position).contains("|")) {
                    textview.setText(items.get(position).split("\\|")[0]);
                } else {
                    textview.setText(items.get(position));
                }
            }
        }

        return view;
    }

    static class SpinnerItemHolder {
        TextView tvTitle;
        TextInputLayout tilTitle;
        EditText etTitle;
    }
}