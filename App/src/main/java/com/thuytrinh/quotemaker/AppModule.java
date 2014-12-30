package com.thuytrinh.quotemaker;

import android.content.Context;
import android.view.LayoutInflater;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.view.CoolTextView;
import com.thuytrinh.quotemaker.viewmodel.CanvasViewModel;
import com.thuytrinh.quotemaker.viewmodel.ColorPickerViewModel;
import com.thuytrinh.quotemaker.viewmodel.FontPicker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
    CoolTextView.class,
    CanvasFragment.class,
    ColorPickerFragment.class,
    FontPickerFragment.class,
    ColorPickerViewModel.class,
    CanvasViewModel.class,
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
  LayoutInflater provideLayoutInflater(Context context) {
    return LayoutInflater.from(context);
  }

  @Provides
  @Singleton
  Bus provideBus() {
    return new MainThreadBus();
  }
}