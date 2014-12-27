package com.thuytrinh.quotemaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thuytrinh.quotemaker.view.ThemeView;
import com.thuytrinh.quotemaker.viewmodel.ThemeViewModel;

import java.util.List;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ViewHolder> {
  private List<ThemeViewModel> themes;
  private LayoutInflater inflater;

  public ThemesAdapter(LayoutInflater inflater) {
    this.inflater = inflater;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ThemeView itemView = (ThemeView) inflater.inflate(R.layout.view_theme, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.themeView.setViewModel(themes.get(position));
  }

  @Override
  public int getItemCount() {
    return themes.size();
  }

  public void setThemes(List<ThemeViewModel> themes) {
    this.themes = themes;
    notifyDataSetChanged();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final ThemeView themeView;

    public ViewHolder(ThemeView itemView) {
      super(itemView);
      themeView = itemView;
    }
  }
}