package app.m.ophthalmicuser.auth;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.auth.viewModels.AuthViewModels;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.Validate;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentSignUpBinding;


public class SignUpFragment extends BaseFragment {
    FragmentSignUpBinding signUpBinding;
    AuthViewModels authViewModels;
    Resources resources;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        authViewModels = new AuthViewModels();
        signUpBinding.setSignUpViewModel(authViewModels);
        Validate.isInputValid(signUpBinding.inputEmail, signUpBinding.userEmail, 0, getActivity());
        Validate.isInputValid(signUpBinding.passwordLayout, signUpBinding.password, 1, getActivity());
        Validate.isInputValid(signUpBinding.inputPhone, signUpBinding.userPhone, 2, getActivity());
//
        checkConnection();

        liveDataListeners();

        return signUpBinding.getRoot();
    }

    private void liveDataListeners() {
        authViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == Codes.HOME_SCREEN) {
                MovementManager.startMainActivity(getActivity(), result);
            } else if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.CHECK_ERRORS) {
                if (TextUtils.isEmpty(signUpBinding.userEmail.getText())) {
                    signUpBinding.inputEmail.setError(getActivity().getResources().getString(R.string.invalidEmail));
                } else if (TextUtils.isEmpty(signUpBinding.password.getText())) {
                    signUpBinding.passwordLayout.setError(getActivity().getResources().getString(R.string.invalidPassword));
                } else if (TextUtils.isEmpty(signUpBinding.userPhone.getText())) {
                    signUpBinding.inputPhone.setError(getActivity().getResources().getString(R.string.invalidPhone));
                } else if (signUpBinding.inputEmail.getError() == null && signUpBinding.passwordLayout.getError() == null && signUpBinding.inputPhone.getError() == null) {
                    authViewModels.signUpAction();
                }
            } else if (result == Codes.SHOW_MESSAGE_SUCCESS) {
                showMessage(authViewModels.getReturnedMessage(), 0, 0);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(authViewModels.getReturnedMessage(), 0, 1);
            }
        });
        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    signUpBinding.btnSignUp.setEnabled(true);
                } else {
                    signUpBinding.btnSignUp.setEnabled(false);
                    showMessage(resources.getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });
    }


}
