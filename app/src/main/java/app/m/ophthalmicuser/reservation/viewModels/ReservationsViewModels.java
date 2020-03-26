package app.m.ophthalmicuser.reservation.viewModels;


import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.reservation.adapters.ReservationsAdapter;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;


public class ReservationsViewModels extends BaseViewModel {
    private FirebaseFirestore firebaseFirestore;
    public List<ReservationsResponse> reservationsResponseList = new ArrayList<>();
    private ReservationsAdapter reservationsAdapter;

    public ReservationsViewModels() {
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @BindingAdapter({"app:reservationsAdapter"})
    public static void getDoctorsBinding(RecyclerView recyclerView, ReservationsAdapter reservationsAdapter) {
        recyclerView.setAdapter(reservationsAdapter);
    }

    @Bindable
    public ReservationsAdapter getReservationsAdapter() {
        return this.reservationsAdapter == null ? this.reservationsAdapter = new ReservationsAdapter() : this.reservationsAdapter;
    }


    public void getReservations() {
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
                                reservationsResponseList.add(object);
                            }
                        }
                        getReservationsAdapter().updateData(reservationsResponseList);
                    }
                }
            }
        });

    }

    public void toBack() {
        getClicksMutableLiveData().setValue(Codes.BACK);
    }
}
