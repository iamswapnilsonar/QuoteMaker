package com.thuytrinh.quotemaker;

import android.content.Context;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.fragment.CanvasFragment;
import com.thuytrinh.quotemaker.fragment.ColorPickerFragment;
import com.thuytrinh.quotemaker.fragment.FontPickerFragment;
import com.thuytrinh.quotemaker.view.TextItemView;
import com.thuytrinh.quotemaker.viewmodel.FontPicker;
import com.thuytrinh.quotemaker.viewmodel.QuoteEditor;
import com.thuytrinh.quotemaker.viewmodel.ThemePicker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
    TextItemView.class,
    CanvasFragment.class,
    ColorPickerFragment.class,
    FontPickerFragment.class,
    FontsAdapter.class,
    ThemePicker.class,
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