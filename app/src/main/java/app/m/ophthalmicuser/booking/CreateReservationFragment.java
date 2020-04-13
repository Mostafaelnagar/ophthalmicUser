package app.m.ophthalmicuser.booking;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import app.m.ophthalmicuser.PassingObject;
import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.Params;
import app.m.ophthalmicuser.base.PopUpMenus;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.viewModels.CreateReservationsViewModels;
import app.m.ophthalmicuser.databinding.FragmentCreateReservationBinding;


public class CreateReservationFragment extends BaseFragment {
    FragmentCreateReservationBinding reservationBinding;
    CreateReservationsViewModels reservationsViewModels;
    Bundle bundle;
    String passingObject;
    private List<String> AvailableDates = new ArrayList<>();

    public CreateReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reservationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_reservation, container, false);
        reservationsViewModels = new CreateReservationsViewModels();
        reservationBinding.setReserveViewModel(reservationsViewModels);
        bundle = this.getArguments();
        if (bundle != null) {
            passingObject = bundle.getString(Params.BUNDLE);
            reservationsViewModels.setPassingObject(new Gson().fromJson(passingObject, PassingObject.class));
        }
        liveDataListeners();
        checkConnection();
        return reservationBinding.getRoot();
    }


    private void liveDataListeners() {
        reservationsViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.SHOW_DIALOG) {
                showPopUpMenu();
            } else if (result == Codes.SHOW_DATES) {
                showAvailableDates();
            } else if (result == Codes.BACK) {
                ((Activity) getActivity()).finish();
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(reservationsViewModels.getReturnedMessage(), 0, 1);
            } else if (result == Codes.SHOW_MESSAGE_SUCCESS) {
                showMessage(reservationsViewModels.getReturnedMessage(), 0, 0);
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    reservationsViewModels.getWorkingDays();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }

    private void showAvailableDates() {
        PopUpMenus.showWorkingDaysPopUp(getActivity(), reservationBinding.workingDates, AvailableDates).
                setOnMenuItemClickListener(item -> {
                    reservationBinding.workingDates.setText(AvailableDates.get(item.getItemId()));
                    reservationsViewModels.selectedDate = AvailableDates.get(item.getItemId());
                    reservationsViewModels.notifyChange();
                    return false;
                });
    }

    private void showPopUpMenu() {
        PopUpMenus.showWorkingDaysPopUp(getActivity(), reservationBinding.workingDays, reservationsViewModels.getWorkingDoctorDays()).
                setOnMenuItemClickListener(item -> {
                    reservationBinding.workingDays.setText(reservationsViewModels.getWorkingDoctorDays().get(item.getItemId()));
                    reservationsViewModels.selectedDay = reservationsViewModels.getWorkingDoctorDays().get(item.getItemId());
                    if (reservationsViewModels.selectedDay.equals("Saturday")) {
                        getFutureDates(0);
                    } else if (reservationsViewModels.selectedDay.equals("Sunday")) {
                        getFutureDates(1);
                    } else if (reservationsViewModels.selectedDay.equals("Monday")) {
                        getFutureDates(2);
                    } else if (reservationsViewModels.selectedDay.equals("Tuesday")) {
                        getFutureDates(3);
                    } else if (reservationsViewModels.selectedDay.equals("Wednesday")) {
                        getFutureDates(4);
                    } else if (reservationsViewModels.selectedDay.equals("Thursday")) {
                        getFutureDates(5);
                    } else if (reservationsViewModels.selectedDay.equals("Friday")) {
                        getFutureDates(6);
                    }
                    reservationsViewModels.notifyChange();
                    return false;
                });
    }

    public List<String> getFutureDates(int day) {
        if (AvailableDates.size() > 0)
            AvailableDates.clear();
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + day; //add 2 if your week start on monday
        System.out.println(delta);
        now.add(Calendar.DAY_OF_WEEK, delta);
        for (int i = 0; i < 4; i++) {
            Date d = now.getTime();
            if (d.after(currentDate)) {
                AvailableDates.add(format.format(now.getTime()));
            }
            now.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        }
        return AvailableDates;
    }

}
