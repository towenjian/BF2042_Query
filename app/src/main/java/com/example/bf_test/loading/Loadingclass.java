package com.example.bf_test.loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import com.example.bf_test.R;


public class Loadingclass extends Dialog {
    private ImageView imageView;

    public Loadingclass(@NonNull Context context) {
        super(context);
        setContentView(R.layout.loading_layout);
        setCancelable(false);
        imageView = findViewById(R.id.loading);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_anim);
        imageView.startAnimation(animation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        if (window!=null){
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            window.setDimAmount(0.5f);
            WindowManager.LayoutParams params = window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            window.setAttributes(params);
        }
    }

    @Override
    public void show() {
        super.show();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);
        imageView.startAnimation(animation);
        Log.d("dh", "show");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Log.d("dh", "dismiss");
        imageView.clearAnimation();
    }
}


