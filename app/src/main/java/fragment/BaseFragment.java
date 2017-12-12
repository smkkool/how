package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minhpvn.com.swipedemo.MainActivity;
import minhpvn.com.swipedemo.R;

/**
 * Created by smkko on 12/12/2017.
 */

public class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.base_fragment,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("Home");
        return view;
    }
}
