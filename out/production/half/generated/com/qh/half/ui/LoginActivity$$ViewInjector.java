// Generated code from Butter Knife. Do not modify!
package com.qh.half.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LoginActivity$$ViewInjector {
  public static void inject(Finder finder, final com.qh.half.ui.LoginActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131296256);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296256' for field 'mSchool' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mSchool = (android.widget.ImageView) view;
    view = finder.findById(source, 2131296257);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296257' for field 'mWeibo' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mWeibo = (android.widget.ImageView) view;
    view = finder.findById(source, 2131296258);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296258' for field 'mDouban' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mDouban = (android.widget.ImageView) view;
    view = finder.findById(source, 2131296259);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131296259' for field 'mQq' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mQq = (android.widget.ImageView) view;
  }

  public static void reset(com.qh.half.ui.LoginActivity target) {
    target.mSchool = null;
    target.mWeibo = null;
    target.mDouban = null;
    target.mQq = null;
  }
}
