package com.thuytrinh.quotemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.thuytrinh.quotemaker.ObjectCreator;
import com.thuytrinh.quotemaker.QuotesAdapter;
import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.Quote;
import com.thuytrinh.quotemaker.viewmodel.QuoteGallery;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class QuoteGalleryFragment extends BaseFragment {
  @Inject QuoteGallery quoteGallery;
  @Inject QuotesAdapter quotesAdapter;

  public QuoteGalleryFragment() {
    super(R.layout.fragment_quote_gallery);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ObjectCreator.getGraph().inject(this);

    quotesAdapter.viewModel.setValue(quoteGallery);
    quoteGallery.openQuote().observe().subscribe(new Action1<Quote>() {
      @Override
      public void call(Quote quote) {
        QuoteEditorFragment fragment = new QuoteEditorFragment();
        fragment.viewModel.setValue(quote);

        getFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, fragment)
            .addToBackStack("quoteEditor")
            .commit();
      }
    });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    quoteGallery.loadQuotes(getActivity())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    View addQuoteButton = view.findViewById(R.id.addQuoteButton);
    addQuoteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        QuoteEditorFragment fragment = new QuoteEditorFragment();
        fragment.viewModel.setValue(new Quote());

        getFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, fragment)
            .addToBackStack("quoteEditor")
            .commit();
      }
    });

    RecyclerView quotesView = (RecyclerView) view.findViewById(R.id.quotesView);
    quotesView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    quotesView.setAdapter(quotesAdapter);
  }
}