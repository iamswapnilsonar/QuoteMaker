package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;

import com.thuytrinh.quotemaker.model.QuoteModel;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableList;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class QuoteGallery {
  private final ObservableProperty<ObservableList<Quote>> quotes;

  @Inject
  public QuoteGallery() {
    quotes = new ObservableProperty<>(new ObservableList<>(new ArrayList<Quote>()));
  }

  public ObservableProperty<ObservableList<Quote>> quotes() {
    return quotes;
  }

  /**
   * Load quotes from database.
   */
  public Observable<List<Quote>> loadQuotes(final Context context) {
    return Observable.create(new Observable.OnSubscribe<List<Quote>>() {
      @Override
      public void call(Subscriber<? super List<Quote>> subscriber) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();

        RealmResults<QuoteModel> results = realm.where(QuoteModel.class).findAll();
        Observable.from(results).subscribe(new Action1<QuoteModel>() {
          @Override
          public void call(QuoteModel model) {
            quotes.getValue().add(new Quote(model));
          }
        });

        realm.commitTransaction();
        subscriber.onCompleted();
      }
    });
  }
}