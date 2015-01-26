package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.Gravity;

import com.thuytrinh.quotemaker.model.TextModel;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableAction;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class TextItem {
  private final ObservableProperty<String> text = new ObservableProperty<>();
  private final ObservableProperty<String> fontPath = new ObservableProperty<>("fonts/Anton.ttf");
  private final ObservableProperty<Float> size = new ObservableProperty<>(45f); /* In sp */
  private final ObservableProperty<Float> x = new ObservableProperty<>(0f);
  private final ObservableProperty<Float> y = new ObservableProperty<>(0f);
  private final ObservableProperty<Integer> gravity = new ObservableProperty<>(Gravity.CENTER);
  private final ObservableAction<Object> delete = new ObservableAction<>();

  private TextModel model;

  public TextItem() {}

  public TextItem(@NonNull TextModel model) {
    this();

    text.setValue(model.getText());
    fontPath.setValue(model.getFontPath());
    size.setValue(model.getSize());
    x.setValue(model.getX());
    y.setValue(model.getY());
    gravity.setValue(model.getGravity());

    this.model = model;
  }

  public ObservableProperty<String> text() {
    return text;
  }

  public ObservableProperty<Float> size() {
    return size;
  }

  public ObservableProperty<String> fontPath() {
    return fontPath;
  }

  public ObservableProperty<Float> x() {
    return x;
  }

  public ObservableProperty<Float> y() {
    return y;
  }

  public ObservableProperty<Integer> gravity() {
    return gravity;
  }

  public ObservableAction<Object> delete() {
    return delete;
  }

  public Observable<Typeface> getTypeface(final Context appContext) {
    return fontPath.observe()
        .map(new Func1<String, Typeface>() {
          @Override
          public Typeface call(String value) {
            return Typeface.createFromAsset(appContext.getAssets(), value);
          }
        });
  }

  public Observable<TextModel> save(@NonNull final Realm realm) {
    return Observable.create(new Observable.OnSubscribe<TextModel>() {
      @Override
      public void call(Subscriber<? super TextModel> subscriber) {
        if (model == null) {
          model = realm.createObject(TextModel.class);
        }

        model.setText(text.getValue());
        model.setSize(size.getValue());
        model.setFontPath(fontPath.getValue());
        model.setX(x.getValue());
        model.setY(y.getValue());
        model.setGravity(gravity.getValue());

        subscriber.onNext(model);
        subscriber.onCompleted();
      }
    });
  }
}