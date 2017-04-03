package com.wic.buildher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Adapter for a list of sponsors
 */
class SponsorListAdapter extends ArrayAdapter<SponsorFragment.Sponsor> {
    private LayoutInflater mInflater;

    public SponsorListAdapter(Context context, List<SponsorFragment.Sponsor> sponsors) {
        super(context, 0, sponsors);
        mInflater = LayoutInflater.from(getContext());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_sponsor, parent, false);
        }
        SponsorFragment.Sponsor sponsor = getItem(position);
        ((ImageView) convertView).setImageResource(sponsor.logo);
        convertView.setContentDescription(sponsor.name + " logo");
        return convertView;
    }
}
