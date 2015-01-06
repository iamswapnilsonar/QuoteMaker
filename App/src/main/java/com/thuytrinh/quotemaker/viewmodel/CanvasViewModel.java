package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;

import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.R;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class CanvasViewModel {
  public final ObservableList<TextViewModel> items = new ObservableList<>(new ArrayList<TextViewModel>());
  public final ObservableProperty<Integer> backgroundColor;
  private final Context context;

  @Inject
  public CanvasViewModel(Context context) {
    this.context = context;

    backgroundColor = new ObservableProperty<>(context.getResources().getColor(R.color.teal));

    Observable.from(items)
        .subscribe(new Action1<TextViewModel>() {
          @Override
          public void call(final TextViewModel item) {
            item.delete.observe()
                .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object unused) {
                    items.remove(item);
                  }
                });
          }
        });
  }

  @Subscribe
  public void onEvent(ThemeViewModel selectedTheme) {
    backgroundColor.setValue(selectedTheme.getBackgroundColor());
  }
}