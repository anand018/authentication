package com.simlearn.authentication.constants;

public class ApplicatiopnConstants {

    public static final int ATTEMPTS_LIMIT = 5;
    public static final int ZERO = 0;
    public static final int COOLING_PERIOD = 1800;
    public static final long OTP_VALIDITY_DURATION = 30 * 60 * 1000;
    public static final String ADMIN = "ADMIN";
    public static final String INCORRECT_USERNAME_PASSWORD = "Username or password is incorrect";
    public static final String NO_ACCESS_TO_LOGIN = "Do not have the right access";
    public static final String EXCEEDED_ATTEMPTS = "You have exceeded maximum number of login attempts. Try after 30 minutes";
    public static final String LOGIN_SUCCESS = "Successfully logged in for user: ";
    public static final String FAILED_TO_LOGIN = "Login failed for user: ";
    public static final String FAILURE_REASON = " Failure Reason: ";
    public static final String INSTRUCTOR = "INSTRUCTOR";
    public static final String EMAIL_SUBJECT = "[Simlearn] Authenticate Your Email";
    public static final String EMAIL_MESSAGE = "This code is valid for 30 minutes. OTP for authentication is: ";
    public static final String OTP_SUCCESS = "OTP is sent to email: ";
    public static final String OTP_FAILURE = "Failed to send OTP to email: ";
    public static final String OTP_INVALID = "Invalid OTP";

}
