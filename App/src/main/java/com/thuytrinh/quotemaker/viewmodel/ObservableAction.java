package com.thuytrinh.quotemaker.viewmodel;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * This doesn't suit for async operation yet.
 */
public class ObservableAction<T> implements Action1<T> {
  private final PublishSubject<T> callSubject = PublishSubject.create();

  @Override
  public void call(T t) {
    callSubject.onNext(t);
  }

  public Observable<T> observe() {
    return callSubject;
  }
}