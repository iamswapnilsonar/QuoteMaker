package com.thuytrinh.quotemaker.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.thuytrinh.quotemaker.BuildConfig;
import com.thuytrinh.quotemaker.R;

import rx.Observable;
import rx.subjects.PublishSubject;

public class ChangeTextFragment extends BaseDialogFragment {
  private final PublishSubject<CharSequence> onDone = PublishSubject.create();

  public ChangeTextFragment() {
    super(R.layout.fragment_change_text);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    final EditText editTextView = (EditText) view.findViewById(R.id.editTextView);
    if (BuildConfig.DEBUG) {
      editTextView.setText("Move Fast\nand\nBreak Things");
    }

    Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    toolbar.inflateMenu(R.menu.change_text);
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.doneMenuItem) {
          onDone.onNext(editTextView.getText());
          dismiss();

          return true;
        } else {
          return false;
        }
      }
    });
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    onDone.onCompleted();
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }

  public Observable<CharSequence> onDone() {
    return onDone;
  }
}