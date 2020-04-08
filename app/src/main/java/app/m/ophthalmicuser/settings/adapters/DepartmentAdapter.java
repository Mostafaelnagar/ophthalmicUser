package app.m.ophthalmicuser.settings.adapters;

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
import app.m.ophthalmicuser.base.MovementManager;
import app.m.ophthalmicuser.base.constantsutils.Codes;
import app.m.ophthalmicuser.databinding.DepartmentItemBinding;
import app.m.ophthalmicuser.databinding.ReserveItemBinding;
import app.m.ophthalmicuser.reservation.itemViewModels.ReservationsItemViewModel;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;
import app.m.ophthalmicuser.settings.itemViewModels.DepartmentItemViewModel;
import app.m.ophthalmicuser.settings.models.Departmentmodel;


public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {
    public List<Departmentmodel> departmentmodelList;
    Context context;

    public DepartmentAdapter() {
        departmentmodelList = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_item,
                parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Departmentmodel dataModel = departmentmodelList.get(position);
        DepartmentItemViewModel homeItemViewModels = new DepartmentItemViewModel(dataModel);
        homeItemViewModels.getClicksMutableLiveData().observe(((LifecycleOwner) context), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                MovementManager.startActivityWithObject(context, Codes.DEPARTMENT_DETAILS, new PassingObject(dataModel.getId()));
            }
        });
        holder.setViewModel(homeItemViewModels);
    }


    @Override
    public int getItemCount() {
        return this.departmentmodelList.size();
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

    public void updateData(@Nullable List<Departmentmodel> data) {
        this.departmentmodelList.clear();
        this.departmentmodelList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DepartmentItemBinding itemBinding;

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

        void setViewModel(DepartmentItemViewModel itemViewModels) {
            if (itemBinding != null) {
                itemBinding.setDepartmentItemViewModels(itemViewModels);
            }
        }
    }
}
