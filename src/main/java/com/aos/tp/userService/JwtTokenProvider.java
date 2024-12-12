//package com.aos.tp.userService;
//
//import org.springframework.stereotype.Component;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//
//    private final String secretKey = "MySecretKey"; // Clé secrète pour signer les tokens
//    private final long validityInMilliseconds = 3600000; // Durée de validité du token (1 heure)
//
//    // Générer un JWT simple
//    public String generateToken(String username) {
//        long now = System.currentTimeMillis();
//        long expiry = now + validityInMilliseconds;
//
//        // Payload du token
//        String payload = "{\"sub\":\"" + username + "\",\"iat\":" + now + ",\"exp\":" + expiry + "}";
//
//        // Encodage Base64 pour l'en-tête et le payload
//        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
//        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());
//
//        // Signature HMAC-SHA256
//        String signature = sign(header + "." + encodedPayload, secretKey);
//
//        // Retourne le token complet
//        return header + "." + encodedPayload + "." + signature;
//    }
//
//    // Valider un JWT
//    public boolean validateToken(String token) {
//        try {
//            String[] parts = token.split("\\.");
//            if (parts.length != 3) return false;
//
//            String header = parts[0];
//            String payload = parts[1];
//            String signature = parts[2];
//
//            // Vérifie la signature
//            String expectedSignature = sign(header + "." + payload, secretKey);
//            if (!expectedSignature.equals(signature)) return false;
//
//            // Vérifie l'expiration
//            String decodedPayload = new String(Base64.getDecoder().decode(payload));
//            long expiry = Long.parseLong(decodedPayload.replaceAll(".*\"exp\":(\\d+).*", "$1"));
//            return expiry > System.currentTimeMillis();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // Extraire le nom d'utilisateur d'un JWT
//    public String getUsernameFromToken(String token) {
//        String[] parts = token.split("\\.");
//        if (parts.length != 3) throw new IllegalArgumentException("Token invalide");
//
//        String payload = new String(Base64.getDecoder().decode(parts[1]));
//        return payload.replaceAll(".*\"sub\":\"([^\"]+)\".*", "$1");
//    }
//
//    // Méthode pour signer avec HMAC-SHA256
//    private String sign(String data, String secret) {
//        try {
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
//            mac.init(secretKeySpec);
//            return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
//        } catch (Exception e) {
//            throw new RuntimeException("Erreur lors de la signature du token", e);
//        }
//    }
//}
