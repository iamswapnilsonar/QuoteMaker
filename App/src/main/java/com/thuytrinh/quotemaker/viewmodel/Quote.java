package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.model.QuoteModel;
import com.thuytrinh.quotemaker.model.TextModel;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableList;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class Quote {
  private final ObservableProperty<Long> id = new ObservableProperty<>();
  private final ObservableList<TextItem> items = new ObservableList<>(new ArrayList<TextItem>());
  private final ObservableProperty<Integer> backgroundColor = new ObservableProperty<>(0xff018db1);
  private final ObservableProperty<File> snapshotFile = new ObservableProperty<>();

  private QuoteModel model;

  @Inject
  public Quote() {
    Observable.from(items)
        .subscribe(new Action1<TextItem>() {
          @Override
          public void call(final TextItem item) {
            item.delete().observe()
                .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object unused) {
                    items.remove(item);
                  }
                });
          }
        });
  }

  public Quote(QuoteModel model) {
    this();

    backgroundColor.setValue(model.getBackgroundColor());
    if (!TextUtils.isEmpty(model.getSnapshotFilePath())) {
      snapshotFile.setValue(new File(model.getSnapshotFilePath()));
    }

    Observable.from(model.getItems())
        .subscribe(new Action1<TextModel>() {
          @Override
          public void call(TextModel model) {
            items.add(new TextItem(model));
          }
        });

    this.model = model;
  }

  public ObservableProperty<Long> id() {
    return id;
  }

  public ObservableList<TextItem> items() {
    return items;
  }

  public ObservableProperty<Integer> backgroundColor() {
    return backgroundColor;
  }

  public ObservableProperty<File> snapshotFile() {
    return snapshotFile;
  }

  @Subscribe
  public void onEvent(Theme selectedTheme) {
    backgroundColor.setValue(selectedTheme.getBackgroundColor());
  }

  public Observable<File> saveSnapshot(@NonNull final Bitmap snapshot,
                                       @NonNull final File dir) {
    return Observable.create(new Observable.OnSubscribe<File>() {
      @Override
      public void call(Subscriber<? super File> subscriber) {
        // What if id is unavailable yet?
        String name = String.format("snapshot_%d.png", id.getValue());
        File snapshotFile = new File(dir, name);
        try {
          FileOutputStream stream = new FileOutputStream(snapshotFile);
          snapshot.compress(Bitmap.CompressFormat.PNG, 100, stream);
          stream.close();

          subscriber.onNext(snapshotFile);
          subscriber.onCompleted();
        } catch (IOException e) {
          subscriber.onError(e);
        }
      }
    }).doOnNext(snapshotFile);
  }

  public Observable<Object> save(final Context appContext) {
    return Observable.create(new Observable.OnSubscribe<Object>() {
      @Override
      public void call(Subscriber<? super Object> subscriber) {
        final Realm realm = Realm.getInstance(appContext);
        realm.beginTransaction();

        if (model == null) {
          model = realm.createObject(QuoteModel.class);
        }

        model.setBackgroundColor(backgroundColor.getValue());
        model.setSnapshotFilePath(snapshotFile.getValue().getPath());

        Observable.from(items)
            .flatMap(new Func1<TextItem, Observable<TextModel>>() {
              @Override
              public Observable<TextModel> call(TextItem item) {
                // Should we do this?
                return item.save(realm);
              }
            })
            .subscribe(new Action1<TextModel>() {
              @Override
              public void call(TextModel itemModel) {
                model.getItems().add(itemModel);
              }
            });

        realm.commitTransaction();
        subscriber.onCompleted();
      }
    });
  }
}