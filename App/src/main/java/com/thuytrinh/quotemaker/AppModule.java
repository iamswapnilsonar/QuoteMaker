package com.thuytrinh.quotemaker;

import android.content.Context;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.view.TextItemView;
import com.thuytrinh.quotemaker.viewmodel.ColorPickerViewModel;
import com.thuytrinh.quotemaker.viewmodel.FontPicker;
import com.thuytrinh.quotemaker.viewmodel.QuoteEditor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
    TextItemView.class,
    CanvasFragment.class,
    ColorPickerFragment.class,
    FontPickerFragment.class,
    FontsAdapter.class,
    ColorPickerViewModel.class,
    QuoteEditor.class,
    FontPicker.class
}, library = true)
public class AppModule {
  private final Context appContext;

  public AppModule(Context appContext) {
    this.appContext = appContext;
  }

  @Provides
  Context provideContext() {
    return appContext;
  }

  @Provides
  @Singleton
  Bus provideBus() {
    return new MainThreadBus();
  }
}