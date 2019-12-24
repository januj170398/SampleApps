package com.example.sampleappcollection.spannablestring;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：富文本字符串
 *
 * @author zhangqin
 */
public class SpannableActivity extends AppCompatActivity {

    @BindView(R.id.span_string)
    TextView spanString;
    @BindView(R.id.span_builder)
    TextView spanBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);
        ButterKnife.bind(this);

        SpannableString spannableString = new SpannableString("前景色背景色相对大小删除线下划线" +
                "上标小上标下标粗体斜体显示图片点击超链接");

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(2f);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        UnderlineSpan underlineSpan = new UnderlineSpan();
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        RelativeSizeSpan relativeSizeSpan2 = new RelativeSizeSpan(0.5f);
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);

        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.ic_launcher);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // 文字不变色
                ds.setUnderlineText(false);
            }
        };
        URLSpan urlSpan = new URLSpan("http://www.sdwfqin.com");

        spannableString.setSpan(foregroundColorSpan, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(backgroundColorSpan, 3, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan, 6, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(strikethroughSpan, 10, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, 13, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(superscriptSpan, 16, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan2, 18, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(subscriptSpan, 21, 23, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B, 23, 25, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_I, 25, 27, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan, 29, 31, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 31, 33, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlSpan, 33, 36, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        spanString.setMovementMethod(LinkMovementMethod.getInstance());
        // 点击背景色
        // spanString.setHighlightColor(Color.parseColor("#36969696"));
        // 可以点击
        spanString.setText(spannableString);

        SpannableStringBuilder builder = new SpannableStringBuilder("哈哈哈");
        builder.setSpan(foregroundColorSpan, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append("lalala");
        // 注意：如果使用toString()方法设置的样式就没有了
        spanBuilder.setText(builder);
    }
}
