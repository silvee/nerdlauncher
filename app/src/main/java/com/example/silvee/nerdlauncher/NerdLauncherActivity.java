package com.example.silvee.nerdlauncher;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class NerdLauncherActivity extends SingleFragmentActivity {
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nerd_launcher_activity);

        FragmentManager fm = getSupportFragmentManager();

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    protected Fragment createFragment() {
        return NerdLauncherFragment.newInstance();
    }
}
