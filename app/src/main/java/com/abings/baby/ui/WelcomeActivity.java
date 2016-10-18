package com.abings.baby.ui;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.data.model.Profile;
import com.abings.baby.ui.base.BaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeActivity extends BaseActivity {

    private static final String EXTRA_PROFILE = "io.ribot.app.ui.WelcomeActivity.EXTRA_PROFILE";
    private static final String EXTRA_TIME_DISPLAYING = "io.ribot.app.ui.WelcomeActivity.EXTRA_TIME_DISPLAYING";

    @Bind(R.id.layout_profile_info)
    View mProfileInfoLayout;
    @Bind(R.id.image_profile)
    CircleImageView mProfileImage;
    @Bind(R.id.text_greeting)
    TextView mGreetingText;

    private Handler mHandler;
    private long mTimeDisplaying;

    public static Intent newStartIntent(Context context, Profile profile) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.putExtra(EXTRA_PROFILE, profile);
        return intent;
    }

    // timeDisplaying is milliseconds Activity remains opened until it navigates to main screen.
    public static Intent newStartIntent(Context context, Profile profile, long timeDisplaying) {
        Intent intent = newStartIntent(context, profile);
        intent.putExtra(EXTRA_TIME_DISPLAYING, timeDisplaying);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View
                    .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(R.color.black_20p));
        }
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        // Set profile values in views
        Intent intent = getIntent();
        Profile profile = intent.getParcelableExtra(EXTRA_PROFILE);
        mTimeDisplaying = intent.getLongExtra(EXTRA_TIME_DISPLAYING, -1);
        mGreetingText.setText(getString(R.string.welcome_greetings, profile.name.first));
        loadProfileImage(profile.avatar);

        String hexColor = profile.hexColor;
        if (hexColor != null) mProfileInfoLayout.setBackgroundColor(Color.parseColor(hexColor));

        mHandler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // We need to start the reveal animation from the listener so we make sure the view
            // is attached.
            // http://stackoverflow.com/questions/26819429/cannot-start-this-animator-on-a-detached-view-reveal-effect
            mProfileInfoLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    revealProfileInfo();
                }
            });
        } else {
            mProfileInfoLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void iniInjector() {

    }


    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mTimeDisplaying > 0) {
            mHandler.postDelayed(mNavigateToMainActivity, mTimeDisplaying);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mNavigateToMainActivity);
    }

    private void loadProfileImage(String avatar) {
        Picasso.with(this).load(avatar).placeholder(R.drawable.profile_placeholder_large).error(R.drawable
                .profile_placeholder_large).into(mProfileImage, new Callback() {
            @Override
            public void onSuccess() {
                mProfileImage.setBorderColor(Color.WHITE);
            }

            @Override
            public void onError() {
                //Timber.e("There was an error loading the profile image");
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealProfileInfo() {
        int cx = mProfileInfoLayout.getMeasuredWidth() / 2;
        int cy = mProfileInfoLayout.getMeasuredHeight() / 2;
        int finalRadius = Math.max(mProfileInfoLayout.getWidth(), mProfileInfoLayout.getHeight()) / 2;
        Animator anim = ViewAnimationUtils.createCircularReveal(mProfileInfoLayout, cx, cy, 0, finalRadius);
        mProfileInfoLayout.setVisibility(View.VISIBLE);
        anim.start();
    }

    private Runnable mNavigateToMainActivity = new Runnable() {
        @Override
        public void run() {
            //  Intent intent = MainActivity.getStartIntent(WelcomeActivity.this, false);
            //  startActivity(intent);
            //  finish();
        }
    };
}
