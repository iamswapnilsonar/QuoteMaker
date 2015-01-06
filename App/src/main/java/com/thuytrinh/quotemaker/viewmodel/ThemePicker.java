package com.thuytrinh.quotemaker.viewmodel;

import android.content.Context;
import android.content.res.Resources;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.R;
import com.thuytrinh.quotemaker.viewmodel.rx.ObservableProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class ThemePicker {
  private final Context appContext;
  private final List<Theme> themes = new ArrayList<>();
  private final ObservableProperty<Theme> selectedTheme = new ObservableProperty<>();
  private final Bus eventBus;

  @Inject
  public ThemePicker(Context appContext, Bus eventBus) {
    this.appContext = appContext;
    this.eventBus = eventBus;

    initThemes();
    initRelationships();
  }

  public List<Theme> getThemes() {
    return themes;
  }

  private void initRelationships() {
    Observable.from(themes)
        .subscribe(new Action1<Theme>() {
          @Override
          public void call(final Theme theme) {
            theme.isSelected().observe().subscribe(new Action1<Boolean>() {
              @Override
              public void call(Boolean isSelected) {
                if (isSelected && theme != selectedTheme.getValue()) {
                  if (selectedTheme.hasValue()) {
                    selectedTheme.getValue().isSelected().setValue(false);
                  }

                  selectedTheme.setValue(theme);
                }
              }
            });
          }
        });

    selectedTheme.observe().subscribe(new Action1<Theme>() {
      @Override
      public void call(Theme theme) {
        eventBus.post(theme);
      }
    });
  }

  private void initThemes() {
    Resources resources = appContext.getResources();
    themes.addAll(Arrays.asList(
        new Theme(
            resources.getResourceName(R.color.raspberry),
            resources.getColor(R.color.raspberry),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.navy),
            resources.getColor(R.color.navy),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.grass),
            resources.getColor(R.color.grass),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.teal),
            resources.getColor(R.color.teal),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.red),
            resources.getColor(R.color.red),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.yellow),
            resources.getColor(R.color.yellow),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.kelly_green),
            resources.getColor(R.color.kelly_green),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.baby_blue),
            resources.getColor(R.color.baby_blue),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.heather_grey),
            resources.getColor(R.color.heather_grey),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.gold),
            resources.getColor(R.color.gold),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.eggplant),
            resources.getColor(R.color.eggplant),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.dark_forest),
            resources.getColor(R.color.dark_forest),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.grass_2),
            resources.getColor(R.color.grass_2),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.mint),
            resources.getColor(R.color.mint),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.heather_grey_3),
            resources.getColor(R.color.heather_grey_3),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.grass_3),
            resources.getColor(R.color.grass_3),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.fuchsia),
            resources.getColor(R.color.fuchsia),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.white),
            resources.getColor(R.color.white),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.blue),
            resources.getColor(R.color.blue),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.orange),
            resources.getColor(R.color.orange),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.baby_blue_2),
            resources.getColor(R.color.baby_blue_2),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.brown),
            resources.getColor(R.color.brown),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.creme),
            resources.getColor(R.color.creme),
            android.R.color.white
        ),
        new Theme(
            resources.getResourceName(R.color.royal_blue),
            resources.getColor(R.color.royal_blue),
            android.R.color.white
        )
    ));
  }
}