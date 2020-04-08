package app.m.ophthalmicuser.home.itemViewModels;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.home.models.Posts;

public class HomeItemViewModel extends BaseViewModel {
    private Posts posts;

    public HomeItemViewModel(Posts posts) {
        this.posts = posts;
    }

    @Bindable
    public Posts getPosts() {
        return posts;
    }


    @BindingAdapter({"autherImage"})
    public static void loadImage(ImageView view, String countryImage) {

    }

}
