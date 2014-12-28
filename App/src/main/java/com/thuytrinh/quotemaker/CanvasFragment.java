package com.thuytrinh.quotemaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.viewmodel.CanvasViewModel;
import com.thuytrinh.quotemaker.viewmodel.ThemeViewModel;

import javax.inject.Inject;

public class CanvasFragment extends BaseFragment {
  @Inject CanvasViewModel viewModel;
  @Inject Bus eventBus;

  private View backgroundView;

  public CanvasFragment() {
    super(R.layout.fragment_canvas);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ObjectCreator.getGraph().inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    eventBus.register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    eventBus.unregister(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    backgroundView = view.findViewById(R.id.backgroundView);

    View chooseBackgroundButton = view.findViewById(R.id.chooseBackgroundButton);
    chooseBackgroundButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
        getFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, colorPickerFragment)
            .addToBackStack("colorPicker")
            .commit();
      }
    });

    View addTextButton = view.findViewById(R.id.addTextButton);
    addTextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new ChangeTextFragment().show(getFragmentManager(), "addText");
      }
    });
  }

  @Subscribe
  public void onEvent(ThemeViewModel selectedTheme) {
    backgroundView.setBackgroundColor(selectedTheme.getBackgroundColor());
  }
}