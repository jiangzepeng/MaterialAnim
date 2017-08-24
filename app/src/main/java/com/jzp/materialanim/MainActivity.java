package com.jzp.materialanim;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzp.materialanim.common.ImageViewTouchListener;
import com.jzp.materialanim.common.ToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ToolbarActivity {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.move_image)
    ImageView moveImage;
    @BindView(R.id.rootView)
    FrameLayout rootView;
    private String[] title = new String[]{"Reveal Animation", "Share Element", "CollapsingToolbarLayout"};
    public final static String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        moveImage.setOnTouchListener(new ImageViewTouchListener(moveImage, rootView));

        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false));
            }

            @Override
            public void onBindViewHolder(final MyViewHolder holder, final int position) {
                holder.item_title.setText(title[position]);
                holder.item_parent.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, RevealAnimationActivity.class);
                                intent.putExtra(TITLE, title[position]);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent shareIntent = new Intent(MainActivity.this, ShareElementActivity.class);
                                String transitionName = getString(R.string.share_tag);
                                ActivityOptionsCompat transitionActivityOptions
                                        = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, holder.item_image, transitionName);
                                startActivity(shareIntent, transitionActivityOptions.toBundle());
                                break;
                            case 2:
                                Intent collapsingIntent = new Intent(MainActivity.this, CollapsingToolbarActivity.class);
                                startActivity(collapsingIntent);
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return title == null ? 0 : title.length;
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.move_image, R.id.rootView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.move_image:
                Intent shareIntent = new Intent(MainActivity.this, ShareElementActivity.class);
                String transitionName = getString(R.string.share_tag);
                ActivityOptionsCompat transitionActivityOptions
                        = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, moveImage, transitionName);
                startActivity(shareIntent, transitionActivityOptions.toBundle());
                break;
            case R.id.rootView:
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        ImageView item_image;
        LinearLayout item_parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            item_parent = (LinearLayout) itemView.findViewById(R.id.item_parent);
        }
    }
}
