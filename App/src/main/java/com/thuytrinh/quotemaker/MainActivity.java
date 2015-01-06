package com.thuytrinh.quotemaker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.thuytrinh.quotemaker.fragment.CanvasFragment;

public class MainActivity extends ActionBarActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.container, new CanvasFragment())
          .commit();
    }
  }
}