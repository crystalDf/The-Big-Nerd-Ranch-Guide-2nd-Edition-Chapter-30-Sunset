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
    private View mShadowView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private int mHeatSunColor;
    private int mColdSunColor;

    private boolean mSunset = true;

    public static final int DURATION = 3000;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mShadowView = view.findViewById(R.id.shadow);
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

        float shadowYStart = -mShadowView.getHeight();
        float shadowYEnd = mShadowView.getTop();

        ObjectAnimator sunHeightAnimator = ObjectAnimator.ofFloat(mSunView, "y",
                sunYStart, sunYEnd)
                .setDuration(DURATION);

        sunHeightAnimator.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator shadowHeightAnimator = ObjectAnimator.ofFloat(mShadowView, "y",
                shadowYStart, shadowYEnd)
                .setDuration(DURATION);

        shadowHeightAnimator.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator sunriseSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mSunsetSkyColor, mBlueSkyColor)
                .setDuration(DURATION);

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mNightSkyColor, mSunsetSkyColor)
                .setDuration(DURATION);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(sunHeightAnimator)
                .with(shadowHeightAnimator)
                .with(sunriseSkyAnimator)
                .after(nightSkyAnimator);

        animatorSet.start();
    }

    private void startSunsetAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        float shadowYStart = mShadowView.getTop();
        float shadowYEnd = -mShadowView.getHeight();

        ObjectAnimator sunHeightAnimator = ObjectAnimator.ofFloat(mSunView, "y",
                sunYStart, sunYEnd)
                .setDuration(DURATION);

        sunHeightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator shadowHeightAnimator = ObjectAnimator.ofFloat(mShadowView, "y",
                shadowYStart, shadowYEnd)
                .setDuration(DURATION);

        shadowHeightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mBlueSkyColor, mSunsetSkyColor)
                .setDuration(DURATION);

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofObject(mSkyView, "backgroundColor",
                new ArgbEvaluator(), mSunsetSkyColor, mNightSkyColor)
                .setDuration(DURATION);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(sunHeightAnimator)
                .with(shadowHeightAnimator)
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
