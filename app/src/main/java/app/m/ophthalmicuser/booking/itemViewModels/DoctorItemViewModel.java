package app.m.ophthalmicuser.booking.itemViewModels;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;

import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;

public class DoctorItemViewModel extends BaseViewModel {
    private UserData userData;
    private MutableLiveData<UserData> itemsOperationsLiveListener;

    public DoctorItemViewModel(UserData userData) {
        Log.e("DoctorItemViewModel", "DoctorItemViewModel: " + userData.getUser_name());
        this.userData = userData;
        this.itemsOperationsLiveListener = new MutableLiveData<>();
    }


    public MutableLiveData<UserData> getItemsOperationsLiveListener() {
        return itemsOperationsLiveListener;
    }


    @Bindable
    public UserData getUserData() {
        return userData;
    }

    public void itemAction() {
        notifyChange();
        getClicksMutableLiveData().setValue(Codes.MAKE_RESERVATION);
    }

    public void toDoctorProfile() {
        getClicksMutableLiveData().setValue(Codes.DOCTOR_PROFILE);
    }

    @BindingAdapter({"doctorImage"})
    public static void loadImage(ImageView view, String countryImage) {
//        Glide.
    }

}
