package by.epam.onlinetraining.service.util;


public class Validator {
    private static final String EMAIL_REGEXP = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private static final String PASSWORD_REGEXP = "^[a-zA-Z0-9]{4,20}";
    private static final String FIRST_NAME_REGEXP = "^[А-ЯA-Z][a-яa-z]{2,30}";
    private static final String LAST_NAME_REGEXP = "^[А-ЯA-Z][a-яa-z]{2,30}(-[А-ЯA-Z][a-яa-z]{2,30})?";

    public static boolean isEmailValid(String email) {
        return (email != null) && email.matches(EMAIL_REGEXP);
    }

    public static boolean isPasswordValid(String password) {
        return (password != null) && password.matches(PASSWORD_REGEXP);
    }

    public static boolean isPasswordRepeatValid(String password, String passwordRepeat) {
        boolean match = false;
        if (passwordRepeat != null && password != null) {
           return password.equals(passwordRepeat);
        }
        return match;
    }

    public static boolean isFirstNameValid(String firstName) {
        return (firstName != null) && firstName.matches(FIRST_NAME_REGEXP);
    }

    public static boolean isLastNameValid(String lastName) {
        return (lastName != null) && lastName.matches(LAST_NAME_REGEXP);
    }
}
