package com.star.sunset;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private int mHeatSunColor;
    private int mColdSunColor;

    private boolean mSunset = true;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSunset) {
                    startSunsetAnimation();
                } else {
                    startSunriseAnimation();
                }
                mSunset = !mSunset;
                startSunPulsateAnimation();
            }
        });

        mBlueSkyColor = getResources().getColor(R.color.blue_sky);
        mSunsetSkyColor = getResources().getColor(R.color.sun_set_sky);
        mNightSkyColor = getResources().getColor(R.color.night_sky);

        mHeatSunColor = getResources().getColor(R.color.heat_sun);
        mColdSunColor = getResources().getColor(R.color.cold_sun);

        return view;
    }

    private void startSunriseAnimation() {
        float sunYStart = mSkyView.getHeight();
        float sunYEnd = mSunView.getTop();

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y",
                sunYStart, sunYEnd)
                .setDuration(3000);

        heightAnimator.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator sunriseSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mNightSkyColor, mSunsetSkyColor)
                .setDuration(3000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunriseSkyAnimator)
                .after(nightSkyAnimator);

        animatorSet.start();
    }

    private void startSunsetAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y",
                sunYStart, sunYEnd)
                .setDuration(3000);

        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mSunsetSkyColor, mNightSkyColor)
                .setDuration(3000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);

        animatorSet.start();

    }

    private void startSunPulsateAnimation() {
        ObjectAnimator sunPulsateAnimator = ObjectAnimator.ofObject(mSunView, "backgroundColor",
                new ArgbEvaluator(), mColdSunColor, mHeatSunColor)
                .setDuration(1000);

        sunPulsateAnimator.setRepeatMode(ValueAnimator.REVERSE);
        sunPulsateAnimator.setRepeatCount(ValueAnimator.INFINITE);

        sunPulsateAnimator.start();
    }

}
