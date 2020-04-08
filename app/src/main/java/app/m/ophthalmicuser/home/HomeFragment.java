package app.m.ophthalmicuser.home;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentHomeBinding;
import app.m.ophthalmicuser.home.adapters.SliderProductAdapter;
import app.m.ophthalmicuser.home.viewModels.HomeViewModels;


public class HomeFragment extends BaseFragment {
    FragmentHomeBinding homeBinding;
    HomeViewModels homeViewModels;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModels = new HomeViewModels();
        homeBinding.setHomeViewModel(homeViewModels);
        List<Drawable> drawableList = new ArrayList<>();
        drawableList.add(getResources().getDrawable(R.drawable.s1, null));
        drawableList.add(getResources().getDrawable(R.drawable.s2, null));
        drawableList.add(getResources().getDrawable(R.drawable.s3, null));
        drawableList.add(getResources().getDrawable(R.drawable.s4, null));
        homeBinding.imageSlider.setSliderAdapter(new SliderProductAdapter(getActivity(), drawableList));
        homeBinding.imageSlider.startAutoCycle();

        liveDataListeners();
        checkConnection();
        return homeBinding.getRoot();
    }


    private void liveDataListeners() {
        homeViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            }else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(homeViewModels.getReturnedMessage(), 1, 1);
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    homeViewModels.getPosts();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }


}
