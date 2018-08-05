package by.epam.onlinetraining.service.util;

public class ScriptSecurityChecker {

    public static final String SCRIPT_END_TAG = "</script>";
    public static final String SCRIPT_TAG = "<script>";
    public static final String SCRIPT_TAG_TO_UPPERCASE = "<SCRIPT>";
    public static final String SCRIPT_END_TAG_TO_UPPERCASE = "</SCRIPT>";
    public static final String REPLACEMENT_TAG = "(= ";
    public static final String REPLACEMENT_END_TAG = " =)";

    public static String scriptCheck(String textLine) {

        if (textLine.toLowerCase().contains(SCRIPT_TAG) || textLine.toLowerCase().contains(SCRIPT_END_TAG)) {
            textLine = textLine.replace(SCRIPT_TAG, REPLACEMENT_TAG);
            textLine = textLine.replace(SCRIPT_END_TAG, REPLACEMENT_END_TAG);
            textLine = textLine.replace(SCRIPT_TAG_TO_UPPERCASE, REPLACEMENT_TAG);
            textLine = textLine.replace(SCRIPT_END_TAG_TO_UPPERCASE, REPLACEMENT_END_TAG);
        }

        return textLine;
    }
}
