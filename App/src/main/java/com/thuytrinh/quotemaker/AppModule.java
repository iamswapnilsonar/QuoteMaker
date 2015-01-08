package com.thuytrinh.quotemaker;

import android.content.Context;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.fragment.CanvasFragment;
import com.thuytrinh.quotemaker.fragment.FontPickerFragment;
import com.thuytrinh.quotemaker.fragment.ThemePickerFragment;
import com.thuytrinh.quotemaker.view.TextItemView;
import com.thuytrinh.quotemaker.viewmodel.DatabaseHelper;
import com.thuytrinh.quotemaker.viewmodel.FontPicker;
import com.thuytrinh.quotemaker.viewmodel.Quote;
import com.thuytrinh.quotemaker.viewmodel.ThemePicker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
    TextItemView.class,
    CanvasFragment.class,
    ThemePickerFragment.class,
    FontPickerFragment.class,
    FontsAdapter.class,
    ThemePicker.class,
    Quote.class,
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

  @Provides
  @Singleton
  DatabaseHelper provideDatabaseHelper(Context context) {
    return new DatabaseHelper(context, "quotes.db", 1);
  }
}