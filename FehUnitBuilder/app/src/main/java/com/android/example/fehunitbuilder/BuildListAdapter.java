/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * BuildListAdapter.java
 */

package com.android.example.fehunitbuilder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for the RecyclerView that displays a list of builds.
 */

public class BuildListAdapter extends RecyclerView.Adapter<BuildListAdapter.BuildViewHolder> {

    private final LayoutInflater mInflater;
    private List<Build> mBuilds; // Cached copy of builds
	private static ClickListener clickListener;

    BuildListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BuildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new BuildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BuildViewHolder holder, int position) {
        if (mBuilds != null) {
            Build current = mBuilds.get(position);
            holder.buildItemView.setText(current.getBuild());
        } else {
            // Covers the case of data not being ready yet.
            holder.buildItemView.setText(R.string.no_word);
        }
    }

    /**
     * Associates a list of builds with this adapter
    */
    void setBuilds(List<Build> builds) {
        mBuilds = builds;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mBuilds has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (mBuilds != null)
            return mBuilds.size();
        else return 0;
    }

    /**
     * Gets the build at a given position.
     * This method is useful for identifying which build
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the build in the RecyclerView
     * @return The build at the given position
     */
    public Build getBuildAtPosition(int position) {
        return mBuilds.get(position);
    }

    class BuildViewHolder extends RecyclerView.ViewHolder {
        private final TextView buildItemView;

        private BuildViewHolder(View itemView) {
            super(itemView);
            buildItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        BuildListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
