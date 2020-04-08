package app.m.ophthalmicuser.settings.viewModels;


import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.m.ophthalmicuser.PassingObject;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.reservation.adapters.ReservationsAdapter;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;
import app.m.ophthalmicuser.settings.adapters.DepartmentAdapter;
import app.m.ophthalmicuser.settings.adapters.DepartmentDetailsAdapter;
import app.m.ophthalmicuser.settings.models.DepartmentDetailsModel;
import app.m.ophthalmicuser.settings.models.Departmentmodel;
import app.m.ophthalmicuser.settings.models.SettingsRequest;


public class SettingsViewModels extends BaseViewModel {
    private FirebaseFirestore db;
    private SettingsRequest settingsRequest;
    private DepartmentAdapter departmentAdapter;
    private DepartmentDetailsAdapter detailsAdapter;
    private List<Departmentmodel> departmentmodelList;
    private List<DepartmentDetailsModel> departmentDetailsModelList;
    private PassingObject passingObject;

    public SettingsViewModels() {
        passingObject = new PassingObject();
        db = FirebaseFirestore.getInstance();
        settingsRequest = new SettingsRequest();
        departmentmodelList = new ArrayList<>();
        departmentDetailsModelList = new ArrayList<>();
    }

    public PassingObject getPassingObject() {
        return passingObject;
    }

    @Bindable
    public void setPassingObject(PassingObject passingObject) {
        notifyPropertyChanged(app.m.ophthalmicuser.BR.passingObject);
        this.passingObject = passingObject;
    }

    public SettingsRequest getSettingsRequest() {
        return settingsRequest;
    }

    @BindingAdapter({"app:departmentAdapter"})
    public static void getDoctorsBinding(RecyclerView recyclerView, DepartmentAdapter departmentAdapter) {
        recyclerView.setAdapter(departmentAdapter);
    }

    @Bindable
    public DepartmentAdapter getDepartmentAdapter() {
        return this.departmentAdapter == null ? this.departmentAdapter = new DepartmentAdapter() : this.departmentAdapter;
    }

    @BindingAdapter({"app:detailsAdapter"})
    public static void getDoctorsBinding(RecyclerView recyclerView, DepartmentDetailsAdapter detailsAdapter) {
        recyclerView.setAdapter(detailsAdapter);
    }

    @Bindable
    public DepartmentDetailsAdapter getDetailsAdapter() {
        return this.detailsAdapter == null ? this.detailsAdapter = new DepartmentDetailsAdapter() : this.detailsAdapter;
    }

    public void getDepartmentNews() {
        accessLoadingBar(View.VISIBLE);
        Query query = db.collection("Department_News").whereEqualTo("department_id", getPassingObject().getObject());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                } else {
                    if (queryDocumentSnapshots.isEmpty()) {
                        accessLoadingBar(View.GONE);
                    } else {
                        accessLoadingBar(View.GONE);
                        for (final DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                DepartmentDetailsModel object = doc.getDocument().toObject(DepartmentDetailsModel.class);
                                 departmentDetailsModelList.add(object);
                            }
                        }
                        getDetailsAdapter().updateData(departmentDetailsModelList);
                    }
                }
            }
        });

    }

    public void getDepartments() {
        accessLoadingBar(View.VISIBLE);
        db.collection("Departments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        accessLoadingBar(View.GONE);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("df", document.getId() + " => " + document.getData());
                                Departmentmodel departmentmodel = document.toObject(Departmentmodel.class);
                                departmentmodelList.add(departmentmodel);
                            }
                            getDepartmentAdapter().updateData(departmentmodelList);
                        } else {
                            Log.w("df", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public void sendContact() {
        if (getSettingsRequest().isContactUs()) {
            accessLoadingBar(View.VISIBLE);
            Map<String, Object> contact = new HashMap<>();
            contact.put("message", getSettingsRequest().getMessage());
            contact.put("user_id", UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData().getId());

            db.collection("ContactUs").document().set(contact).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        setReturnedMessage("Send Successfully");
                        getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_SUCCESS);
                    } else {
                        setReturnedMessage(task.getException().getMessage());
                        getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                    }
                    accessLoadingBar(View.GONE);
                }
            });
        } else {
            setReturnedMessage("Please enter message");
            getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
        }
    }

}
