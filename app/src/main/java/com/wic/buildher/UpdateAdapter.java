package com.wic.buildher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for a list of {@link Update}
 */
public class UpdateAdapter extends ArrayAdapter<Update> {
    static class ViewHolder {
        @BindView(R.id.subject) TextView subject;
        @BindView(R.id.message) TextView message;
        @BindView(R.id.time) TextView time;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private LayoutInflater mInflater;
    private DateTimeFormatter mDateTimeFormatter;

    public UpdateAdapter(Context context, List<Update> updates) {
        super(context, 0, updates);
        mInflater = LayoutInflater.from(getContext());
        mDateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd, hh:mm a",
                Locale.getDefault());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_update, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Update update = getItem(position);
        holder.subject.setText(update.getSubject());
        holder.message.setText(update.getMessage());
        holder.time.setText(update.getTime().format(mDateTimeFormatter));

        return convertView;
    }
}
