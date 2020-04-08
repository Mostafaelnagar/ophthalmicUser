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
import app.m.ophthalmicuser.databinding.DepartmentDetailsItemBinding;
import app.m.ophthalmicuser.databinding.DepartmentItemBinding;
import app.m.ophthalmicuser.settings.itemViewModels.DepartmentDetailsItemViewModel;
import app.m.ophthalmicuser.settings.itemViewModels.DepartmentItemViewModel;
import app.m.ophthalmicuser.settings.models.DepartmentDetailsModel;
import app.m.ophthalmicuser.settings.models.Departmentmodel;


public class DepartmentDetailsAdapter extends RecyclerView.Adapter<DepartmentDetailsAdapter.ViewHolder> {
    public List<DepartmentDetailsModel> departmentDetailsModels;
    Context context;

    public DepartmentDetailsAdapter() {
        departmentDetailsModels = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_details_item,
                parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DepartmentDetailsModel dataModel = departmentDetailsModels.get(position);
        DepartmentDetailsItemViewModel homeItemViewModels = new DepartmentDetailsItemViewModel(dataModel);
        holder.setViewModel(homeItemViewModels);
    }


    @Override
    public int getItemCount() {
        return this.departmentDetailsModels.size();
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

    public void updateData(@Nullable List<DepartmentDetailsModel> data) {
         this.departmentDetailsModels.clear();
        this.departmentDetailsModels.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DepartmentDetailsItemBinding itemBinding;

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

        void setViewModel(DepartmentDetailsItemViewModel itemViewModels) {
            if (itemBinding != null) {
                itemBinding.setDetailsItemViewModels(itemViewModels);
            }
        }
    }
}
