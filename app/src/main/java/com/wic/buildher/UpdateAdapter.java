package com.wic.buildher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for a list of {@link Update}
 */
public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.subject) TextView subject;
        @BindView(R.id.message) TextView message;
        @BindView(R.id.time) TextView time;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private List<Update> mUpdates;
    private LayoutInflater mInflater;
    private DateTimeFormatter mDateTimeFormatter;

    public UpdateAdapter(Context context, List<Update> updates) {
        mUpdates = updates;
        mInflater = LayoutInflater.from(context);
        mDateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd, hh:mm a",
                Locale.getDefault());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = mInflater.inflate(R.layout.list_item_update, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Update update = mUpdates.get(position);
        holder.subject.setText(update.getSubject());
        holder.message.setText(update.getMessage());
        holder.time.setText(update.getTime().format(mDateTimeFormatter));
    }

    @Override
    public int getItemCount() {
        return mUpdates.size();
    }

    @Override
    public long getItemId(int position) {
        return mUpdates.get(position).getObjectId().hashCode();
    }

    public void swap(List<Update> list) {
        mUpdates = list;
        notifyDataSetChanged();
    }
}
