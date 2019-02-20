package com.core.app.rule;

import android.text.TextUtils;

import com.mobsandgeeks.saripaar.AnnotationRule;

class PhoneRule extends AnnotationRule<Phone, String> {

    protected PhoneRule(Phone phone) {
        super(phone);
    }

    @Override
    public boolean isValid(final String input) {

        if (TextUtils.isEmpty(input)) {
            return false;
        }

        String phone = input.trim();

        return TextUtils.isDigitsOnly(phone) && phone.length() == 10;
    }
}