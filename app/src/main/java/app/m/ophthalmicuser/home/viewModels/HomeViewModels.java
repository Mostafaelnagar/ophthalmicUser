package app.m.ophthalmicuser.home.viewModels;


import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.home.adapters.HomeAdapter;
import app.m.ophthalmicuser.home.models.Posts;
import app.m.ophthalmicuser.settings.adapters.DepartmentDetailsAdapter;
import app.m.ophthalmicuser.settings.models.Departmentmodel;


public class HomeViewModels extends BaseViewModel {
    private FirebaseFirestore db;
    private List<Posts> postsList;
    private HomeAdapter homeAdapter;

    public HomeViewModels() {
        db = FirebaseFirestore.getInstance();
        postsList = new ArrayList<>();
    }

    @BindingAdapter({"app:homeAdapter"})
    public static void getDoctorsBinding(RecyclerView recyclerView, HomeAdapter homeAdapter) {
        recyclerView.setAdapter(homeAdapter);
    }

    @Bindable
    public HomeAdapter getHomeAdapter() {
        return this.homeAdapter == null ? this.homeAdapter = new HomeAdapter() : this.homeAdapter;
    }

    public void getPosts() {
        accessLoadingBar(View.VISIBLE);
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        accessLoadingBar(View.GONE);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Posts departmentmodel = document.toObject(Posts.class);
                                postsList.add(departmentmodel);
                            }
                            getHomeAdapter().updateData(postsList);
                        } else {
                            Log.w("df", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

}
