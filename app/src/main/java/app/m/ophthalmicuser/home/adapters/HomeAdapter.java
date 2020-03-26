package app.m.ophthalmicuser.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import app.m.ophthalmicuser.R;
import app.m.ophthalmicuser.databinding.HomeItemBinding;
import app.m.ophthalmicuser.home.itemViewModels.HomeItemViewModel;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    //    public List<CategoriesItem> categoriesItems;
    Context context;

    public HomeAdapter() {
//        categoriesItems = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,
                parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        CategoriesItem dataModel = categoriesItems.get(position);
//        setUpAnimtions(holder);
//        CategoriesItemViewModel homeItemViewModels = new CategoriesItemViewModel(dataModel);
//        homeItemViewModels.getItemsOperationsLiveListener().observe(((LifecycleOwner) context), new Observer<CategoriesItem>() {
//            @Override
//            public void onChanged(CategoriesItem categoriesItem) {
//                UserPreferenceHelper.getInstance(context).addCategoryId(categoriesItem.getId());
//                itemMutableLiveData.setValue(categoriesItem);
//                notifyItemChanged(position);
//            }
//        });
//        if (UserPreferenceHelper.getInstance(context).getCategoryId() != 0) {
//            if (UserPreferenceHelper.getInstance(context).getCategoryId() == dataModel.getId()) {
//                holder.itemBinding.catImage.setBorderColor(context.getResources().getColor(R.color.red));
//                holder.itemBinding.catImage.setBorderWidth(6);
//            }
//        }
//        holder.setViewModel(homeItemViewModels);
    }


    @Override
    public int getItemCount() {
//        return this.categoriesItems.size();
        return 0;
    }

    //
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

//    public void updateData(@Nullable List<CategoriesItem> data) {
//        this.categoriesItems.clear();
//
//        this.categoriesItems.addAll(data);
//        notifyDataSetChanged();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HomeItemBinding itemBinding;

        //
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

        void setViewModel(HomeItemViewModel itemViewModels) {
            if (itemBinding != null) {
                itemBinding.setHomeItemViewModels(itemViewModels);
            }
        }
    }
}
