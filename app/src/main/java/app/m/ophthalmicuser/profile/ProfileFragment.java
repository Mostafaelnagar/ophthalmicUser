package app.m.ophthalmicuser.profile;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

import app.m.ophthalmicuser.PassingObject;
import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.auth.viewModels.AuthViewModels;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.Params;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.Validate;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.FragmentProfileBinding;
import app.m.ophthalmicuser.databinding.FragmentSignUpBinding;
import app.m.ophthalmicuser.profile.viewModels.ProfileViewModels;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends BaseFragment {
    FragmentProfileBinding fragmentProfileBinding;
    ProfileViewModels profileViewModels;
    Uri filePath;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        profileViewModels = new ProfileViewModels();
        fragmentProfileBinding.setProfileViewModel(profileViewModels);

        checkConnection();
        liveDataListeners();

        return fragmentProfileBinding.getRoot();
    }

    private void liveDataListeners() {
        profileViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == Codes.SELECT_PROFILE_IMAGE) {
                pickUpImage();
            } else if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.SHOW_MESSAGE_SUCCESS) {
                showMessage(profileViewModels.getReturnedMessage(), 0, 0);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(profileViewModels.getReturnedMessage(), 0, 1);
            } else if (result == Codes.BACK) {
                ((Activity) getActivity()).finish();
            } else if (result == Codes.UPDATE_AUTH) {
                MovementManager.startActivityWithObject(getActivity(), result, new PassingObject(Codes.UPDATE_AUTH));
            } else if (result == Codes.UPDATE_DATA) {
                MovementManager.startActivityWithObject(getActivity(), result, new PassingObject(Codes.UPDATE_DATA));
            }
        });
        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {

                } else {
                    showMessage(getActivity().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });
    }

    private void pickUpImage() {
        // Defining Implicit Intent to mobile gallery
//        MovementManager.pickImage(getActivity());
        CropImage.activity()
                .start(getContext(), this);
    }

    // Override onActivityResult method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            // Get the Uri of data
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                fragmentProfileBinding.logo.setImageURI(resultUri);
                profileViewModels.filePath = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
