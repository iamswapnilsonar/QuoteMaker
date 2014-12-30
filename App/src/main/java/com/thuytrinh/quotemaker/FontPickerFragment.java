package com.thuytrinh.quotemaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thuytrinh.quotemaker.viewmodel.FontPicker;
import com.thuytrinh.quotemaker.viewmodel.ForViewModel;

import javax.inject.Inject;

public class FontPickerFragment extends BaseFragment implements ForViewModel<FontPicker> {
  @Inject FontPicker viewModel;

  public FontPickerFragment() {
    super(R.layout.fragment_font_picker);
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

    RecyclerView fontsView = ((RecyclerView) view.findViewById(R.id.fontsView));
    fontsView.setLayoutManager(new LinearLayoutManager(getActivity()));
  }

  @Override
  public FontPicker getViewModel() {
    return viewModel;
  }
}