package com.wic.buildher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Fragment for the Sponsors tab of the application
 */
public class SponsorFragment extends WatchableFragment implements AdapterView.OnItemClickListener {
    /**
     * Class representing a sponsor
     */
    public static class Sponsor {
        String name;
        String websiteUrl;
        @DrawableRes int logo;

        public Sponsor(String name, String websiteUrl, int logo) {
            this.name = name;
            this.websiteUrl = websiteUrl;
            this.logo = logo;
        }
    }

    public static SponsorFragment newInstance() {
        return new SponsorFragment();
    }

    @BindView(R.id.sponsor_list) ListView mSponsorList;
    private List<Sponsor> mSponsors;

    public SponsorFragment() {
        mSponsors = new ArrayList<>();
        mSponsors.add(new Sponsor("Microsoft",
                "http://www.microsoft.com",
                R.drawable.logo_microsoft));
        mSponsors.add(new Sponsor("Deloitte",
                "http://www.deloitte.com",
                R.drawable.logo_deloitte));
        mSponsors.add(new Sponsor("Diamond Assets",
                "http://www.diamond-assets.com",
                R.drawable.logo_diamond_assets));
        mSponsors.add(new Sponsor("Intel",
                "http://www.intel.com",
                R.drawable.logo_intel));
        mSponsors.add(new Sponsor("Github",
                "http://www.github.com",
                R.drawable.logo_github));
        mSponsors.add(new Sponsor("Morningstar",
                "http://www.morningstar.com",
                R.drawable.logo_morningstar));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call to super required for WatchableFragment to know
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_sponsors, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSponsorList.setAdapter(new SponsorListAdapter(getActivity(), mSponsors));
        mSponsorList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri website = Uri.parse(mSponsors.get(position).websiteUrl);
        startActivity(new Intent(Intent.ACTION_VIEW, website));
    }
}
