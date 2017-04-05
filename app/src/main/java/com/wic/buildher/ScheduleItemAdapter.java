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
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Adapter for a list of {@link ScheduleItem} in a
 * {@link se.emilsjolander.stickylistheaders.StickyListHeadersListView}
 */
public class ScheduleItemAdapter extends ArrayAdapter<ScheduleItem>
        implements StickyListHeadersAdapter {
    static class ViewHolder {
        @BindView(R.id.time) TextView time;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.description) TextView description;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderViewHolder {
        @BindView(R.id.day) TextView day;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private LayoutInflater mInflater;
    private DateTimeFormatter mTimeFormatter;

    public ScheduleItemAdapter(Context context, List<ScheduleItem> scheduleItems) {
        super(context, 0, scheduleItems);
        mInflater = LayoutInflater.from(getContext());
        mTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_schedule, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ScheduleItem scheduleItem = getItem(position);
        holder.time.setText(scheduleItem.getTime().format(mTimeFormatter));
        holder.title.setText(scheduleItem.getTitle());
        String description = scheduleItem.getDescription();
        if (description.isEmpty()) {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(description);
        }

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_header_schedule, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.day.setText(getItem(position).getDay());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getDay().hashCode();
    }
}
