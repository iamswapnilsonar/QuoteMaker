package com.thuytrinh.quotemaker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {
  @Inject
  public QuotesAdapter() {}

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}