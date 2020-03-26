package app.m.ophthalmicuser.medicalRecord;


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
import app.m.ophthalmicuser.databinding.FragmentMedicalRecordBinding;
import app.m.ophthalmicuser.medicalRecord.viewModels.MedicalRecordViewModels;


public class MedicalRecordFragment extends BaseFragment {
    FragmentMedicalRecordBinding recordBinding;
    MedicalRecordViewModels recordViewModels;


    public MedicalRecordFragment() {
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
        recordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_medical_record, container, false);
        recordViewModels = new MedicalRecordViewModels();
        recordBinding.setMedicalRecordViewModel(recordViewModels);
        liveDataListeners();
        checkConnection();
        return recordBinding.getRoot();
    }


    private void liveDataListeners() {
        recordViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.BACK) {
                ((Activity) getActivity()).finish();
            } else if (result == Codes.FILTER) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(recordViewModels.getReturnedMessage(), 0, 1);
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    recordViewModels.getMedicalRecords();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }


}
