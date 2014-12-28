package com.thuytrinh.quotemaker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class ChangeTextFragment extends BaseDialogFragment {
  public ChangeTextFragment() {
    super(R.layout.fragment_change_text);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    toolbar.inflateMenu(R.menu.change_text);
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.doneMenuItem) {
          dismiss();
          return true;
        } else {
          return false;
        }
      }
    });
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }
}