package app.m.ophthalmicuser.booking;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;

import app.m.ophthalmicuser.PassingObject;
import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.Params;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.viewModels.CreateReservationsViewModels;
import app.m.ophthalmicuser.booking.viewModels.DoctorsViewModels;
import app.m.ophthalmicuser.databinding.FragmentDoctorProfileBinding;
import app.m.ophthalmicuser.databinding.FragmentDoctorsBinding;


public class DoctorsProfileFragment extends BaseFragment {
    FragmentDoctorProfileBinding doctorProfileBinding;
    CreateReservationsViewModels doctorsProfileViewModels;
    Bundle bundle;
    String passingObject;

    public DoctorsProfileFragment() {
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
        doctorProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_doctor_profile, container, false);
        doctorsProfileViewModels = new CreateReservationsViewModels();
        doctorProfileBinding.setReserveViewModel(doctorsProfileViewModels);
        bundle = this.getArguments();
        if (bundle != null) {
            passingObject = bundle.getString(Params.BUNDLE);
            doctorsProfileViewModels.setPassingObject(new Gson().fromJson(passingObject, PassingObject.class));
        }
        liveDataListeners();
        checkConnection();
        return doctorProfileBinding.getRoot();
    }


    private void liveDataListeners() {
        doctorsProfileViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.BACK) {
                ((Activity) getActivity()).finish();
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(doctorsProfileViewModels.getReturnedMessage(), 0, 1);
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    doctorsProfileViewModels.getWorkingDays();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }


}
