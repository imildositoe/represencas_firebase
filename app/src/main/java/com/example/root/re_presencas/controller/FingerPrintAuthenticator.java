package com.example.root.re_presencas.controller;

import android.annotation.TargetApi;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerPrintAuthenticator {

    private static final String KEY_NAME = "android.fingerprint";
    private static FingerPrintAuthenticator fingerPrintAuthenticator;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;

    private FingerPrintAuthenticator() {
        initAuthentication();
    }


    /**
     * Inicia as variaveis e gera a chave
     * */
    @TargetApi(Build.VERSION_CODES.M)
    public void initAuthentication() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            Log.e("I-KEYSTORE", keyStore.toString());

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            Log.e("I-KEYGENERATOR", keyGenerator.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);

            keyGenerator.init(new KeyGenParameterSpec
                    .Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            SecretKey key = keyGenerator.generateKey();

            Log.e("I-GENERATED_KEY", key.toString());

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" +
                    KeyProperties.ENCRYPTION_PADDING_PKCS7);

            Log.e("I-CIPHER-IV", Arrays.toString(cipher.getIV()));
            Log.e("I-CIPHER-TOSTRING", cipher.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);

            Log.e("I-SECRETKEY", key.toString());

            cipher.init(Cipher.ENCRYPT_MODE, key);

            Log.e("I-CIPHER-IV-AFTER-INIT", Arrays.toString(cipher.getIV()));
            Log.e("I-CIPHERSTRING-AFTERINI", cipher.toString());

            Log.e("I-SECRETKEY-AFTER-INIT", key.toString());

            return true;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static FingerPrintAuthenticator getInstanece() {
        if (fingerPrintAuthenticator == null) {
            fingerPrintAuthenticator = new FingerPrintAuthenticator();
        }
        return fingerPrintAuthenticator;
    }

    public Cipher getCipher() {
        return cipher;
    }
}
