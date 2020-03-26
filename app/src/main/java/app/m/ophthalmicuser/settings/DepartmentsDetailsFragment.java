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
import app.m.ophthalmicuser.databinding.FragmentDepartmentsBinding;
import app.m.ophthalmicuser.settings.viewModels.SettingsViewModels;


public class DepartmentsDetailsFragment extends BaseFragment {
    FragmentDepartmentsBinding departmentsBinding;
    SettingsViewModels settingsViewModels;


    public DepartmentsDetailsFragment() {
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
        departmentsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_departments, container, false);
        settingsViewModels = new SettingsViewModels();
        departmentsBinding.setDepartmentViewModel(settingsViewModels);
        liveDataListeners();
        checkConnection();
        return departmentsBinding.getRoot();
    }


    private void liveDataListeners() {
        settingsViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.SEARCH) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.FILTER) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(settingsViewModels.getReturnedMessage(), 1, 1);
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
