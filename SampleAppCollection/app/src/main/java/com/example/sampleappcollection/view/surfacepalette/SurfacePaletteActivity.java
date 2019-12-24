package com.example.sampleappcollection.view.surfacepalette;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：SurfaceView画板
 *
 * @author zhangqin
 * @date 2017/6/27
 */
public class SurfacePaletteActivity extends AppCompatActivity {

    @BindView(R.id.surface)
    PaletteSurfaceView mSurface;
    @BindView(R.id.btn_save)
    TextView mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_palette);
        ButterKnife.bind(this);

        mBtnSave.setOnClickListener(view -> {
            // TODO 待实现
//            boolean save = ImageUtils.save(bitmap, Config.SAVE_REAL_PATH + "/" + System.currentTimeMillis() + ".jpg", Bitmap.CompressFormat.JPEG);
//            if (save) {
//                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
//            }
        });
    }
}
