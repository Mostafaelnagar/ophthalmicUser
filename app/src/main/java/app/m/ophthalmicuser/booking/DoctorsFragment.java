package app.m.ophthalmicuser.booking;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.viewModels.DoctorsViewModels;
import app.m.ophthalmicuser.databinding.FragmentDoctorsBinding;
import app.m.ophthalmicuser.databinding.FragmentHomeBinding;
import app.m.ophthalmicuser.home.viewModels.HomeViewModels;


public class DoctorsFragment extends BaseFragment {
    FragmentDoctorsBinding doctorsBinding;
    DoctorsViewModels doctorsViewModels;


    public DoctorsFragment() {
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
        doctorsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_doctors, container, false);
        doctorsViewModels = new DoctorsViewModels();
        doctorsBinding.setDoctorsViewModel(doctorsViewModels);
        liveDataListeners();
        checkConnection();
        return doctorsBinding.getRoot();
    }


    private void liveDataListeners() {
        doctorsViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.BACK) {
                ((Activity) getActivity()).finish();
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(doctorsViewModels.getReturnedMessage(), 1, 1);
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    doctorsViewModels.getDoctors();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }


}
