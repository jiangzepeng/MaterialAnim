package com.jzp.materialanim.common;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


/**
 * Description :
 * Author : jzp
 * Date   : 17/8/23
 */
public abstract class ToolbarActivity extends AppCompatActivity {
    private ToolbarHelper mToolbarHelper;
    public int toolbarHeight = 0;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
        mToolbarHelper = new ToolbarHelper(this, layoutResID);
        Toolbar toolbar = mToolbarHelper.getToolbar();

        toolbarHeight = mToolbarHelper.getToolbarHeight();
        setContentView(mToolbarHelper.getContentView());
        /* 设置支持ActionBar */
        setSupportActionBar(toolbar);
        /* 自定义的一些操作 */
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
