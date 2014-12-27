package com.thuytrinh.quotemaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thuytrinh.quotemaker.viewmodel.ColorPickerViewModel;
import com.thuytrinh.quotemaker.viewmodel.ForViewModel;

import javax.inject.Inject;

public class ColorPickerFragment
    extends BaseFragment
    implements ForViewModel<ColorPickerViewModel> {

  @Inject ColorPickerViewModel viewModel;
  @Inject ThemesAdapter themesAdapter;

  public ColorPickerFragment() {
    super(R.layout.fragment_color_picker);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ObjectCreator.getGraph().inject(this);

    themesAdapter.setThemes(viewModel.getThemes());
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView colorsView = (RecyclerView) view.findViewById(R.id.colorsView);
    colorsView.setLayoutManager(new GridLayoutManager(getActivity(), 6));
    colorsView.setAdapter(themesAdapter);
  }

  @Override
  public ColorPickerViewModel getViewModel() {
    return viewModel;
  }
}