package com.thuytrinh.quotemaker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thuytrinh.quotemaker.ChangeInfo;
import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.CanvasViewModel;
import com.thuytrinh.quotemaker.viewmodel.TextViewModel;

import rx.functions.Action1;

public class CanvasView extends FrameLayout {
  public CanvasView(Context context) {
    super(context);
  }

  public CanvasView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void bind(final CanvasViewModel viewModel) {
    viewModel.items.onItemsInserted().subscribe(new Action1<ChangeInfo>() {
      @Override
      public void call(ChangeInfo changeInfo) {
        final TextView newItemView = (TextView) LayoutInflater.from(getContext())
            .inflate(R.layout.view_text, CanvasView.this, false);
        addView(newItemView);

        TextViewModel newItem = viewModel.items.get(changeInfo.positionStart);
        newItem.text.observe().subscribe(new Action1<CharSequence>() {
          @Override
          public void call(CharSequence text) {
            newItemView.setText(text);
          }
        });
      }
    });
  }
}