package app.m.ophthalmicuser;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import app.m.ophthalmicuser.auth.EmailConfirmationFragment;
import app.m.ophthalmicuser.auth.LoginFragment;
import app.m.ophthalmicuser.auth.SignUpFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.Params;
import app.m.ophthalmicuser.base.ParentActivity;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.CreateReservationFragment;
import app.m.ophthalmicuser.booking.DoctorsFragment;
import app.m.ophthalmicuser.booking.DoctorsProfileFragment;
import app.m.ophthalmicuser.databinding.ActivityBaseBinding;
import app.m.ophthalmicuser.medicalRecord.MedicalRecordDetailsFragment;
import app.m.ophthalmicuser.medicalRecord.MedicalRecordFragment;
import app.m.ophthalmicuser.profile.ProfileFragment;
import app.m.ophthalmicuser.profile.UpdateAuthFragment;
import app.m.ophthalmicuser.reservation.ReservationsFragment;
import app.m.ophthalmicuser.settings.ContactUsFragment;
import app.m.ophthalmicuser.settings.DepartmentsDetailsFragment;
import app.m.ophthalmicuser.settings.DepartmentsFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class BaseActivity extends ParentActivity {
    public ActivityBaseBinding activityBaseBinding;
    public String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font1.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        super.onCreate(savedInstanceState);
        activityBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        lang = UserPreferenceHelper.getInstance(this).getCurrentLanguage(this);
        if (getIntent().hasExtra(Params.INTENT_PAGE)) {
            addFragment(getIntent().getIntExtra(Params.INTENT_PAGE, 0));
        } else {
            addFragment(Codes.SPLASH);
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("getInstanceId", "getInstanceId failed" + task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        UserPreferenceHelper.getInstance(BaseActivity.this).saveToken(token);
                    }
                });
    }


    private void addFragment(int page) {

        if (page == Codes.SPLASH) {
            MovementManager.addFragment(this, new SplashFragment(), "");
        } else if (page == Codes.LOGIN_SCREEN) {
            MovementManager.addFragment(this, new LoginFragment(), "");
        } else if (page == Codes.REGISTER_SCREEN) {
            MovementManager.addFragment(this, new SignUpFragment(), "");
        } else if (page == Codes.SEND_CODE_SCREEN) {
            MovementManager.addFragment(this, new EmailConfirmationFragment(), "");
        } else if (page == Codes.BOOKING) {
            MovementManager.addFragment(this, new DoctorsFragment(), "");
        } else if (page == Codes.MY_ORDERS) {
            MovementManager.addFragment(this, new ReservationsFragment(), "");
        } else if (page == Codes.MEDICAL_RECORD) {
            MovementManager.addFragment(this, new MedicalRecordFragment(), "");
        } else if (page == Codes.PROFILE) {
            MovementManager.addFragment(this, new ProfileFragment(), "");
        } else if (page == Codes.CONTACT) {
            MovementManager.addFragment(this, new ContactUsFragment(), "");
        } else if (page == Codes.DEPARTMENT) {
            MovementManager.addFragment(this, new DepartmentsFragment(), "");
        } else if (page == Codes.MAKE_RESERVATION) {
            CreateReservationFragment fragment = new CreateReservationFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Params.BUNDLE, getIntent().getStringExtra(Params.BUNDLE));
            fragment.setArguments(bundle);
            MovementManager.addFragment(this, fragment, "");
        } else if (page == Codes.DOCTOR_PROFILE) {
            DoctorsProfileFragment fragment = new DoctorsProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Params.BUNDLE, getIntent().getStringExtra(Params.BUNDLE));
            fragment.setArguments(bundle);
            MovementManager.addFragment(this, fragment, "");
        } else if (page == Codes.MEDICAL_RECORD_DETAILS) {
            MedicalRecordDetailsFragment fragment = new MedicalRecordDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Params.BUNDLE, getIntent().getStringExtra(Params.BUNDLE));
            fragment.setArguments(bundle);
            MovementManager.addFragment(this, fragment, "");
        } else if (page == Codes.UPDATE_AUTH || page == Codes.UPDATE_DATA) {
            UpdateAuthFragment fragment = new UpdateAuthFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Params.BUNDLE, getIntent().getStringExtra(Params.BUNDLE));
            fragment.setArguments(bundle);
            MovementManager.addFragment(this, fragment, "");
        }else if (page == Codes.DEPARTMENT_DETAILS ) {
            DepartmentsDetailsFragment fragment = new DepartmentsDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Params.BUNDLE, getIntent().getStringExtra(Params.BUNDLE));
            fragment.setArguments(bundle);
            MovementManager.addFragment(this, fragment, "");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

}