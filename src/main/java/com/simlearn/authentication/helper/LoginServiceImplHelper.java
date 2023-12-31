package com.simlearn.authentication.helper;

import static com.simlearn.authentication.constants.ApplicatiopnConstants.*;

import com.simlearn.authentication.exception.InvalidOTPException;
import com.simlearn.authentication.exception.OtpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Component
@Slf4j
public class LoginServiceImplHelper {

    private Map<String, OtpInfo> otpStorage = new HashMap<>();
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtp(String email) {
        String otpCode = generateOtp();
        sendOtpByEmail(email, otpCode);
        otpStorage.put(email, new OtpInfo(otpCode, System.currentTimeMillis()));
    }

    public boolean validateOtp(String email, String otpEntered) {
        OtpInfo otpInfo = otpStorage.get(email);
        boolean isValid = false;

        if (otpInfo != null) {
            // Check if the provided OTP matches the stored OTP. Valid (within a 30-minute window).
            if (otpEntered.equals(otpInfo.getOtpCode())) {
                long currentTime = System.currentTimeMillis();
                long otpCreationTime = otpInfo.getCreationTime();
                isValid = (currentTime - otpCreationTime) <= OTP_VALIDITY_DURATION;
            }
        }
        otpStorage.remove(email);
        if(isValid)
            return true;
        else
            throw new InvalidOTPException(OTP_INVALID);
    }

    private void sendOtpByEmail(String email, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(EMAIL_SUBJECT);
        message.setText(EMAIL_MESSAGE.concat(otpCode));
        try {
            javaMailSender.send(message);
            log.info(OTP_SUCCESS.concat(email));
        } catch (Exception e) {
            log.error(OTP_FAILURE.concat(email));
            throw new OtpException(OTP_FAILURE.concat(email));
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a random 6-digit number
        return String.valueOf(otp);
    }

    private static class OtpInfo {
        private final String otpCode;
        private final long creationTime;

        public OtpInfo(String otpCode, long creationTime) {
            this.otpCode = otpCode;
            this.creationTime = creationTime;
        }

        public String getOtpCode() {
            return otpCode;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }
}
