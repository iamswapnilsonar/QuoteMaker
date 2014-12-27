package com.thuytrinh.quotemaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.thuytrinh.quotemaker.view.ThemesView;
import com.thuytrinh.quotemaker.viewmodel.ColorPickerViewModel;
import com.thuytrinh.quotemaker.viewmodel.ForViewModel;

import javax.inject.Inject;

public class ColorPickerFragment extends BaseFragment implements ForViewModel<ColorPickerViewModel> {
  @Inject ColorPickerViewModel viewModel;

  public ColorPickerFragment() {
    super(R.layout.fragment_color_picker);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ObjectCreator.getGraph().inject(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ThemesView colorsView = (ThemesView) view.findViewById(R.id.colorsView);
    colorsView.themes.setValue(viewModel.getThemes());
  }

  @Override
  public ColorPickerViewModel getViewModel() {
    return viewModel;
  }
}