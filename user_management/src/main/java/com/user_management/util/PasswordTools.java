package com.user_management.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordTools {
	private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final int KEY_LENGTH_BITS = 160;
	private static final int ITERATION_COUNT = 65536;

	private static final String RANDOM_NUMBER_GENERATOR_ALGORITHM = "SHA1PRNG";
	private static final int SALT_LENGTH_BYTES = 32;

	public static byte[] generatePasswordHash(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
		final KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH_BITS);

		final SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

		return secretKey.getEncoded();
	}

	public static byte[] generateSalt() throws NoSuchAlgorithmException {
		final SecureRandom random = SecureRandom.getInstance(RANDOM_NUMBER_GENERATOR_ALGORITHM);
		final byte[] salt = new byte[SALT_LENGTH_BYTES];
		random.nextBytes(salt);
		return salt;
	}

}
