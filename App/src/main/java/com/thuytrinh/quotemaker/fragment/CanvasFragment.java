package com.thuytrinh.quotemaker.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.thuytrinh.quotemaker.ObjectCreator;
import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.view.CanvasView;
import com.thuytrinh.quotemaker.view.TextItemView;
import com.thuytrinh.quotemaker.viewmodel.FontItem;
import com.thuytrinh.quotemaker.viewmodel.Quote;
import com.thuytrinh.quotemaker.viewmodel.TextItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import rx.functions.Action1;

public class CanvasFragment extends BaseFragment {
  @Inject Quote viewModel;
  @Inject Bus eventBus;

  private TextItem selectedItem;
  private View deleteView;

  public CanvasFragment() {
    super(R.layout.fragment_canvas);
  }

  /**
   * Determines if given points are inside view
   *
   * @param x    x coordinate of point
   * @param y    y coordinate of point
   * @param view view object to compare
   * @return true if the points are within view bounds, false otherwise
   */
  public static boolean isPointInsideView(float x, float y, View view) {
    int location[] = new int[2];
    view.getLocationOnScreen(location);
    int viewX = location[0];
    int viewY = location[1];

    return (x > viewX && x < (viewX + view.getWidth())) &&
        (y > viewY && y < (viewY + view.getHeight()));
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
        ThemePickerFragment themePickerFragment = new ThemePickerFragment();
        getFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, themePickerFragment)
            .addToBackStack("themePicker")
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
            TextItem newItem = new TextItem();
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

    deleteView = view.findViewById(R.id.deleteView);
  }

  @Subscribe
  public void onEvent(TextItem item) {
    selectedItem = item;
    getFragmentManager()
        .beginTransaction()
        .add(android.R.id.content, new FontPickerFragment())
        .addToBackStack("fontPicker")
        .commit();
  }

  @Subscribe
  public void onEvent(FontItem fontItem) {
    selectedItem.fontPath.setValue(fontItem.fontPath);
  }

  /**
   * Don't change 'Integer' into 'int'.
   */
  @Subscribe
  public void onEvent(Integer textGravity) {
    selectedItem.gravity.setValue(textGravity);
  }

  @Subscribe
  public void onEvent(Float sizeIncrement) {
    selectedItem.size.setValue(selectedItem.size.getValue() + sizeIncrement);
  }

  @Subscribe
  public void onEvent(Pair<Float, Float> alignment) {
    selectedItem.x.setValue(alignment.first);
    selectedItem.y.setValue(alignment.second);
  }

  @Subscribe
  public void onEvent(String text) {
    selectedItem.text.setValue(text);
  }

  @Subscribe
  public void onEvent(TextItemView.DragEvent event) {
    if (isPointInsideView(
        (int) event.dragEvent.getRawX(),
        (int) event.dragEvent.getRawY(),
        deleteView
    )) {
      event.view.setAlpha(0.5f);
    } else {
      event.view.setAlpha(1f);
    }
  }

  @Subscribe
  public void onEvent(TextItemView.UpEvent event) {
    if (isPointInsideView(
        (int) event.moveEvent.getRawX(),
        (int) event.moveEvent.getRawY(),
        deleteView
    )) {
      event.view.viewModel.getValue().delete.call(null);
    }
  }
}