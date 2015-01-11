package com.thuytrinh.quotemaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuytrinh.quotemaker.viewmodel.Quote;
import com.thuytrinh.quotemaker.viewmodel.QuoteGallery;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableList;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {
  public final ObservableProperty<QuoteGallery> viewModel = new ObservableProperty<>();
  private List<Quote> quotes = Collections.emptyList();

  @Inject
  public QuotesAdapter() {
    viewModel.observe()
        .subscribe(new Action1<QuoteGallery>() {
          @Override
          public void call(QuoteGallery quoteGallery) {
            quoteGallery.quotes().observe()
                .subscribe(new Action1<ObservableList<Quote>>() {
                  @Override
                  public void call(ObservableList<Quote> value) {
                    quotes = value;
                  }
                });
          }
        });
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_quote, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
  }

  @Override
  public int getItemCount() {
    return quotes.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}