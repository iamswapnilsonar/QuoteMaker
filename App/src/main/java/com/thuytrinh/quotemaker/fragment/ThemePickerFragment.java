package com.thuytrinh.quotemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.thuytrinh.quotemaker.ObjectCreator;
import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.view.ThemesView;
import com.thuytrinh.quotemaker.viewmodel.ForViewModel;
import com.thuytrinh.quotemaker.viewmodel.ThemePicker;

import javax.inject.Inject;

public class ThemePickerFragment extends BaseFragment implements ForViewModel<ThemePicker> {
  @Inject ThemePicker viewModel;

  public ThemePickerFragment() {
    super(R.layout.fragment_theme_picker);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ObjectCreator.getGraph().inject(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    View overlayView = view.findViewById(R.id.overlayView);
    overlayView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // What if this fragment isn't on top of back stack?
        getFragmentManager().popBackStack();
      }
    });

    ThemesView themesView = (ThemesView) view.findViewById(R.id.themesView);
    themesView.themes.setValue(viewModel.getThemes());
  }

  @Override
  public ThemePicker getViewModel() {
    return viewModel;
  }
}