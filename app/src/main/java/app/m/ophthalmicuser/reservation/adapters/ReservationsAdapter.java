package app.m.ophthalmicuser.reservation.adapters;

import android.content.Context;
import android.util.Log;
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
import app.m.ophthalmicuser.databinding.ReserveItemBinding;
import app.m.ophthalmicuser.reservation.itemViewModels.ReservationsItemViewModel;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;


public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder> {
    public List<ReservationsResponse> reservationsResponseList;
    Context context;

    public ReservationsAdapter() {
        reservationsResponseList = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserve_item,
                parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReservationsResponse dataModel = reservationsResponseList.get(position);
        ReservationsItemViewModel homeItemViewModels = new ReservationsItemViewModel(dataModel);
        holder.setViewModel(homeItemViewModels);
    }


    @Override
    public int getItemCount() {
        return this.reservationsResponseList.size();
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

    public void updateData(@Nullable List<ReservationsResponse> data) {
        this.reservationsResponseList.clear();
        this.reservationsResponseList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ReserveItemBinding itemBinding;

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

        void setViewModel(ReservationsItemViewModel itemViewModels) {
            if (itemBinding != null) {
                itemBinding.setReserveItemViewModels(itemViewModels);
            }
        }
    }
}
