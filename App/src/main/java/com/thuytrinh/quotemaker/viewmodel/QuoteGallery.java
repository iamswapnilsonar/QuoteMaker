package com.thuytrinh.quotemaker.viewmodel;

import android.support.annotation.NonNull;

import com.thuytrinh.quotemaker.viewmodel.rx.ObservableList;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

public class QuoteGallery {
  private final ObservableProperty<ObservableList<Quote>> quotes;
  private final DatabaseHelper databaseHelper;

  @Inject
  public QuoteGallery(@NonNull DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
    quotes = new ObservableProperty<>(new ObservableList<>(new ArrayList<Quote>()));
  }

  /**
   * Load quotes from database.
   */
  public void loadQuotes() {
    ArrayList<Quote> mockQuotes = new ArrayList<>(Arrays.asList(
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote(),
        new Quote()
    ));
    quotes.setValue(new ObservableList<>(mockQuotes));
  }
}