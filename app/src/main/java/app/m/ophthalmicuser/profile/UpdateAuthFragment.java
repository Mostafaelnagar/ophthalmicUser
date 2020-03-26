package app.m.ophthalmicuser.profile;


import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.auth.viewModels.AuthViewModels;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.Validate;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentSignUpBinding;
import app.m.ophthalmicuser.databinding.FragmentUpdateProfileBinding;
import app.m.ophthalmicuser.profile.viewModels.ProfileViewModels;


public class UpdateAuthFragment extends BaseFragment {
    FragmentUpdateProfileBinding updateProfileBinding;
    ProfileViewModels profileViewModels;
    Resources resources;

    public UpdateAuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        updateProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_profile, container, false);
        profileViewModels = new ProfileViewModels();
        updateProfileBinding.setSignUpViewModel(profileViewModels);
        Validate.isInputValid(updateProfileBinding.inputEmail, updateProfileBinding.userEmail, 0, getActivity());
        Validate.isInputValid(updateProfileBinding.passwordLayout, updateProfileBinding.password, 1, getActivity());
        Validate.isInputValid(updateProfileBinding.inputPhone, updateProfileBinding.userPhone, 2, getActivity());
//
        checkConnection();

        liveDataListeners();

        return updateProfileBinding.getRoot();
    }

    private void liveDataListeners() {
        profileViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == Codes.HOME_SCREEN) {
                MovementManager.startMainActivity(getActivity(), result);
            } else if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.CHECK_ERRORS) {
                if (updateProfileBinding.inputEmail.getError() == null && updateProfileBinding.passwordLayout.getError() == null && updateProfileBinding.inputPhone.getError() == null) {
//                    profileViewModels.signUpAction();
                } else if (updateProfileBinding.inputEmail.getError() != null) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
                } else if (updateProfileBinding.passwordLayout.getError() != null) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.invalidPassword), Toast.LENGTH_SHORT).show();
                } else if (updateProfileBinding.inputPhone.getError() != null) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.invalidPhone), Toast.LENGTH_SHORT).show();
                }
            } else if (result == Codes.SHOW_MESSAGE_SUCCESS) {
                showMessage(profileViewModels.getReturnedMessage(), 0, 0);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(profileViewModels.getReturnedMessage(), 0, 1);
            }
        });
        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    updateProfileBinding.btnSignUp.setEnabled(true);
                } else {
                    updateProfileBinding.btnSignUp.setEnabled(false);
                    showMessage(resources.getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });
    }


}
