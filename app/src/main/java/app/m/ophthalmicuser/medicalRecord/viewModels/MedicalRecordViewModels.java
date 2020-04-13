package app.m.ophthalmicuser.medicalRecord.viewModels;


import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.medicalRecord.adapters.MedicalAdapter;
import app.m.ophthalmicuser.reservation.adapters.ReservationsAdapter;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;


public class MedicalRecordViewModels extends BaseViewModel {
    private FirebaseFirestore firebaseFirestore;
    public List<ReservationsResponse> reservationsResponseList = new ArrayList<>();
    private MedicalAdapter medicalAdapter;

    public MedicalRecordViewModels() {
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @BindingAdapter({"app:medicalAdapter"})
    public static void getDoctorsBinding(RecyclerView recyclerView, MedicalAdapter medicalAdapter) {
        recyclerView.setAdapter(medicalAdapter);
    }

    @Bindable
    public MedicalAdapter getMedicalAdapter() {
        return this.medicalAdapter == null ? this.medicalAdapter = new MedicalAdapter() : this.medicalAdapter;
    }

    public void getMedicalRecords() {
        accessLoadingBar(View.VISIBLE);
        Query query = firebaseFirestore.collection("Reservations").whereEqualTo("user_id", UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData().getId());
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
                                ReservationsResponse object = doc.getDocument().toObject(ReservationsResponse.class);
                                object.setDoc_id(doc.getDocument().getId());
                                reservationsResponseList.add(object);
                            }
                        }
                        getMedicalAdapter().updateData(reservationsResponseList);
                    }
                }
            }
        });

    }

    public void toBack() {
        getClicksMutableLiveData().setValue(Codes.BACK);
    }
}
