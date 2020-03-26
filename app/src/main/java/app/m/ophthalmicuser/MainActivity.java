package app.m.ophthalmicuser;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.ParentActivity;
import app.m.ophthalmicuser.customViews.NavView;
import app.m.ophthalmicuser.databinding.ActivityMainBinding;
import app.m.ophthalmicuser.home.HomeFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends ParentActivity {
    public ActivityMainBinding activityMainBinding;
    public MutableLiveData<Integer> dialogLiveData;
    public NavView navView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dialogLiveData = new MutableLiveData<>();
        navView = new NavView(this);
        activityMainBinding.llBaseContainer.addView(navView);
        MovementManager.addHomeFragment(this, new HomeFragment(), "");
    }

    public NavView getNavView() {
        return navView;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_home_container);
        fragment.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}