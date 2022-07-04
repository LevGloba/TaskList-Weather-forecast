// Generated by view binder compiler. Do not edit!
package com.example.diplom.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.diplom.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AlertDialogBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton buttonNegativ;

  @NonNull
  public final ImageButton buttonPosistiv;

  @NonNull
  public final EditText editTask;

  @NonNull
  public final EditText editTitele;

  private AlertDialogBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton buttonNegativ,
      @NonNull ImageButton buttonPosistiv, @NonNull EditText editTask,
      @NonNull EditText editTitele) {
    this.rootView = rootView;
    this.buttonNegativ = buttonNegativ;
    this.buttonPosistiv = buttonPosistiv;
    this.editTask = editTask;
    this.editTitele = editTitele;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AlertDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AlertDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.alert_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AlertDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonNegativ;
      ImageButton buttonNegativ = ViewBindings.findChildViewById(rootView, id);
      if (buttonNegativ == null) {
        break missingId;
      }

      id = R.id.buttonPosistiv;
      ImageButton buttonPosistiv = ViewBindings.findChildViewById(rootView, id);
      if (buttonPosistiv == null) {
        break missingId;
      }

      id = R.id.editTask;
      EditText editTask = ViewBindings.findChildViewById(rootView, id);
      if (editTask == null) {
        break missingId;
      }

      id = R.id.editTitele;
      EditText editTitele = ViewBindings.findChildViewById(rootView, id);
      if (editTitele == null) {
        break missingId;
      }

      return new AlertDialogBinding((ConstraintLayout) rootView, buttonNegativ, buttonPosistiv,
          editTask, editTitele);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
