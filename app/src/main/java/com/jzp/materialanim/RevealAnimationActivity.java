package com.jzp.materialanim;

import android.animation.Animator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzp.materialanim.common.ToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author : jzp
 * ate   : 17/8/23
 */

public class RevealAnimationActivity extends ToolbarActivity implements View.OnTouchListener {
    @BindView(R.id.reveal_head)
    TextView revealHead;
    @BindView(R.id.reveal_middle)
    TextView revealMiddle;
    @BindView(R.id.reveal_bottom)
    TextView revealBottom;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_anim);
        ButterKnife.bind(this);
        revealHead.setOnTouchListener(this);
        revealMiddle.setOnTouchListener(this);
        revealBottom.setOnTouchListener(this);
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        title = getIntent().getStringExtra(MainActivity.TITLE);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.reveal_head:
                    animateRevealColorFromCoordinates(rootView, R.color.sample_red, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    break;
                case R.id.reveal_middle:
                    animateRevealColorFromCoordinates(rootView, R.color.sample_blue, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    break;
                case R.id.reveal_bottom:
                    animateRevealColorFromCoordinates(rootView, R.color.sample_green, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    break;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int pointx, int pointY) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
        //由于toolbar是自己绘制的所以要减去这个高度
        pointY = pointY - toolbarHeight;
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, pointx, pointY, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }
}
