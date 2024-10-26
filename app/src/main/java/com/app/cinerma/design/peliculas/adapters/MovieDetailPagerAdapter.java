package com.app.cinerma.design.peliculas.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.cinerma.design.cines.frament.Fragment_Cine_selection;

public class MovieDetailPagerAdapter extends FragmentStateAdapter {

    public MovieDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_Cine_selection();
            default:
                return new Fragment(); // or any default fragment
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Número de tabs
    }
}
