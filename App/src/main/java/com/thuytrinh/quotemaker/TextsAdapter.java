package com.thuytrinh.quotemaker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thuytrinh.quotemaker.viewmodel.ObservableProperty;
import com.thuytrinh.quotemaker.viewmodel.TextViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.functions.Action1;

public class TextsAdapter extends RecyclerView.Adapter<TextsAdapter.ViewHolder> {
  public final ObservableProperty<ArrayList<TextViewModel>> texts = new ObservableProperty<>(new ArrayList<TextViewModel>());

  @Inject
  public TextsAdapter() {}

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(new TextView(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    TextViewModel item = texts.getValue().get(position);
    item.text.observe().subscribe(new Action1<CharSequence>() {
      @Override
      public void call(CharSequence text) {
        holder.textView.setText(text);
      }
    });
  }

  @Override
  public int getItemCount() {
    return texts.getValue().size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView textView;

    public ViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView;
    }
  }
}