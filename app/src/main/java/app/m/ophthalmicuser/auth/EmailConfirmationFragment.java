package app.m.ophthalmicuser.auth;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.auth.viewModels.ForgetPasswordsViewModels;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentEmailConfirmationBinding;


public class EmailConfirmationFragment extends BaseFragment {
    FragmentEmailConfirmationBinding confirmationBinding;
    ForgetPasswordsViewModels passwordsViewModels;

    public EmailConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        confirmationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_email_confirmation, container, false);
        passwordsViewModels = new ForgetPasswordsViewModels();
        confirmationBinding.setForgetViewModels(passwordsViewModels);
        checkConnection();
        liveDataListeners();
        return confirmationBinding.getRoot();

    }

    private void liveDataListeners() {
        passwordsViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == Codes.BACK) {
                ((Activity) getActivity()).finish();
            } else if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(passwordsViewModels.getReturnedMessage(), 0, 1);
            } else if (result == Codes.SHOW_MESSAGE_SUCCESS) {
                showMessage(passwordsViewModels.getReturnedMessage(), 0, 0);
            }
        });
        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    confirmationBinding.btnForget.setEnabled(true);
                } else {
                    confirmationBinding.btnForget.setEnabled(false);
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });
    }
}
