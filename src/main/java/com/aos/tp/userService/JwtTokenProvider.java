package com.aos.tp.userService;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey = "MySecretKey";
    private final long validityInMilliseconds = 3600000;

    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        long expiry = now + validityInMilliseconds;

        String payload = "{\"sub\":\"" + username + "\",\"iat\":" + now + ",\"exp\":" + expiry + "}";

        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());

        String signature = sign(header + "." + encodedPayload, secretKey);

        return header + "." + encodedPayload + "." + signature;
    }

    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            String expectedSignature = sign(header + "." + payload, secretKey);
            if (!expectedSignature.equals(signature)) return false;

            String decodedPayload = new String(Base64.getDecoder().decode(payload));
            long expiry = Long.parseLong(decodedPayload.replaceAll(".*\"exp\":(\\d+).*", "$1"));
            return expiry > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid token");

        String payload = new String(Base64.getDecoder().decode(parts[1]));
        return payload.replaceAll(".*\"sub\":\"([^\"]+)\".*", "$1");
    }

    private String sign(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error signing the token", e);
        }
    }
}
