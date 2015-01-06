package com.thuytrinh.quotemaker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.thuytrinh.quotemaker.ObjectCreator;
import com.thuytrinh.quotemaker.viewmodel.ObservableProperty;
import com.thuytrinh.quotemaker.viewmodel.TextViewModel;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class CoolTextView extends TextView {
  public final ObservableProperty<TextViewModel> viewModel = new ObservableProperty<>();
  private final PublishSubject<MotionEvent> onUp = PublishSubject.create();

  @Inject Bus eventBus;

  private Subscription viewModelSubscription;
  private GestureDetector gestureDetector;

  public CoolTextView(Context context) {
    super(context);
    init();
  }

  public CoolTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CoolTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CoolTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  public Observable<MotionEvent> onUp() {
    return onUp;
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    viewModelSubscription = viewModel.observe().subscribe(new Action1<TextViewModel>() {
      @Override
      public void call(TextViewModel value) {
        bind(value);
      }
    });
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (viewModelSubscription != null) {
      viewModelSubscription.unsubscribe();
    }
  }

  private void init() {
    if (isInEditMode()) {
      return;
    }

    ObjectCreator.getGraph().inject(this);

    gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
      private float downX;
      private float downY;

      @Override
      public boolean onDown(MotionEvent e) {
        downX = viewModel.getValue().x.getValue();
        downY = viewModel.getValue().y.getValue();
        return true;
      }

      @Override
      public boolean onScroll(MotionEvent downEvent,
                              MotionEvent moveEvent,
                              float distanceX,
                              float distanceY) {
        float offsetX = moveEvent.getRawX() - downEvent.getRawX();
        float offsetY = moveEvent.getRawY() - downEvent.getRawY();
        viewModel.getValue().x.setValue(downX + offsetX);
        viewModel.getValue().y.setValue(downY + offsetY);

        // TODO: Fix this EventBus pattern.
        eventBus.post(new ScrollEvent(CoolTextView.this, moveEvent));
        return true;
      }

      @Override
      public boolean onDoubleTap(MotionEvent e) {
        eventBus.post(viewModel.getValue());
        return true;
      }
    });

    setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
          onUp.onNext(event);

          // TODO: Fix this EventBus pattern.
          eventBus.post(new UpEvent(CoolTextView.this, event));
        }

        return gestureDetector.onTouchEvent(event);
      }
    });
  }

  private void bind(TextViewModel viewModel) {
    viewModel.text.observe().subscribe(new Action1<CharSequence>() {
      @Override
      public void call(CharSequence text) {
        setText(text);
      }
    });
    viewModel.getTypeface(getContext()).subscribe(new Action1<Typeface>() {
      @Override
      public void call(Typeface typeface) {
        setTypeface(typeface);
      }
    });
    viewModel.x.observe().subscribe(new Action1<Float>() {
      @Override
      public void call(Float x) {
        setTranslationX(x);
      }
    });
    viewModel.y.observe().subscribe(new Action1<Float>() {
      @Override
      public void call(Float y) {
        setTranslationY(y);
      }
    });
    viewModel.gravity.observe().subscribe(new Action1<Integer>() {
      @Override
      public void call(Integer gravity) {
        setGravity(gravity);
      }
    });
    viewModel.size.observe().subscribe(new Action1<Float>() {
      @Override
      public void call(Float size) {
        setTextSize(size);
      }
    });
  }

  public static class ScrollEvent {
    public final CoolTextView view;
    public final MotionEvent moveEvent;

    public ScrollEvent(@NonNull CoolTextView view, @NonNull MotionEvent moveEvent) {
      this.view = view;
      this.moveEvent = moveEvent;
    }
  }

  public static class UpEvent {
    public final CoolTextView view;
    public final MotionEvent moveEvent;

    public UpEvent(@NonNull CoolTextView view, @NonNull MotionEvent moveEvent) {
      this.view = view;
      this.moveEvent = moveEvent;
    }
  }
}