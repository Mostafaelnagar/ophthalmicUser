package app.m.ophthalmicuser.medicalRecord.itemViewModels;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;

public class MedicalItemViewModel extends BaseViewModel {
    private ReservationsResponse reservationsResponse;

    public MedicalItemViewModel(ReservationsResponse reservationsResponse) {
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

    public void toRecordDetails() {
        getClicksMutableLiveData().setValue(Codes.MEDICAL_RECORD_DETAILS);
    }
}
