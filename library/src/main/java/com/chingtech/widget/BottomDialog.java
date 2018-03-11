package com.chingtech.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.chingtech.AddressProvider;
import com.chingtech.AddressSelector;
import com.chingtech.DefaultProvider;
import com.chingtech.wheelview.R;

public class BottomDialog extends Dialog {

    private AddressSelector selector;

    public BottomDialog(Context context) {
        super(context, R.style.bottom_dialog);
        init(context, null);
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, null);
    }

    public BottomDialog(Context context, AddressProvider addressProvider) {
        super(context, R.style.bottom_dialog);
        init(context, addressProvider);
    }

    public BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context, null);
    }

    private void init(Context context, AddressProvider addressProvider) {
        selector = new AddressSelector(context,
                                       null == addressProvider ? new DefaultProvider(context) :
                                               addressProvider);
        setContentView(selector.getView());

        Window                     window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = (int) dp2px(256f);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    public void setOnAddressSelectedListener(AddressSelector.OnAddressSelectedListener listener) {
        this.selector.setOnAddressSelectedListener(listener);
    }

    private float dp2px(float dpVal) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, r.getDisplayMetrics());
    }
}
