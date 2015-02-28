package net.onefree.aiphone.ui.fragment;

import android.app.Fragment;

/**
 * Created by maoah on 14-10-18.
 */
public abstract class BaseFragment extends Fragment {
    private final String NAME = BaseFragment.class.getSimpleName();

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }
}
