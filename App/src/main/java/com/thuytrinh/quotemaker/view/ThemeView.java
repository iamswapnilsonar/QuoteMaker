package com.thuytrinh.quotemaker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.ForViewModel;
import com.thuytrinh.quotemaker.viewmodel.ThemeViewModel;

import rx.Subscription;
import rx.functions.Action1;

public class ThemeView extends SquareView implements ForViewModel<ThemeViewModel> {
  private ThemeViewModel viewModel;
  private View checkMarkView;
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
  public ThemeViewModel getViewModel() {
    return viewModel;
  }

  public void setViewModel(ThemeViewModel viewModel) {
    this.viewModel = viewModel;
    bindToViewModel();
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    checkMarkView = findViewById(R.id.checkMarkView);
  }

  private void bindToViewModel() {
    if (subscription != null) {
      subscription.unsubscribe();
    }

    setBackgroundColor(viewModel.getBackgroundColor());

    subscription = viewModel.isSelected().observe().subscribe(new Action1<Boolean>() {
      @Override
      public void call(Boolean isSelected) {
        checkMarkView.setVisibility(isSelected ? VISIBLE : GONE);
      }
    });

    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        viewModel.isSelected().setValue(true);
      }
    });
  }
}