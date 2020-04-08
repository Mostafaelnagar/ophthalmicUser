package app.m.ophthalmicuser.customViews;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;


public class NavViewModel extends BaseViewModel {
    public int isLogged, checkLogin;
    public String userAddress, userName, userImage, userFollows;

    public NavViewModel() {
        if (UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData() != null) {
            setIsLogged(1);
            setCheckLogin(0);
            userName = UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData().getUser_name();
            userImage = UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData().getImage();
        }
    }

    @BindingAdapter({"profileImg"})
    public static void loadImage(ImageView view, String productImage) {
        if (!productImage.equals(""))
            Glide.with(view.getContext()).load(productImage).into(view);
    }

    public int getCheckLogin() {
        return checkLogin == 0 ? View.GONE : View.VISIBLE;
    }

    public void setCheckLogin(int checkLogin) {
        this.checkLogin = checkLogin;
    }

    public int getIsLogged() {
        return isLogged == 0 ? View.GONE : View.VISIBLE;
    }

    public void setIsLogged(int isLogged) {
        notifyChange();
        this.isLogged = isLogged;
    }

    public void toProfile() {
        getClicksMutableLiveData().setValue(Codes.PROFILE);
    }

    public void toContact() {
        getClicksMutableLiveData().setValue(Codes.CONTACT);
    }

    public void toDepartment() {
        getClicksMutableLiveData().setValue(Codes.DEPARTMENT);
    }

    public void toMedicalRecord() {
        getClicksMutableLiveData().setValue(Codes.MEDICAL_RECORD);
    }

    public void toRate() {
//        getClicksMutableLiveData().setValue(Codes.EDIT_PROFILE);
    }

    public void toShop() {
        getClicksMutableLiveData().setValue(Codes.REGISTER_SHOP);
    }

    public void toTerms() {
        getClicksMutableLiveData().setValue(Codes.TERMS);
    }

    public void logOut() {
        getClicksMutableLiveData().setValue(Codes.LOG_OUT);
    }

    public void toSupport() {
        getClicksMutableLiveData().setValue(Codes.SUPPORT);
    }

    public void toMyOrders() {
        getClicksMutableLiveData().setValue(Codes.MY_ORDERS);
    }

    public void toAbout() {
        getClicksMutableLiveData().setValue(Codes.ABOUT);
    }

    public void toHome() {
//        getClicksMutableLiveData().setValue(Codes.HOME_SCREEN);
    }

    public void changeLang() {
        getClicksMutableLiveData().setValue(Codes.SPLASH);
    }

    public void grandInfo() {
//        getClicksMutableLiveData().setValue(Codes.GRAND_INFO);
    }

    public void toBooking() {
        getClicksMutableLiveData().setValue(Codes.BOOKING);
    }

    public void toNotifications() {
        getClicksMutableLiveData().setValue(Codes.NOTIFICATIONS);
    }

    public void toCart() {
        getClicksMutableLiveData().setValue(Codes.CART);
    }

    public void toOpenAddressDialog() {
        getClicksMutableLiveData().setValue(Codes.DIALOG_SHOW);
    }


}
