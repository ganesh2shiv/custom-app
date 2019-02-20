package com.custom.app.ui.notification;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.core.app.ui.base.BaseView;
import com.core.app.ui.epoxy.BaseHolder;
import com.custom.app.R;

import androidx.annotation.NonNull;
import butterknife.BindView;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.item_notification_list)
public abstract class NotificationItemModel extends EpoxyModelWithHolder<NotificationItemModel.Holder> {

    @EpoxyAttribute BaseView view;
    @EpoxyAttribute String title;
    @EpoxyAttribute String desc;
    @EpoxyAttribute String timestamp;
    @EpoxyAttribute(DoNotHash) View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        if (!TextUtils.isEmpty(title)) {
            holder.title.setText(title);
        }

        holder.desc.setText(desc);

        if (!TextUtils.isEmpty(timestamp)) {
            holder.timestamp.setText(timestamp);
        }

        holder.getItemView().setOnClickListener(clickListener);
    }

    static class Holder extends BaseHolder {

        @BindView(R.id.tv_title) TextView title;
        @BindView(R.id.tv_desc) TextView desc;
        @BindView(R.id.tv_timestamp) TextView timestamp;

    }
}