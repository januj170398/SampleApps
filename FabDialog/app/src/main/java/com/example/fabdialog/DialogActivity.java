package com.example.fabdialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;

public class DialogActivity extends AppCompatActivity {
    private ViewGroup container;
    private TextView design_your_outfit, our_design;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        container = (ViewGroup) findViewById(R.id.container);
        design_your_outfit = (TextView) findViewById(R.id.design_your_outfit);
        our_design = (TextView) findViewById(R.id.our_design);

        //方式一
        setupSharedEelementTransitions1();

        our_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "Message");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                dismiss();

            }
        });

        design_your_outfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DialogActivity.this, "Design your outfit", Toast.LENGTH_SHORT).show();

            }
        });

        //方式二
        //setupSharedEelementTransitions2();

        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        container.setOnClickListener(dismissListener);
        container.findViewById(R.id.close).setOnClickListener(dismissListener);
    }

    /**
     * 使用方式一：调用setupSharedEelementTransitions1方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphFabToDialog, MorphDialogToFab
     */
    public void setupSharedEelementTransitions1() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphFabToDialog sharedEnter = new MorphFabToDialog();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToFab sharedReturn = new MorphDialogToFab();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }



    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

    /**
     * 使用方式二：调用setupSharedEelementTransitions2方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphTransition
     */
    public void setupSharedEelementTransitions2() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        //hujiawei 100是随意给的一个数字，可以修改，需要注意的是这里调用container.getHeight()结果为0
        MorphTransition sharedEnter = new MorphTransition(ContextCompat.getColor(this, R.color.fab_background_color),
                ContextCompat.getColor(this, R.color.dialog_background_color), 100, getResources().getDimensionPixelSize(R.dimen.dialog_corners), true);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphTransition sharedReturn = new MorphTransition(ContextCompat.getColor(this, R.color.dialog_background_color),
                ContextCompat.getColor(this, R.color.fab_background_color), getResources().getDimensionPixelSize(R.dimen.dialog_corners), 100,  false);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }
}
