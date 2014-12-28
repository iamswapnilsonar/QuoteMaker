package com.thuytrinh.quotemaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.viewmodel.CanvasViewModel;
import com.thuytrinh.quotemaker.viewmodel.TextViewModel;
import com.thuytrinh.quotemaker.viewmodel.ThemeViewModel;

import javax.inject.Inject;

import rx.functions.Action1;

public class CanvasFragment extends BaseFragment {
  @Inject CanvasViewModel viewModel;
  @Inject Bus eventBus;
  @Inject TextsAdapter textsAdapter;

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
        ChangeTextFragment fragment = new ChangeTextFragment();
        fragment.onDone().subscribe(new Action1<CharSequence>() {
          @Override
          public void call(CharSequence text) {
            TextViewModel model = new TextViewModel();
            model.text.setValue(text);

            textsAdapter.texts.getValue().add(model);
            textsAdapter.notifyDataSetChanged();
          }
        });
        fragment.show(getFragmentManager(), "addText");
      }
    });

    RecyclerView canvasView = (RecyclerView) view.findViewById(R.id.canvasView);
    canvasView.setLayoutManager(new LinearLayoutManager(getActivity()));
    canvasView.setAdapter(textsAdapter);
  }

  @Subscribe
  public void onEvent(ThemeViewModel selectedTheme) {
    backgroundView.setBackgroundColor(selectedTheme.getBackgroundColor());
  }
}