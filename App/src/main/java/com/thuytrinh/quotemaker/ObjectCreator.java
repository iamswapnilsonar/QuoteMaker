package com.thuytrinh.quotemaker;

import android.content.Context;

import dagger.ObjectGraph;

public class ObjectCreator {
  private static ObjectCreator objectCreator;
  private ObjectGraph graph;

  private ObjectCreator() {}

  public static void init(Context context) {
    if (objectCreator == null) {
      objectCreator = new ObjectCreator();
      objectCreator.graph = ObjectGraph.create(new AppModule(context));
    }
  }

  public static ObjectGraph getGraph() {
    return objectCreator.graph;
  }
}