package com.thuytrinh.quotemaker.viewmodel;

public class DbField {
  public final String name;
  public final String type;
  public final String constraint;

  public DbField(String name, String type) {
    this(name, type, null);
  }

  public DbField(String name, String type, String constraint) {
    this.name = name;
    this.type = type;
    this.constraint = constraint;
  }

  @Override
  public String toString() {
    return name;
  }
}