package com.wang.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author: jyw
 * @date: 2022/7/10 12:11
 * @description:
 */
public final class PasswordEncoderFactories {

    private PasswordEncoderFactories() {
    }

    /**
     * Creates a {@link DelegatingPasswordEncoder} with default mappings. Additional
     * mappings may be added and the encoding will be updated to conform with best
     * practices. However, due to the nature of {@link DelegatingPasswordEncoder} the
     * updates should not impact users. The mappings current are:
     *
     * <ul>
     * <li>bcrypt - {@link BCryptPasswordEncoder} (Also used for encoding)</li>
     * <li>ldap -
     * {@link org.springframework.security.crypto.password.LdapShaPasswordEncoder}</li>
     * <li>MD4 -
     * {@link org.springframework.security.crypto.password.Md4PasswordEncoder}</li>
     * <li>MD5 - {@code new MessageDigestPasswordEncoder("MD5")}</li>
     * <li>noop -
     * {@link org.springframework.security.crypto.password.NoOpPasswordEncoder}</li>
     * <li>pbkdf2 - {@link Pbkdf2PasswordEncoder}</li>
     * <li>scrypt - {@link SCryptPasswordEncoder}</li>
     * <li>SHA-1 - {@code new MessageDigestPasswordEncoder("SHA-1")}</li>
     * <li>SHA-256 - {@code new MessageDigestPasswordEncoder("SHA-256")}</li>
     * <li>sha256 -
     * {@link org.springframework.security.crypto.password.StandardPasswordEncoder}</li>
     * <li>argon2 - {@link Argon2PasswordEncoder}</li>
     * </ul>
     * @return the {@link PasswordEncoder} to use
     */
    @SuppressWarnings("deprecation")
    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
        encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256",
                new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());
        encoders.put("GM", new CustomPasswordEncoder());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}
