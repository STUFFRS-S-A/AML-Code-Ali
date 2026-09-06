package com.example.didit.app.util;

import java.text.MessageFormat;

public class ResponseCodes {
    public static final String Success = "200";
    public static final String AuthenticationError = "1000";
    public static final String TokenIsExpired = "1101";
    public static final String LoginFailed = "1001";
    public static final String UserIsLocked = "1002";
    public static final String UserIsDisabled = "1003";
    public static final String PasswordHasChangedPleaseLoginAgain = "1004";
    public static final String PasswordChangeRequiredByServer = "1005";
    public static final String PasswordAlreadyUsedRecently = "1006";
    public static final String UserRecordNotFound = "1007";
    public static final String CurrentPasswordNotMatch = "1008";
    public static final String PasswordSameAsCurrent = "1009";
    public static final String AdUser = "1010";

    public static String getMessage(String Code, Object... args) {
        String msg = null;
        switch (Code) {
            case ResponseCodes.Success:
                msg = "Successful";
                break;
            case ResponseCodes.AuthenticationError:
                msg = "Authentication Error";
                break;
            case ResponseCodes.TokenIsExpired:
                msg = "Token is Expired";
                break;
            case ResponseCodes.LoginFailed:
                msg = "Invalid username and password";
                break;
            case ResponseCodes.UserIsLocked:
                msg = "Account is locked due to too many failed login attempts, please contact to System Administrator.";
                break;
            case ResponseCodes.UserIsDisabled:
                msg = "Your Account is disabled, please contact to System Administrator.";
                break;
            case ResponseCodes.PasswordHasChangedPleaseLoginAgain:
                msg = "Your Password has been changed, please login again.";
                break;
            case ResponseCodes.PasswordChangeRequiredByServer:
                msg = "You need to change the Password, before performing any activity.";
                break;
            case ResponseCodes.PasswordAlreadyUsedRecently:
                msg = "You cannot reuse any of your last 3 passwords.";
                break;
            case ResponseCodes.UserRecordNotFound:
                msg = "User record is not found.";
                break;
            case ResponseCodes.CurrentPasswordNotMatch:
                msg = "Current password does not match.";
                break;
            case ResponseCodes.PasswordSameAsCurrent:
                msg = "New password is same as Current.";
                break;
            case ResponseCodes.AdUser:
                msg = "redirect AD User to SSO login";
                break;
        }

        return formatMessage(msg, args);
    }

    private static String formatMessage(String template, Object... args) {
        if (args == null || args.length == 0) {
            return template;
        }
        return MessageFormat.format(template, args);
    }
}
