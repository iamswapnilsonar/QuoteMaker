package com.thuytrinh.quotemaker.model;

import io.realm.RealmObject;

public class TextModel extends RealmObject {
  private String text;
  private String fontPath;
  private float size;
  private float x;
  private float y;
  private int gravity;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getFontPath() {
    return fontPath;
  }

  public void setFontPath(String fontPath) {
    this.fontPath = fontPath;
  }

  public float getSize() {
    return size;
  }

  public void setSize(float size) {
    this.size = size;
  }

  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public int getGravity() {
    return gravity;
  }

  public void setGravity(int gravity) {
    this.gravity = gravity;
  }
}