package com.umitcoban.budgetmatebackend.modules.auth.application;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Service
public class JwtTokenService {

    private final byte[] secret;
    private final long accessTokenValiditySeconds;

    public JwtTokenService(
            @Value("${app.security.jwt.secret}") String secret,
            @Value("${app.security.jwt.access-token-validity-seconds:3600}") long accessTokenValiditySeconds
    ) {
        this.secret = secret.getBytes();
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public String generateToken(Long userId, String email, Set<String> roles) throws JOSEException {
        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .claim("roles", String.join(",", roles))
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(accessTokenValiditySeconds)))
                .build();

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        SignedJWT signedJWT = new SignedJWT(header, claims);

        JWSSigner signer = new MACSigner(secret);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public SignedJWT parseAndValidate(String token) throws ParseException, JOSEException {
        SignedJWT jwt = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(secret);

        if (!jwt.verify(verifier)) {
            throw new JOSEException("Invalid signature");
        }

        Date exp = jwt.getJWTClaimsSet().getExpirationTime();
        if (exp == null || exp.before(new Date())) {
            throw new JOSEException("Token expired");
        }

        return jwt;
    }
}
