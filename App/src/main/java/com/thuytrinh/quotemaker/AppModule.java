package com.thuytrinh.quotemaker;

import android.content.Context;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.fragment.CanvasFragment;
import com.thuytrinh.quotemaker.fragment.FontPickerFragment;
import com.thuytrinh.quotemaker.fragment.ThemePickerFragment;
import com.thuytrinh.quotemaker.view.TextItemView;
import com.thuytrinh.quotemaker.viewmodel.DbHelper;
import com.thuytrinh.quotemaker.viewmodel.FontPicker;
import com.thuytrinh.quotemaker.viewmodel.QuoteEditor;
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

  @Provides
  @Singleton
  DbHelper provideDbHelper(Context context) {
    return new DbHelper(context, "quotes.db", 1);
  }
}