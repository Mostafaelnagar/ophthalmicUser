package app.m.ophthalmicuser.auth;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.auth.viewModels.AuthViewModels;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.Validate;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentLoginBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {
    FragmentLoginBinding loginBinding;
    AuthViewModels authViewModels;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        authViewModels = new AuthViewModels();
         checkConnection();
        loginBinding.setLoginViewModel(authViewModels);
        liveDataListeners();
        Validate.isInputValid(loginBinding.inputPassword, loginBinding.password, 1, getActivity());
        Validate.isInputValid(loginBinding.inputUserName, loginBinding.userName, 0, getActivity());
        return loginBinding.getRoot();

    }


    private void liveDataListeners() {
        authViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == Codes.SEND_CODE_SCREEN) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.REGISTER_SCREEN) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.HOME_SCREEN) {
                MovementManager.startMainActivity(getActivity(), result);
            } else if (result == Codes.SHOW_MESSAGE_SUCCESS) {
                showMessage(authViewModels.getReturnedMessage(), 0, 0);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(authViewModels.getReturnedMessage(), 0, 1);
            } else if (result == Codes.CHECK_ERRORS) {
                if (TextUtils.isEmpty(loginBinding.userName.getText())) {
                    loginBinding.inputUserName.setError(getActivity().getResources().getString(R.string.invalidEmail));
                } else if (TextUtils.isEmpty(loginBinding.password.getText())) {
                    loginBinding.inputPassword.setError(getActivity().getResources().getString(R.string.invalidPassword));
                } else if (loginBinding.inputUserName.getError() == null && loginBinding.inputPassword.getError() == null) {
                    authViewModels.loginAction();
                }
            } else if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            }
        });
        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    loginBinding.btnLogin.setEnabled(true);
                } else {
                    loginBinding.btnLogin.setEnabled(false);
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });
    }

}
