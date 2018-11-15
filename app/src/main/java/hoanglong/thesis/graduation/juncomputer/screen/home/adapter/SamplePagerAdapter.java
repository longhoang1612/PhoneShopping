package hoanglong.thesis.graduation.juncomputer.screen.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class SamplePagerAdapter extends PagerAdapter {

    private List<String> sliders;
    private ClickSliderListener mClickSliderListener;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    public SamplePagerAdapter(List<String> sliders, ClickSliderListener clickSliderListener) {
        this.sliders = sliders;
        this.mClickSliderListener = clickSliderListener;
    }

    @Override
    public int getCount() {
        return sliders != null ? sliders.size() : 0;
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
    public Object instantiateItem(ViewGroup view, final int position) {
        PhotoView imageView = new PhotoView(view.getContext());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickSliderListener.onClickSlider(sliders, position);
            }
        });
        Glide.with(view.getContext()).load(sliders.get(position)).into(imageView);
        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    public interface ClickSliderListener {
        void onClickSlider(List<String> sliders, int position);
    }

}