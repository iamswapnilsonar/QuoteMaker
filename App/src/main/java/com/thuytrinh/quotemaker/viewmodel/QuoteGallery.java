package com.thuytrinh.quotemaker.viewmodel;

import com.thuytrinh.quotemaker.viewmodel.rx.ObservableList;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

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