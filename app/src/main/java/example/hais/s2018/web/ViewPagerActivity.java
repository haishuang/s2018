package example.hais.s2018.web;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import example.hais.s2018.R;
import example.hais.s2018.base.BaseActivity;
import example.hais.s2018.utils.LogUtils;

public class ViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_pager);

        String[] imgs = getIntent().getStringArrayExtra("image");

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(new SamplePagerAdapter(imgs));
    }

    class SamplePagerAdapter extends PagerAdapter {
        String[] imgs;

        public SamplePagerAdapter(String[] imgs) {
            this.imgs = imgs;
        }


        @Override
        public int getCount() {
            // LogUtils.e("大小"+imgs.length);
            return imgs.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setBackgroundColor(Color.parseColor("#343443"));
            // photoView.setImageResource(sDrawables[position]);
            LogUtils.e(imgs[position]);
            Glide.with(activity).load(imgs[position]).error(R.mipmap.ic_launcher).into(photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
