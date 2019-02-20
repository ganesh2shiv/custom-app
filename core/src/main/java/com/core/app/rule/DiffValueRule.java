package com.core.app.rule;

import android.view.View;

import com.mobsandgeeks.saripaar.ContextualAnnotationRule;
import com.mobsandgeeks.saripaar.ValidationContext;

import java.lang.annotation.Annotation;
import java.util.List;

class DiffValueRule<CONFIRM extends Annotation, SOURCE extends Annotation, DATA_TYPE>
        extends ContextualAnnotationRule<CONFIRM, DATA_TYPE> {

    private Class<SOURCE> mSourceClass;
    private Class<CONFIRM> mConfirmClass;

    DiffValueRule(final ValidationContext validationContext,
                  final CONFIRM confirmAnnotation,
                  final Class<SOURCE> sourceClass) {
        super(validationContext, confirmAnnotation);
        mSourceClass = sourceClass;
        mConfirmClass = (Class<CONFIRM>) confirmAnnotation.annotationType();
    }

    @Override
    public boolean isValid(final DATA_TYPE confirmValue) {
        List<View> sourceViews = mValidationContext.getAnnotatedViews(mSourceClass);
        int nSourceViews = sourceViews.size();

        if (nSourceViews == 0) {
            String message = String.format(
                    "You should have a view annotated with '%s' to use '%s'.",
                    mSourceClass.getName(), mConfirmClass.getName());
            throw new IllegalStateException(message);
        } else if (nSourceViews > 1) {
            String message = String.format(
                    "More than 1 field annotated with '%s'.", mSourceClass.getName());
            throw new IllegalStateException(message);
        }

        // There's only one, then we're good to go :)
        View view = sourceViews.get(0);
        Object sourceValue = mValidationContext.getData(view, mSourceClass);

        return !confirmValue.equals(sourceValue);
    }
}