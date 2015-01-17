package com.thuytrinh.quotemaker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.FontsAdapter;
import com.thuytrinh.quotemaker.ObjectCreator;
import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.FontPicker;
import com.thuytrinh.quotemaker.viewmodel.ForViewModel;

import javax.inject.Inject;

import rx.functions.Action1;

public class FontPickerFragment extends BaseFragment implements ForViewModel<FontPicker> {
  @Inject FontPicker viewModel;
  @Inject FontsAdapter fontsAdapter;
  @Inject Bus eventBus;

  public FontPickerFragment() {
    super(R.layout.fragment_font_picker);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ObjectCreator.getGraph().inject(this);
    fontsAdapter.setFonts(viewModel.fonts);
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

    RecyclerView fontsView = ((RecyclerView) view.findViewById(R.id.fontsView));
    fontsView.setLayoutManager(new LinearLayoutManager(getActivity()));
    fontsView.setAdapter(fontsAdapter);

    View alignLeftButton = view.findViewById(R.id.alignLeftButton);
    alignLeftButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        eventBus.post(Gravity.LEFT);
      }
    });

    View alignCenterButton = view.findViewById(R.id.alignCenterButton);
    alignCenterButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        eventBus.post(Gravity.CENTER);
      }
    });

    View alignRightButton = view.findViewById(R.id.alignRightButton);
    alignRightButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        eventBus.post(Gravity.RIGHT);
      }
    });

    View decreaseSizeButton = view.findViewById(R.id.decreaseSizeButton);
    decreaseSizeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        eventBus.post(-1f);
      }
    });

    View increaseSizeButton = view.findViewById(R.id.increaseSizeButton);
    increaseSizeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        eventBus.post(1f);
      }
    });

    View centerButton = view.findViewById(R.id.centerButton);
    centerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        eventBus.post(new Pair<>(0f, 0f));
      }
    });

    View editTextButton = view.findViewById(R.id.editTextButton);
    editTextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ChangeTextFragment fragment = new ChangeTextFragment();
        fragment.onDone().subscribe(new Action1<CharSequence>() {
          @Override
          public void call(CharSequence text) {
            eventBus.post(String.valueOf(text));
          }
        });
        fragment.show(getFragmentManager(), "editText");
      }
    });
  }

  @Override
  public FontPicker getViewModel() {
    return viewModel;
  }
}