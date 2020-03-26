package app.m.ophthalmicuser.booking.viewModels;


import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.adapters.DoctorsAdapter;


public class DoctorsViewModels extends BaseViewModel {
    private FirebaseFirestore firebaseFirestore;
    public List<UserData> userDataList = new ArrayList<>();
    private DoctorsAdapter doctorsAdapter;

    public DoctorsViewModels() {
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @BindingAdapter({"app:doctorsAdapter"})
    public static void getDoctorsBinding(RecyclerView recyclerView, DoctorsAdapter doctorsAdapter) {
        recyclerView.setAdapter(doctorsAdapter);
    }

    @Bindable
    public DoctorsAdapter getDoctorsAdapter() {
        return this.doctorsAdapter == null ? this.doctorsAdapter = new DoctorsAdapter() : this.doctorsAdapter;
    }

    public void getDoctors() {
        accessLoadingBar(View.VISIBLE);
        Query query = firebaseFirestore.collection("Users").whereEqualTo("type", "1");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
//                    Toast.makeText(getActivity(), "not error", Toast.LENGTH_SHORT).show();
                } else {
                    if (queryDocumentSnapshots.isEmpty()) {
                        accessLoadingBar(View.GONE);
                    } else {
                        accessLoadingBar(View.GONE);
                        for (final DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                final UserData userData = doc.getDocument().toObject(UserData.class);
                                userData.setId(doc.getDocument().getId());
                                userDataList.add(userData);
                            }
                        }
                        getDoctorsAdapter().updateData(userDataList);
                    }
                }
            }
        });
    }

    public void toBack() {
        getClicksMutableLiveData().setValue(Codes.BACK);
    }
}
