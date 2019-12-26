package com.example.sampleappcollection.view.viewposition;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: Position parameter of View
 *
 * @author zhangqin
 * @date 2017/4/27
 */
public class ViewPositionActivity extends AppCompatActivity {

    @BindView(R.id.position_view)
    PositionView positionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_position);
        ButterKnife.bind(this);
        positionView.setOnClickListener(v -> positionView.setTranslation());
    }
}
