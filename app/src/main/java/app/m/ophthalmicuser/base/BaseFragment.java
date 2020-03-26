package app.m.ophthalmicuser.base;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import app.m.ophthalmicuser.BaseActivity;
import app.m.ophthalmicuser.MainActivity;


public class BaseFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {
    Context context;
    public MutableLiveData<Boolean> ConnectionLiveData;

    public void accessLoadingBar(int visiablity) {
        try {
            if (visiablity == View.VISIBLE) {
                ((BaseActivity) context).activityBaseBinding.pbBaseLoadingBar.setVisibility(visiablity);

            } else {
                ((BaseActivity) context).activityBaseBinding.pbBaseLoadingBar.setVisibility(visiablity);
            }

        } catch (ClassCastException e) {
            e.getStackTrace();
        }
        try {
            if (visiablity == View.VISIBLE) {
                ((MainActivity) context).activityMainBinding.pbBaseLoadingBar.setVisibility(visiablity);
            } else {
                ((MainActivity) context).activityMainBinding.pbBaseLoadingBar.setVisibility(visiablity);
            }
        } catch (ClassCastException e) {
            e.getStackTrace();
        }
    }

    public void showMessage(String message, int type, int mesgStatus) {
        //mesgStatus -> 0 success
        //mesgStatus -> 1 error

        if (type == 0) {
            ((BaseActivity) context).showMessage(message, mesgStatus);
        } else {
            ((MainActivity) context).showMessage(message, mesgStatus);
        }
    }

    public void setTitle(String title) {
        ((MainActivity) context).setTitle(title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.context = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        ConnectionLiveData = new MutableLiveData<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);

    }

    // Method to manually check connection status
    public void checkConnection() {
        ConnectionLiveData.setValue(ConnectivityReceiver.isConnected());
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        ConnectionLiveData.setValue(isConnected);
    }


}
