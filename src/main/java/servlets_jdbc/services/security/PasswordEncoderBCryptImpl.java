package servlets_jdbc.services.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncoderBCryptImpl implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return BCrypt.withDefaults()
                .hashToString(12, password.toCharArray());
    }

    @Override
    public boolean verify(String password, String passwordHashed) {
        BCrypt.Result result = BCrypt.verifyer()
                .verify(password.toCharArray(), passwordHashed);
        return result.verified;
    }
}
