package com.vitorraphael.gestor_comercial.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

/**
 * Faz o hash do PIN de login (SHA-256 + salt aleatório por funcionário).
 * Sem bcrypt/Spring Security aqui de propósito: um PIN curto não precisa de
 * um KDF lento, e evita puxar a dependência inteira do Spring Security só
 * por causa de uma função de hash.
 */
@Service
public class PinHashService {

    private static final SecureRandom RANDOM = new SecureRandom();

    public String gerarSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hash(String pin, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] hash = digest.digest(pin.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo de hash indisponível.", e);
        }
    }

    public boolean confere(String pin, String salt, String hashEsperado) {
        return hash(pin, salt).equals(hashEsperado);
    }
}
