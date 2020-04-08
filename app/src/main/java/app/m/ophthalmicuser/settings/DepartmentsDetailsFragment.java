package app.m.ophthalmicuser.settings;


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
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.Params;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentDepartmentDetailsBinding;
import app.m.ophthalmicuser.databinding.FragmentDepartmentsBinding;
import app.m.ophthalmicuser.settings.viewModels.SettingsViewModels;


public class DepartmentsDetailsFragment extends BaseFragment {
    FragmentDepartmentDetailsBinding detailsBinding;
    SettingsViewModels settingsViewModels;
    private String passingObject;
    private Bundle bundle;


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
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_department_details, container, false);
        settingsViewModels = new SettingsViewModels();
        detailsBinding.setDepartmentViewModel(settingsViewModels);
        bundle = this.getArguments();
        liveDataListeners();
        checkConnection();
        return detailsBinding.getRoot();
    }


    private void liveDataListeners() {
        settingsViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(settingsViewModels.getReturnedMessage(), 1, 1);
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    if (bundle != null) {
                        passingObject = bundle.getString(Params.BUNDLE);
                        settingsViewModels.setPassingObject(new Gson().fromJson(passingObject, PassingObject.class));
                        settingsViewModels.getDepartmentNews();
                    }
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }


}
