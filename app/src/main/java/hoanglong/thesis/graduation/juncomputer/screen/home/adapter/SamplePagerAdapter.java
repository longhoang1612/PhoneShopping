package hoanglong.thesis.graduation.juncomputer.screen.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

public class SamplePagerAdapter extends PagerAdapter {

    private final Random random = new Random();
    private int mSize;
    private List<String> sliders;

    public SamplePagerAdapter() {
        mSize = 5;
    }

    public SamplePagerAdapter(int size, List<String> sliders) {
        mSize = size;
        this.sliders = sliders;
    }

    public SamplePagerAdapter(int count) {
        mSize = count;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        ImageView imageView = new ImageView(view.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(view.getContext()).load(sliders.get(position)).into(imageView);
        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;

        notifyDataSetChanged();
    }
}