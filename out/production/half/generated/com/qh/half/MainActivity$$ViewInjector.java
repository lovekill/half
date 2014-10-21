// Generated code from Butter Knife. Do not modify!
package com.qh.half;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.qh.half.MainActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131034112);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034112' for field 'mCamaraPreViewLayout' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mCamaraPreViewLayout = (android.widget.FrameLayout) view;
    view = finder.findById(source, 2131034113);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034113' for field 'mButton' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mButton = (android.widget.Button) view;
  }

  public static void reset(com.qh.half.MainActivity target) {
    target.mCamaraPreViewLayout = null;
    target.mButton = null;
  }
}
