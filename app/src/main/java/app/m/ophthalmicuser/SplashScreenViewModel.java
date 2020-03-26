package app.m.ophthalmicuser;


import android.content.Intent;
import android.os.Handler;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;

public class SplashScreenViewModel extends BaseViewModel {
    public SplashScreenViewModel() {
        startApp();
    }


    private void startApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getClicksMutableLiveData().setValue(Codes.LOGIN_SCREEN);
            }
        }, 2000);

    }

}
