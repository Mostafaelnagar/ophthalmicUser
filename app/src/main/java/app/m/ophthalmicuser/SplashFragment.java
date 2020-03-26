package app.m.ophthalmicuser;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import java.util.Locale;

import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentSplashBinding;


public class SplashFragment extends BaseFragment {
    FragmentSplashBinding homeBinding;
    SplashScreenViewModel screenViewModel;


    public SplashFragment() {
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


        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        screenViewModel = new SplashScreenViewModel();
        homeBinding.setSplashScreenViewModel(screenViewModel);
        liveDataListeners();
        return homeBinding.getRoot();
    }


    private void liveDataListeners() {
        screenViewModel.getClicksMutableLiveData().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (UserPreferenceHelper.getInstance(getActivity()).getUserData() != null) {
                            MovementManager.startMainActivity(getActivity(), Codes.HOME_SCREEN);
                        } else
                            MovementManager.startBaseActivity(getActivity(), Codes.LOGIN_SCREEN);
                    }
                }
        );
    }


}
