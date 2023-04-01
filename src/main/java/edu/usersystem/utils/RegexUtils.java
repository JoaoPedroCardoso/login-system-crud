package edu.usersystem.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexUtils {

    public static String REGEX_SENHA = "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]\\{\\};':\\\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$";

    public static String REGEX_EMAIL = "(?im)^(?<c1>\"?)\\w+(?:[\\W&&[^@]]?)\\w+\\k<c1>@(?:\\[?(?:\\d{3}\\.?){0,4}\\]?|(?:[\\w&&[^\\d]]+[\\.\\-]?)*(?<=\\.\\w{2,6}))$";

}
