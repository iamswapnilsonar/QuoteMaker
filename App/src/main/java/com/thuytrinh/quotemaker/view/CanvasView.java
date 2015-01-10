package com.thuytrinh.quotemaker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.Quote;
import com.thuytrinh.quotemaker.viewmodel.TextItem;
import com.thuytrinh.quotemaker.viewmodel.rx.ChangeInfo;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import rx.Subscription;
import rx.functions.Action1;

public class CanvasView extends FrameLayout {
  public final ObservableProperty<Quote> viewModel = new ObservableProperty<>();
  private Subscription viewModelSubscription;

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

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    viewModelSubscription = viewModel.observe().subscribe(new Action1<Quote>() {
      @Override
      public void call(Quote viewModel) {
        bind(viewModel);
      }
    });
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (viewModelSubscription != null) {
      viewModelSubscription.unsubscribe();
    }
  }

  private void bind(final Quote viewModel) {
    viewModel.items().onItemsInserted()
        .subscribe(new Action1<ChangeInfo>() {
          @Override
          public void call(ChangeInfo changeInfo) {
            final TextItemView newItemView = (TextItemView) LayoutInflater.from(getContext())
                .inflate(R.layout.view_text_item, CanvasView.this, false);
            addView(newItemView);

            TextItem newItem = viewModel.items().get(changeInfo.positionStart);
            newItem.delete().observe()
                .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object unused) {
                    removeView(newItemView);
                  }
                });
            newItemView.viewModel.setValue(newItem);
          }
        });
  }
}