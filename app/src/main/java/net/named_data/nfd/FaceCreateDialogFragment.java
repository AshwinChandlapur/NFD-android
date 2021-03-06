/* -*- Mode:jde; c-file-style:"gnu"; indent-tabs-mode:nil; -*- */
/*
 * Copyright (c) 2015-2019 Regents of the University of California
 * <p/>
 * This file is part of NFD (Named Data Networking Forwarding Daemon) Android.
 * See AUTHORS.md for complete list of NFD Android authors and contributors.
 * <p/>
 * NFD Android is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * NFD Android is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * NFD Android, e.g., in COPYING.md file.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.named_data.nfd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FaceCreateDialogFragment extends DialogFragment {
  public static interface OnFaceCreateRequested {
    public void
    createFace(String faceUri, boolean isPermanent);
  }

  public static FaceCreateDialogFragment
  newInstance() {
    return new FaceCreateDialogFragment();
  }

  @NonNull @Override
  public Dialog
  onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    builder
      .setView(inflater.inflate(R.layout.dialog_create_face, null))
      .setPositiveButton(R.string.face_add_dialog_create_face, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id)
          {
            EditText uriBox = (EditText) getDialog().findViewById(R.id.faceUri);
            String uri = uriBox.getText().toString();
            CheckBox permanent = (CheckBox) getDialog().findViewById(R.id.permanent);
            ((OnFaceCreateRequested) getTargetFragment()).createFace(uri, permanent.isChecked());
          }
        })
      .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id)
          {
            FaceCreateDialogFragment.this.getDialog().cancel();
          }
        })
    ;

    Dialog faceCreateDialog = builder.create();
    faceCreateDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    return faceCreateDialog;
  }
}
