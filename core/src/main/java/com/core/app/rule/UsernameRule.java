package com.core.app.rule;

import android.text.TextUtils;
import android.util.Patterns;

import com.mobsandgeeks.saripaar.AnnotationRule;

class UsernameRule extends AnnotationRule<Username, String> {

    protected UsernameRule(Username username) {
        super(username);
    }

    @Override
    public boolean isValid(final String username) {

        if (TextUtils.isEmpty(username)) {
            return false;
        }

        String userName = username.trim();

        if (TextUtils.isDigitsOnly(userName)) {
            return userName.length() == 10;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(userName).matches();
        }
    }
}