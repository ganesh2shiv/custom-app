package com.core.app.rule;

import android.text.TextUtils;
import android.util.Patterns;

import com.mobsandgeeks.saripaar.AnnotationRule;

class EmailRule extends AnnotationRule<Email, String> {

    protected EmailRule(Email email) {
        super(email);
    }

    @Override
    public boolean isValid(final String input) {

        if (TextUtils.isEmpty(input)) {
            return false;
        }

        String email = input.trim();

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}