package com.thuytrinh.quotemaker;

import android.content.Context;
import android.view.LayoutInflater;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.viewmodel.CanvasViewModel;
import com.thuytrinh.quotemaker.viewmodel.ColorPickerViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
    CanvasFragment.class,
    ColorPickerFragment.class
}, library = true)
public class AppModule {
  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  Context provideContext() {
    return context;
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

  @Provides
  CanvasViewModel provideCanvasViewModel(Context context) {
    return new CanvasViewModel(context);
  }

  @Provides
  ColorPickerViewModel provideColorPickerViewModel(Context context, Bus eventBus) {
    return new ColorPickerViewModel(context, eventBus);
  }

  @Provides
  ThemesAdapter provideThemesAdapter(LayoutInflater inflater) {
    return new ThemesAdapter(inflater);
  }
}