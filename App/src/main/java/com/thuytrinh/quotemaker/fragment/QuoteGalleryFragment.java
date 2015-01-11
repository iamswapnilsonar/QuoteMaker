package com.thuytrinh.quotemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.QuoteGallery;

import javax.inject.Inject;

public class QuoteGalleryFragment extends BaseFragment {
  @Inject QuoteGallery quoteGallery;

  public QuoteGalleryFragment() {
    super(R.layout.fragment_quote_gallery);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    View addQuoteButton = view.findViewById(R.id.addQuoteButton);
    addQuoteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, new QuoteEditorFragment())
            .addToBackStack("quoteEditor")
            .commit();
      }
    });

    RecyclerView quotesView = (RecyclerView) view.findViewById(R.id.quotesView);
    quotesView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
  }
}