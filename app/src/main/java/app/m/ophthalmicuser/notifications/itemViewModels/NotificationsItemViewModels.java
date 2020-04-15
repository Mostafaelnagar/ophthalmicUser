package app.m.ophthalmicuser.notifications.itemViewModels;

import androidx.databinding.Bindable;

import app.m.ophthalmicuser.base.BaseViewModel;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.notifications.models.NotificationsData;


public class NotificationsItemViewModels extends BaseViewModel {
    private NotificationsData notificationsData;

    public NotificationsItemViewModels(NotificationsData notificationsData) {
        this.notificationsData = notificationsData;
    }

    @Bindable
    public NotificationsData getNotificationsData() {
        return notificationsData;
    }


    public void itemAction() {
        getClicksMutableLiveData().setValue(Codes.MY_ORDERS);
    }


}
