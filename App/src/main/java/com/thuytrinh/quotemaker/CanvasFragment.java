package com.thuytrinh.quotemaker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.view.CanvasView;
import com.thuytrinh.quotemaker.viewmodel.CanvasViewModel;
import com.thuytrinh.quotemaker.viewmodel.FontViewModel;
import com.thuytrinh.quotemaker.viewmodel.TextViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import rx.functions.Action1;

public class CanvasFragment extends BaseFragment {
  @Inject CanvasViewModel viewModel;
  @Inject Bus eventBus;

  private TextViewModel selectedTextViewModel;

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
    eventBus.register(viewModel);
    eventBus.register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    eventBus.unregister(viewModel);
    eventBus.unregister(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    final View backgroundView = view.findViewById(R.id.backgroundView);
    viewModel.backgroundColor.observe().subscribe(new Action1<Integer>() {
      @Override
      public void call(Integer color) {
        backgroundView.setBackgroundColor(color);
      }
    });

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
            TextViewModel newItem = new TextViewModel();
            newItem.text.setValue(text);

            viewModel.items.add(newItem);
          }
        });
        fragment.show(getFragmentManager(), "addText");
      }
    });

    CanvasView canvasView = (CanvasView) view.findViewById(R.id.canvasView);
    canvasView.viewModel.setValue(viewModel);

    final View quoteView = view.findViewById(R.id.quoteView);
    View saveButton = view.findViewById(R.id.saveButton);

    // TODO: Test it on a real device.
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        quoteView.buildDrawingCache();
        Bitmap snapshot = quoteView.getDrawingCache();

        File file = new File(getActivity().getFilesDir(), "snapshot_" + System.currentTimeMillis());
        try {
          FileOutputStream stream = new FileOutputStream(file);
          snapshot.compress(Bitmap.CompressFormat.PNG, 100, stream);
          stream.close();

          Intent shareIntent = new Intent();
          shareIntent.setAction(Intent.ACTION_SEND);
          shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
          shareIntent.setType("image/*");
          startActivity(shareIntent);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @Subscribe
  public void onEvent(TextViewModel textViewModel) {
    selectedTextViewModel = textViewModel;
    getFragmentManager()
        .beginTransaction()
        .add(android.R.id.content, new FontPickerFragment())
        .addToBackStack("fontPicker")
        .commit();
  }

  @Subscribe
  public void onEvent(FontViewModel fontViewModel) {
    selectedTextViewModel.fontPath.setValue(fontViewModel.fontPath);
  }

  /**
   * Don't change 'Integer' into 'int'.
   */
  @Subscribe
  public void onEvent(Integer textGravity) {
    selectedTextViewModel.gravity.setValue(textGravity);
  }

  @Subscribe
  public void onEvent(Float sizeIncrement) {
    selectedTextViewModel.size.setValue(selectedTextViewModel.size.getValue() + sizeIncrement);
  }

  @Subscribe
  public void onEvent(Pair<Float, Float> alignment) {
    selectedTextViewModel.x.setValue(alignment.first);
    selectedTextViewModel.y.setValue(alignment.second);
  }

  @Subscribe
  public void onEvent(String text) {
    selectedTextViewModel.text.setValue(text);
  }
}