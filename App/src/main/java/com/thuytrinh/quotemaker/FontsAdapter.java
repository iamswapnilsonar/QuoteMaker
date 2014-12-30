package com.thuytrinh.quotemaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thuytrinh.quotemaker.viewmodel.FontViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.ViewHolder> {
  private List<FontViewModel> fonts = Collections.emptyList();

  @Inject
  public FontsAdapter() {}

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_font, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    TextView itemView = (TextView) holder.itemView;
    itemView.setTypeface(fonts.get(position).typeface);
    itemView.setText(fonts.get(position).preview);
  }

  @Override
  public int getItemCount() {
    return fonts.size();
  }

  public void setFonts(List<FontViewModel> fonts) {
    this.fonts = fonts;
    notifyDataSetChanged();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}