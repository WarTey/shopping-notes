package com.guillaume.shoppingnotes.tools;

import android.support.design.widget.TextInputLayout;

import java.util.regex.Pattern;

public class CredentialsVerification {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private static boolean checkEmail(String email) { return pattern.matcher(email).matches(); }

    private static boolean emptyTextView(TextInputLayout str) { return str.getEditText().getText().toString().trim().isEmpty(); }

    private static void showError(TextInputLayout field, String message) {
        field.setError(message);
        field.requestFocus();
    }

    public static boolean registerVerification(TextInputLayout inputLastname, TextInputLayout inputFirstname, TextInputLayout inputEmail, TextInputLayout inputPassword, TextInputLayout inputPasswordRepeat) {
        String txtLastname = inputLastname.getEditText().getText().toString().trim();
        String txtFirstname = inputFirstname.getEditText().getText().toString().trim();
        String txtEmail = inputEmail.getEditText().getText().toString().trim();
        String txtPassword = inputPassword.getEditText().getText().toString().trim();
        String txtPasswordRepeat = inputPasswordRepeat.getEditText().getText().toString().trim();

        if (!txtLastname.isEmpty() && !txtFirstname.isEmpty() && !txtEmail.isEmpty() && !txtPassword.isEmpty() && txtPassword.equals(txtPasswordRepeat)) {
            if (checkEmail(txtEmail))
                return true;
            showError(inputEmail, "Incorrect email format");
            return false;
        }
        if (!txtPassword.equals(txtPasswordRepeat))
            showError(inputPasswordRepeat, "Your password must be identical");
        for (TextInputLayout field : new TextInputLayout[]{inputPasswordRepeat, inputPassword, inputEmail, inputFirstname, inputLastname})
            if (emptyTextView(field))
                showError(field, "This field cannot be empty");
        return false;
    }

    public static void registerFailed(TextInputLayout inputEmail) {
        if (!emptyTextView(inputEmail) && checkEmail(inputEmail.getEditText().getText().toString().trim()))
            showError(inputEmail, "This email already exist.");
    }

    public static boolean loginVerification(TextInputLayout inputEmail, TextInputLayout inputPassword) {
        if (emptyTextView(inputEmail) || emptyTextView(inputPassword)) {
            if (emptyTextView(inputPassword))
                showError(inputPassword, "This field cannot be empty");
            if (emptyTextView(inputEmail))
                showError(inputEmail, "This field cannot be empty");
            return false;
        }
        return true;
    }

    public static void loginFailed(TextInputLayout inputEmail, TextInputLayout intputPassword) {
        if (!emptyTextView(inputEmail) && !emptyTextView(intputPassword)) {
            showError(intputPassword, "Invalid password");
            showError(inputEmail, "Invalid email");
        }
    }
}
