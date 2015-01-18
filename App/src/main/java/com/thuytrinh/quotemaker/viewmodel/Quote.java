package com.thuytrinh.quotemaker.viewmodel;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.model.QuoteModel;
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

public class Quote {
  private final ObservableProperty<Long> id = new ObservableProperty<>();
  private final ObservableList<TextItem> items = new ObservableList<>(new ArrayList<TextItem>());
  private final ObservableProperty<Integer> backgroundColor = new ObservableProperty<>(0xff018db1);
  private final ObservableProperty<File> snapshotFile = new ObservableProperty<>();

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

    /*
    this.id.setValue(id);
    this.backgroundColor.setValue(backgroundColor);

    if (!TextUtils.isEmpty(snapshotPath)) {
      snapshotFile.setValue(new File(snapshotPath));
    }
    */
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

          // Done! Let's emit the result.
          subscriber.onNext(snapshotFile);
        } catch (IOException e) {
          subscriber.onError(e);
        }
      }
    }).doOnNext(snapshotFile);
  }

  public Observable<Object> save(@NonNull final Realm realm) {
    return Observable.create(new Observable.OnSubscribe<Object>() {
      @Override
      public void call(Subscriber<? super Object> subscriber) {
        realm.beginTransaction();
        realm.commitTransaction();
      }
    });
  }
}