package com.app.cloudpet.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.cloudpet.R;

public class DialogUtil {
    public static void alertEditTextDialog(Context context,String title, PositiveBtnClickListener positiveBtnClickListener) {

        View outerView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_with_edittext, null);
        TextView dialogMessage = (TextView) outerView.findViewById(R.id.et_alert_dialog_message);
        new AlertDialog.Builder(context)
                .setView(outerView)
                .setTitle(title)
                .setPositiveButton("确定", (v, var) -> {
                    String inputText = dialogMessage.getText().toString().trim();
                    if (TextUtils.isEmpty(inputText)) {
                        return;
                    }
                    if (positiveBtnClickListener != null)
                        positiveBtnClickListener.onPositiveBtnClick(inputText);
                })
                .show();
    }


    public interface PositiveBtnClickListener {
        public void onPositiveBtnClick(String inputText);
    }
}

