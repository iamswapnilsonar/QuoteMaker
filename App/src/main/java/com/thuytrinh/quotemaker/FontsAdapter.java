package com.thuytrinh.quotemaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thuytrinh.quotemaker.viewmodel.FontItem;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.ViewHolder> {
  private List<FontItem> fonts = Collections.emptyList();

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
    final FontItem font = fonts.get(position);

    TextView itemView = (TextView) holder.itemView;
    itemView.setTypeface(font.typeface);
    itemView.setText(font.preview);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        font.isSelected.setValue(true);
      }
    });
  }

  @Override
  public int getItemCount() {
    return fonts.size();
  }

  public void setFonts(List<FontItem> fonts) {
    this.fonts = fonts;
    notifyDataSetChanged();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}