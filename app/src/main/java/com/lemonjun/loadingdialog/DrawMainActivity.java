package com.lemonjun.loadingdialog;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lemonjun.animatorlibrary.SportsView;
import com.lemonjun.drawlib.TapeView;

public class DrawMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_main);

        SportsView sportView = findViewById(R.id.sport_view);

        Keyframe keyframe01 = Keyframe.ofFloat(0,0);

        Keyframe keyframe02 = Keyframe.ofFloat(0.8f,100);

        Keyframe keyframe03 = Keyframe.ofFloat(1f,80);

        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress",keyframe01,keyframe02,keyframe03);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(sportView,holder);
        animator.setDuration(1000);
        animator.start();

        final TextView tvValue = findViewById(R.id.tv_value);
        TapeView tapeView = findViewById(R.id.tape_view);

        tapeView.setOnValueChangeListener(new TapeView.OnValueChangeListener() {
            @Override
            public void onChange(float value) {
                tvValue.setText(value + "");
            }
        });
    }
}
