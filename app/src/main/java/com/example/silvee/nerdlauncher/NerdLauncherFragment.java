package com.example.silvee.nerdlauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by silvee on 19.12.2017.
 */

public class NerdLauncherFragment extends Fragment {
    public static final String TAG = "NerdLauncherFragment";


    private RecyclerView recyclerView;

    public static NerdLauncherFragment newInstance() {
        return new NerdLauncherFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(o1.loadLabel(pm).toString(), o2.loadLabel(pm).toString());
            }
        });
        Log.i(TAG, "Found " + activities.size() + " activities.");
        recyclerView.setAdapter(new ActivityAdapter(activities));
    }


    // Viewholder class
    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ResolveInfo resolveInfo;
        private TextView textView;

        public ActivityHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            textView.setText(resolveInfo.loadLabel(pm).toString());
        }

        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            Intent intent = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
            startActivity(intent);
        }
    }


    // Adapter class
    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {
        private List<ResolveInfo> activities;

        public ActivityAdapter(List<ResolveInfo> activities) {
            this.activities = activities;
        }


        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            holder.bindActivity(activities.get(position));
        }

        @Override
        public int getItemCount() {
            return activities.size();
        }
    }


}
