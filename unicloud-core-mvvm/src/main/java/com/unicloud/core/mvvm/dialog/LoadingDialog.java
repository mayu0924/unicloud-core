package com.unicloud.core.mvvm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.unicloud.core.mvvm.R;

/**
 * Created by mayu on 2017/6/13,下午4:04.
 */
public class LoadingDialog extends BaseDialog {
    private String message = "加载中...";

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base_loading);
    }

    public LoadingDialog setMessage(String msg) {
        message = msg;
        return this;
    }

    @Override
    public void show() {
        super.show();
        TextView progress_message = findViewById(R.id.message);
        ImageView imageView = findViewById(R.id.iv);
        progress_message.setText(message);
        Glide.with(getContext()).asGif().apply(new RequestOptions().centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(R.drawable.loading)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        resource.setLoopCount(1000);
                        return false;
                    }
                }).into(imageView);
    }
}