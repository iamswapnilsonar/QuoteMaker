package com.thuytrinh.quotemaker.viewmodel;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import rx.functions.Action1;

import static org.assertj.core.api.Assertions.assertThat;

public class QuoteTest extends AndroidTestCase {
  public void testShouldSaveSnapshot() {
    Quote quote = new Quote();
    quote.id().setValue(123L);

    final AtomicReference<File> snapshotFile = new AtomicReference<>();
    quote.saveSnapshot(
        Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        getContext().getFilesDir()
    ).subscribe(new Action1<File>() {
      @Override
      public void call(File file) {
        snapshotFile.set(file);
      }
    });

    assertThat(snapshotFile.get()).isNotNull();
    assertThat(snapshotFile.get().getName()).isEqualTo("snapshot_123.png");
    assertThat(snapshotFile.get()).isEqualTo(quote.snapshotFile().getValue());
  }
}