package app.m.ophthalmicuser.settings.itemViewModels;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;
import app.m.ophthalmicuser.settings.models.Departmentmodel;

public class DepartmentItemViewModel extends BaseViewModel {
    private Departmentmodel departmentmodel;

    public DepartmentItemViewModel(Departmentmodel departmentmodel) {
        this.departmentmodel = departmentmodel;
    }


    @Bindable
    public Departmentmodel getDepartmentmodel() {
        return departmentmodel;
    }

    public void toDepartmentDetails() {
        getClicksMutableLiveData().setValue(Codes.DEPARTMENT_DETAILS);
    }

    @BindingAdapter({"departmentImage"})
    public static void loadImage(ImageView view, String departmentImage) {
        if (!departmentImage.equals(""))
            Glide.with(view.getContext()).load(departmentImage).into(view);
    }

}
