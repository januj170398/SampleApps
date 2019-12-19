package com.example.sampleappcollection.view.delbtn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.sdwfqin.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteButtonActivity extends AppCompatActivity {

    @BindView(R.id.btn_del)
    DeleteButtonView mBtnDel;
    @BindView(R.id.edit)
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_button);
        ButterKnife.bind(this);
        mBtnDel.setEditText(mEdit);
    }
}
