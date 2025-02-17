// Generated by view binder compiler. Do not edit!
package com.example.city_bus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.city_bus.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignUpBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout main;

  @NonNull
  public final Button signUpBtnDone;

  @NonNull
  public final EditText signUpEdtEmail;

  @NonNull
  public final EditText signUpEdtMobNum;

  @NonNull
  public final EditText signUpEdtName;

  @NonNull
  public final EditText signUpEdtPassword;

  private ActivitySignUpBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout main,
      @NonNull Button signUpBtnDone, @NonNull EditText signUpEdtEmail,
      @NonNull EditText signUpEdtMobNum, @NonNull EditText signUpEdtName,
      @NonNull EditText signUpEdtPassword) {
    this.rootView = rootView;
    this.main = main;
    this.signUpBtnDone = signUpBtnDone;
    this.signUpEdtEmail = signUpEdtEmail;
    this.signUpEdtMobNum = signUpEdtMobNum;
    this.signUpEdtName = signUpEdtName;
    this.signUpEdtPassword = signUpEdtPassword;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout main = (LinearLayout) rootView;

      id = R.id.signUpBtnDone;
      Button signUpBtnDone = ViewBindings.findChildViewById(rootView, id);
      if (signUpBtnDone == null) {
        break missingId;
      }

      id = R.id.signUpEdtEmail;
      EditText signUpEdtEmail = ViewBindings.findChildViewById(rootView, id);
      if (signUpEdtEmail == null) {
        break missingId;
      }

      id = R.id.signUpEdtMobNum;
      EditText signUpEdtMobNum = ViewBindings.findChildViewById(rootView, id);
      if (signUpEdtMobNum == null) {
        break missingId;
      }

      id = R.id.signUpEdtName;
      EditText signUpEdtName = ViewBindings.findChildViewById(rootView, id);
      if (signUpEdtName == null) {
        break missingId;
      }

      id = R.id.signUpEdtPassword;
      EditText signUpEdtPassword = ViewBindings.findChildViewById(rootView, id);
      if (signUpEdtPassword == null) {
        break missingId;
      }

      return new ActivitySignUpBinding((LinearLayout) rootView, main, signUpBtnDone, signUpEdtEmail,
          signUpEdtMobNum, signUpEdtName, signUpEdtPassword);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
