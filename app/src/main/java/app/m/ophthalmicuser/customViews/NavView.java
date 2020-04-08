package app.m.ophthalmicuser.customViews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;

import app.m.ophthalmicuser.MainActivity;
import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.CustomeNavigationDrawerBinding;

public class NavView extends RelativeLayout {
    public CustomeNavigationDrawerBinding drawerBinding;
    NavViewModel navViewModel;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    Context context;

    public NavView(Context context) {
        super(context);
        this.context = context;
        onCreateView();
    }

    public NavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreateView();
    }

    public NavView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onCreateView();
    }


    public void onCreateView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        drawerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.custome_navigation_drawer, this, true);

        navViewModel = new NavViewModel();
        drawerBinding.setVavViewModel(navViewModel);
        setupToolbar();
        setupDrawerToggle();
        liveDataListeners();

    }


    public NavViewModel getNavViewModel() {
        return navViewModel;
    }

    void setupToolbar() {
        toolbar = (Toolbar) drawerBinding.customToolbar;
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle((AppCompatActivity) getActivity(), drawerBinding.customDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerToggle.setToolbarNavigationClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserPreferenceHelper.getInstance(context).getUserData() != null) {
                    navViewModel.setIsLogged(1);
                    navViewModel.setCheckLogin(0);
                    navViewModel.userName = UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData().getUser_name();
                    navViewModel.userImage = UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData().getImage();
                } else
                    navViewModel.setCheckLogin(1);
                drawerBinding.customDrawer.openDrawer(GravityCompat.START);
            }
        });
        mDrawerToggle.syncState();
    }

    private void liveDataListeners() {
        navViewModel.getClicksMutableLiveData().observeForever(result -> {
            if (result == Codes.NOTIFICATIONS) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.BOOKING) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.MEDICAL_RECORD) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.LOGIN_SCREEN) {
                MovementManager.startActivity(context, result);
            } else if (result == Codes.LOG_OUT) {
                loggout();
            } else if (result == Codes.DEPARTMENT) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.CONTACT) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.ABOUT) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.PROFILE) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.USER_FOLLOWS) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.MY_ORDERS) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.REGISTER_SHOP) {
                MovementManager.startActivity(getActivity(), result);
            } else if (result == Codes.SPLASH) {
                MovementManager.startBaseActivity(getActivity(), result);
            }
//
            drawerBinding.customDrawer.closeDrawer(GravityCompat.START);
        });

    }


    public void loggout() {
        final Dialog dialog = new Dialog(context, R.style.PauseDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.log_out_dialog);
        TextView confirmDialog = dialog.findViewById(R.id.agree);
        TextView decline = dialog.findViewById(R.id.decline);

        confirmDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                MovementManager.loggout(context);
                dialog.dismiss();
            }
        });
        decline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    Context getActivity() {
        return getContext();
    }


}
