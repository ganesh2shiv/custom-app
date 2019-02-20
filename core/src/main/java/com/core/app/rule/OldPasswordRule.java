package com.core.app.rule;

import com.mobsandgeeks.saripaar.ValidationContext;
import com.mobsandgeeks.saripaar.annotation.Password;

public class OldPasswordRule extends DiffValueRule<OldPassword, Password, String> {

    protected OldPasswordRule(final ValidationContext validationContext,
                              final OldPassword oldPassword) {
        super(validationContext, oldPassword, Password.class);
    }

    @Override
    public boolean isValid(final String confirmValue) {
        return super.isValid(confirmValue);
    }
}