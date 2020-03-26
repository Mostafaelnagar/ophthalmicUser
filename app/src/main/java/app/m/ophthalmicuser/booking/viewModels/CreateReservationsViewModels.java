package app.m.ophthalmicuser.booking.viewModels;


import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.rpc.Code;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.m.ophthalmicuser.PassingObject;
import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.MyApplication;
import app.m.ophthalmicuser.base.UserPreferenceHelper;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.models.DoctorDetails;


public class CreateReservationsViewModels extends BaseViewModel {
    PassingObject passingObject;
    UserData userData;
    private DoctorDetails doctorDetails;
    private FirebaseFirestore db;
    public String selectedDay;
    private List<String> workingDoctorDays = new ArrayList<>();

    public CreateReservationsViewModels() {
        passingObject = new PassingObject();
        userData = new UserData();
        doctorDetails = new DoctorDetails();
        db = FirebaseFirestore.getInstance();
    }

    public DoctorDetails getDoctorDetails() {
        return doctorDetails;
    }

    public void setDoctorDetails(DoctorDetails doctorDetails) {
        this.doctorDetails = doctorDetails;
    }

    public List<String> getWorkingDoctorDays() {
        return workingDoctorDays;
    }

    public void setWorkingDoctorDays(List<String> workingDoctorDays) {
        this.workingDoctorDays = workingDoctorDays;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public PassingObject getPassingObject() {
        return passingObject;
    }

    @Bindable
    public void setPassingObject(PassingObject passingObject) {
        setUserData(passingObject.getObjectClass());
        this.passingObject = passingObject;
    }

    public void createResevation() {
        if (selectedDay != null) {
            accessLoadingBar(View.VISIBLE);
            Map<String, Object> reserve = new HashMap<>();
            reserve.put("doctor", getUserData());
            reserve.put("user_id", UserPreferenceHelper.getInstance(MyApplication.getInstance()).getUserData().getId());
            reserve.put("day", selectedDay);
            reserve.put("status", "waiting");
            reserve.put("note", "");
            reserve.put("medicine", "");
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date dateobj = new Date();
            reserve.put("time", df.format(dateobj));
            db.collection("Reservations").document().set(reserve).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        setReturnedMessage("Reservation added, please wait confirmation ");
                        getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_SUCCESS);
                        toBack();
                    } else {
                        setReturnedMessage(task.getException().getMessage());
                        getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
                    }
                    accessLoadingBar(View.GONE);
                }
            });
        } else {
            setReturnedMessage("Please select day");
            getClicksMutableLiveData().setValue(Codes.SHOW_MESSAGE_ERROR);
        }
    }

    public void getWorkingDays() {
        accessLoadingBar(View.VISIBLE);
        DocumentReference docRef = db.collection("Doctor_details").document(userData.getId());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                accessLoadingBar(View.GONE);
                setDoctorDetails(documentSnapshot.toObject(DoctorDetails.class));
                if (getDoctorDetails() != null) {
                    setWorkingDoctorDays(new ArrayList<String>(Arrays.asList(doctorDetails.getWorking_days().split(","))));
                    notifyChange();
                }
            }
        });
    }

    public void showDoctorDays() {
        getClicksMutableLiveData().setValue(Codes.SHOW_DIALOG);
    }

    public void toBack() {
        getClicksMutableLiveData().setValue(Codes.BACK);
    }
}
