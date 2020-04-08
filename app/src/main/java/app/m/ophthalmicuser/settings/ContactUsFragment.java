package app.m.ophthalmicuser.settings;


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
import app.m.ophthalmicuser.booking.viewModels.CreateReservationsViewModels;
import app.m.ophthalmicuser.databinding.FragmentContactBinding;
import app.m.ophthalmicuser.databinding.FragmentCreateReservationBinding;
import app.m.ophthalmicuser.settings.viewModels.SettingsViewModels;


public class ContactUsFragment extends BaseFragment {
    FragmentContactBinding contactBinding;
    SettingsViewModels settingsViewModels;


    public ContactUsFragment() {
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
        contactBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        settingsViewModels = new SettingsViewModels();
        contactBinding.setContactViewModel(settingsViewModels);
        liveDataListeners();
        checkConnection();
        return contactBinding.getRoot();
    }


    private void liveDataListeners() {
        settingsViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(settingsViewModels.getReturnedMessage(), 0, 1);
            } else if (result == Codes.SHOW_MESSAGE_SUCCESS) {
                showMessage(settingsViewModels.getReturnedMessage(), 0, 0);
                settingsViewModels.goBack(getActivity());
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
//                    homeViewModels.getHomeData();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }


}
