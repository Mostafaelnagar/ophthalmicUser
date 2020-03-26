package app.m.ophthalmicuser.booking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.m.ophthalmicuser.PassingObject;
import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.booking.itemViewModels.DoctorItemViewModel;
import app.m.ophthalmicuser.databinding.DoctorItemBinding;


public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    public List<UserData> userDataList;
    Context context;

    public DoctorsAdapter() {
        userDataList = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,
                parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData dataModel = userDataList.get(position);
        DoctorItemViewModel homeItemViewModels = new DoctorItemViewModel(dataModel);
        homeItemViewModels.getClicksMutableLiveData().observe(((LifecycleOwner) context), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == Codes.MAKE_RESERVATION) {
                    MovementManager.startActivityWithObject(context, Codes.MAKE_RESERVATION, new PassingObject(dataModel));
                } else if (integer == Codes.DOCTOR_PROFILE) {
                    MovementManager.startActivityWithObject(context, Codes.DOCTOR_PROFILE, new PassingObject(dataModel));
                }
            }
        });
        holder.setViewModel(homeItemViewModels);
    }


    @Override
    public int getItemCount() {
        return this.userDataList.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<UserData> data) {
        this.userDataList.clear();
        this.userDataList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DoctorItemBinding itemBinding;

        ViewHolder(View itemView) {
            super(itemView);
            bind();
        }


        void bind() {
            if (itemBinding == null) {
                itemBinding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (itemBinding != null) {
                itemBinding.unbind();
            }
        }

        void setViewModel(DoctorItemViewModel itemViewModels) {
            if (itemBinding != null) {
                itemBinding.setDoctorItemViewModels(itemViewModels);
            }
        }
    }
}
