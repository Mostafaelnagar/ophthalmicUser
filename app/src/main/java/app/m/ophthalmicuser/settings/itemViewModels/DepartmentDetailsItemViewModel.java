package app.m.ophthalmicuser.settings.itemViewModels;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.settings.models.DepartmentDetailsModel;
import app.m.ophthalmicuser.settings.models.Departmentmodel;

public class DepartmentDetailsItemViewModel extends BaseViewModel {
    private DepartmentDetailsModel detailsModel;

    public DepartmentDetailsItemViewModel(DepartmentDetailsModel detailsModel) {
        this.detailsModel = detailsModel;
    }


    @Bindable
    public DepartmentDetailsModel getDetailsModel() {
        return detailsModel;
    }

    @BindingAdapter({"departmentImage"})
    public static void loadImage(ImageView view, String departmentImage) {
        if (!departmentImage.equals(""))
            Glide.with(view.getContext()).load(departmentImage).into(view);
    }

}
