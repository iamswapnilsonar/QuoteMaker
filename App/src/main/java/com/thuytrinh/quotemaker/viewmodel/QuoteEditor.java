package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;

import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableList;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class QuoteEditor {
  public final ObservableList<TextItem> items = new ObservableList<>(new ArrayList<TextItem>());
  public final ObservableProperty<Integer> backgroundColor;
  private final Context appContext;

  @Inject
  public QuoteEditor(Context appContext) {
    this.appContext = appContext;

    // Default background color.
    backgroundColor = new ObservableProperty<>(appContext.getResources().getColor(R.color.teal));

    Observable.from(items)
        .subscribe(new Action1<TextItem>() {
          @Override
          public void call(final TextItem item) {
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