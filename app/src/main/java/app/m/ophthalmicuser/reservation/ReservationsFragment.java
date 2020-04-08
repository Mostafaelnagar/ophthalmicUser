package app.m.ophthalmicuser.reservation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.base.BaseFragment;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.viewModels.DoctorsViewModels;
import app.m.ophthalmicuser.databinding.FragmentDoctorsBinding;
import app.m.ophthalmicuser.databinding.FragmentReservationsBinding;
import app.m.ophthalmicuser.reservation.viewModels.ReservationsViewModels;


public class ReservationsFragment extends BaseFragment {
    FragmentReservationsBinding reservationsBinding;
    ReservationsViewModels reservationsViewModels;


    public ReservationsFragment() {
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
        reservationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservations, container, false);
        reservationsViewModels = new ReservationsViewModels();
        reservationsBinding.setReserveViewModel(reservationsViewModels);
        liveDataListeners();
        checkConnection();
        return reservationsBinding.getRoot();
    }


    private void liveDataListeners() {
        reservationsViewModels.getClicksMutableLiveData().observe(this, result -> {
            if (result == View.VISIBLE || result == View.GONE) {
                accessLoadingBar(result);
            } else if (result == Codes.SHOW_MESSAGE_ERROR) {
                showMessage(reservationsViewModels.getReturnedMessage(), 0, 1);
            }
        });

        ConnectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    reservationsViewModels.getReservations();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.connection_invaild_msg), 0, 1);
                }
            }
        });


    }


}
