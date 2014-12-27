package com.thuytrinh.quotemaker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.ObservableProperty;
import com.thuytrinh.quotemaker.viewmodel.ThemeViewModel;

import rx.Subscription;
import rx.functions.Action1;

public class ThemeView extends SquareView {
  public final ObservableProperty<ThemeViewModel> theme = new ObservableProperty<>();
  private View checkMarkView;
  private View colorView;
  private Subscription subscription;

  public ThemeView(Context context) {
    super(context);
  }

  public ThemeView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ThemeView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ThemeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (subscription != null) {
      subscription.unsubscribe();
    }
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    colorView = findViewById(R.id.colorView);
    checkMarkView = findViewById(R.id.checkMarkView);

    theme.observe()
        .doOnNext(new Action1<ThemeViewModel>() {
          @Override
          public void call(ThemeViewModel theme) {
            if (subscription != null) {
              subscription.unsubscribe();
            }
          }
        })
        .subscribe(new Action1<ThemeViewModel>() {
          @Override
          public void call(final ThemeViewModel theme) {
            colorView.setBackgroundColor(theme.getBackgroundColor());

            subscription = theme.isSelected().observe().subscribe(new Action1<Boolean>() {
              @Override
              public void call(Boolean isSelected) {
                checkMarkView.setVisibility(isSelected ? VISIBLE : GONE);
              }
            });

            setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                theme.isSelected().setValue(true);
              }
            });
          }
        });
  }
}