package app.m.ophthalmicuser.reservation.itemViewModels;

 import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
 import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;

public class ReservationsItemViewModel extends BaseViewModel {
    private ReservationsResponse reservationsResponse;

    public ReservationsItemViewModel(ReservationsResponse reservationsResponse) {
        this.reservationsResponse = reservationsResponse;
    }


    @Bindable
    public ReservationsResponse getReservationsResponse() {
        return reservationsResponse;
    }

    public void toDoctorProfile() {
        getClicksMutableLiveData().setValue(Codes.DOCTOR_PROFILE);
    }

    @BindingAdapter({"doctorImage"})
    public static void loadImage(ImageView view, String countryImage) {
//        Glide.
    }

}
