package com.custom.app.ui.about;

import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.core.app.ui.base.BaseFragment;
import com.core.app.ui.custom.LinkifyMovement;
import com.core.app.ui.custom.SpannyText;
import com.core.app.util.Util;
import com.custom.app.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AboutFragment extends BaseFragment implements LinkifyMovement.OnLinkClickListener {

    private Unbinder unbinder;

    @BindView(R.id.tv_mobile)
    TextView tvMobile;

    @BindView(R.id.tv_work)
    TextView tvWork;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        LinkifyMovement.linkify(Linkify.ALL, ((ViewGroup) rootView)).setOnLinkClickListener(this);

        SpannyText mobile = new SpannyText(tvMobile.getText())
                .append("\n")
                .append("\n", new RelativeSizeSpan(0.65f))
                .append("Mobile",
                        new RelativeSizeSpan(0.85f),
                        new ForegroundColorSpan(ContextCompat.getColor(context(), R.color.medium_grey)));

        tvMobile.setText(mobile);

        SpannyText work = new SpannyText(tvWork.getText())
                .append("\n")
                .append("\n", new RelativeSizeSpan(0.65f))
                .append("Work",
                        new RelativeSizeSpan(0.85f),
                        new ForegroundColorSpan(ContextCompat.getColor(context(), R.color.medium_grey)));

        tvWork.setText(work);

        SpannyText email = new SpannyText(tvEmail.getText())
                .append("\n")
                .append("\n", new RelativeSizeSpan(0.65f))
                .append("Email",
                        new RelativeSizeSpan(0.85f),
                        new ForegroundColorSpan(ContextCompat.getColor(context(), R.color.medium_grey)));

        tvEmail.setText(email);

        return rootView;
    }

    @Override
    public boolean onClick(TextView textView, String url) {
        return Util.handleUrl(textView.getContext(), url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}