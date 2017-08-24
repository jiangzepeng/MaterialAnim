package com.jzp.materialanim.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jzp.materialanim.R;

/**
 * Description :生成toolbar的工具类
 * Author : jzp
 * Date   : 17/8/23
 */
public class ToolbarHelper
{
    private final Context mContext;
    private final LayoutInflater mInflater;
    private FrameLayout mContentView;
    private Toolbar mToolbar;

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {R.attr.windowActionBarOverlay, R.attr.actionBarSize};

    public ToolbarHelper(Context context, int layoutId)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /* 初始化整个页面的布局 */
        initContentView();
        /* 初始化实际activity内容 */
        initActivityView(layoutId);
        /* 初始化Toolbar */
        initToolbar();
    }

    private void initActivityView(int layoutId)
    {
        View view = mInflater.inflate(layoutId, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /* 获取主题中定义的悬浮标志 */
        boolean overly = typedArray.getBoolean(0, false);
        /* 获取主题中定义的toolbar的高度 */
        int toolBarSize = getToolbarHeight();
        typedArray.recycle();
        /* 如果是悬浮状态，则不需要设置间距 */
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(view, params);
    }

    public int getToolbarHeight()
    {
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);

        return (int) typedArray.getDimension(android.support.v7.appcompat.R.styleable.Toolbar_android_minHeight, (int) mContext
                .getResources()
                .getDimension(R.dimen.abc_action_bar_default_height_material));
    }

    private void initToolbar()
    {
        View view = mInflater.inflate(R.layout.item_toolbar, mContentView);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }

    private void initContentView()
    {
        mContentView = new FrameLayout(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    public FrameLayout getContentView()
    {
        return mContentView;
    }

    public Toolbar getToolbar()
    {
        return mToolbar;
    }
}
