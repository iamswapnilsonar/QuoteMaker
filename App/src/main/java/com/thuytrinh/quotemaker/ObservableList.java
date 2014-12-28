package com.thuytrinh.quotemaker;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import rx.Observable;
import rx.subjects.PublishSubject;

public class ObservableList<T> implements List<T> {
  private List<T> decoratedList;
  private PublishSubject<ChangeInfo> onItemsInserted;

  public ObservableList(List<T> decoratedList) {
    this.decoratedList = decoratedList;

    onItemsInserted = PublishSubject.create();
  }

  @Override
  public void add(int location, T object) {
    decoratedList.add(location, object);
  }

  @Override
  public boolean add(T object) {
    int location = size();
    boolean result = decoratedList.add(object);
    onItemsInserted.onNext(new ChangeInfo(location, 1));
    return result;
  }

  @Override
  public boolean addAll(int location, Collection<? extends T> collection) {
    return decoratedList.addAll(location, collection);
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    return decoratedList.addAll(collection);
  }

  @Override
  public void clear() {
    decoratedList.clear();
  }

  @Override
  public boolean contains(Object object) {
    return decoratedList.contains(object);
  }

  @Override
  public boolean containsAll(@NonNull Collection<?> collection) {
    return decoratedList.containsAll(collection);
  }

  @Override
  public T get(int location) {
    return decoratedList.get(location);
  }

  @Override
  public int indexOf(Object object) {
    return decoratedList.indexOf(object);
  }

  @Override
  public boolean isEmpty() {
    return decoratedList.isEmpty();
  }

  @NonNull
  @Override
  public Iterator<T> iterator() {
    return decoratedList.iterator();
  }

  @Override
  public int lastIndexOf(Object object) {
    return decoratedList.lastIndexOf(object);
  }

  @NonNull
  @Override
  public ListIterator<T> listIterator() {
    return decoratedList.listIterator();
  }

  @NonNull
  @Override
  public ListIterator<T> listIterator(int location) {
    return decoratedList.listIterator(location);
  }

  @Override
  public T remove(int location) {
    return decoratedList.remove(location);
  }

  @Override
  public boolean remove(Object object) {
    return decoratedList.remove(object);
  }

  @Override
  public boolean removeAll(@NonNull Collection<?> collection) {
    return decoratedList.removeAll(collection);
  }

  @Override
  public boolean retainAll(@NonNull Collection<?> collection) {
    return decoratedList.retainAll(collection);
  }

  @Override
  public T set(int location, T object) {
    return decoratedList.set(location, object);
  }

  @Override
  public int size() {
    return decoratedList.size();
  }

  @NonNull
  @Override
  public List<T> subList(int start, int end) {
    return decoratedList.subList(start, end);
  }

  @NonNull
  @Override
  public Object[] toArray() {
    return decoratedList.toArray();
  }

  @NonNull
  @Override
  public <T1> T1[] toArray(@NonNull T1[] array) {
    return decoratedList.toArray(array);
  }

  public Observable<ChangeInfo> onItemsInserted() {
    return onItemsInserted;
  }
}