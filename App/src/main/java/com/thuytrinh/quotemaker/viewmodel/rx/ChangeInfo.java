package com.thuytrinh.quotemaker.viewmodel.rx;

public class ChangeInfo {
  public final int positionStart;
  public final int itemCount;

  public ChangeInfo(int positionStart, int itemCount) {
    this.positionStart = positionStart;
    this.itemCount = itemCount;
  }
}