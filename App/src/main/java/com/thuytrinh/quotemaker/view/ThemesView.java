package com.thuytrinh.quotemaker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridLayout;

import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.ObservableProperty;
import com.thuytrinh.quotemaker.viewmodel.ThemeViewModel;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ThemesView extends GridLayout {
  public final ObservableProperty<List<ThemeViewModel>> themes = new ObservableProperty<>();

  public ThemesView(Context context) {
    super(context);
    init();
  }

  public ThemesView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ThemesView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ThemesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    if (isInEditMode()) {
      return;
    }

    themes.observe()
        .doOnNext(new Action1<List<ThemeViewModel>>() {
          @Override
          public void call(List<ThemeViewModel> themes) {
            removeAllViews();
          }
        })
        .flatMap(new Func1<List<ThemeViewModel>, Observable<ThemeViewModel>>() {
          @Override
          public Observable<ThemeViewModel> call(List<ThemeViewModel> themes) {
            return Observable.from(themes);
          }
        })
        .subscribe(new Action1<ThemeViewModel>() {
          @Override
          public void call(ThemeViewModel theme) {
            ThemeView themeView = (ThemeView) LayoutInflater.from(getContext())
                .inflate(R.layout.view_theme, ThemesView.this, false);
            addView(themeView);
            themeView.theme.setValue(theme);
          }
        });
  }
}