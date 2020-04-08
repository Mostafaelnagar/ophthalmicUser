package app.m.ophthalmicuser.home.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import app.m.ophthalmicuser.R;

public class SliderProductAdapter extends SliderViewAdapter<SliderProductAdapter.SliderAdapterVH> {
    private List<Drawable> pagerList;
    private Context context;

    public SliderProductAdapter(Context context, List<Drawable> pagerList) {
        this.pagerList = pagerList;
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_slider_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        Glide.with(context).load(pagerList.get(position)).into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
