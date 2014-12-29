package com.thuytrinh.quotemaker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.thuytrinh.quotemaker.viewmodel.ObservableProperty;
import com.thuytrinh.quotemaker.viewmodel.TextViewModel;

import rx.Subscription;
import rx.functions.Action1;

public class CoolTextView extends TextView {
  public final ObservableProperty<TextViewModel> viewModel = new ObservableProperty<>();
  private Subscription viewModelSubscription;

  public CoolTextView(Context context) {
    super(context);
  }

  public CoolTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CoolTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CoolTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    viewModelSubscription = viewModel.observe()
        .subscribe(new Action1<TextViewModel>() {
          @Override
          public void call(TextViewModel value) {
            bind(value);
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

  private void bind(TextViewModel viewModel) {
    viewModel.text.observe()
        .subscribe(new Action1<CharSequence>() {
          @Override
          public void call(CharSequence text) {
            setText(text);
          }
        });
    viewModel.getTypeface(getContext())
        .subscribe(new Action1<Typeface>() {
          @Override
          public void call(Typeface typeface) {
            setTypeface(typeface);
          }
        });
  }
}