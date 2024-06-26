package io.cordova.ifb.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MyTextWatcher implements TextWatcher {
    private EditText editText;

    public MyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        int position = (int) editText.getTag();
        // Do whatever you want with position
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text=editText.getText().toString();

    }
}
