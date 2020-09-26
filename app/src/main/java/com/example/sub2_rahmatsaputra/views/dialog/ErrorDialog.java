package com.example.sub2_rahmatsaputra.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sub2_rahmatsaputra.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErrorDialog extends Dialog {

    @BindView(R.id.textViewTittleError)
    TextView textViewTittleError;
    @BindView(R.id.textViewDescriptionError)
    TextView textViewDescriptionError;
    @BindView(R.id.imageViewError)
    ImageView imageViewError;

    private ErrorDialog.OnProceedListener listener;
    private String title;
    private String description;
    private Boolean isSuccess;
    private Context mContext;

    public ErrorDialog(@NonNull Context context, String title, String description, Boolean isSuccess) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.description = description;
        this.isSuccess = isSuccess;
    }

    /**
     * Proceeding Action
     */
    @OnClick(R.id.button_back_error)
    void proceed() {
        dismiss();
        if (listener != null) {
            listener.onProceed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);
        setCanceledOnTouchOutside(false);
        setCancelable(true);

        ButterKnife.bind(this);

        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onStart() {
        super.onStart();

        textViewTittleError.setText(title);
        textViewDescriptionError.setText(description);
        imageViewError.setImageResource(R.drawable.ic_cancel_red_24dp);
        textViewTittleError.setTextColor(getContext().getResources().getColor(R.color.colorRed));

    }

    /**
     * Set Listener when you click 'Yes' Button
     *
     * @param listener Interface when you click 'Yes' Button
     */
    public void setOnProceedListener(ErrorDialog.OnProceedListener listener) {
        this.listener = listener;
    }

    public interface OnProceedListener {
        void onProceed();
    }

    /**
     * On Click 'Yes' Button
     */

}