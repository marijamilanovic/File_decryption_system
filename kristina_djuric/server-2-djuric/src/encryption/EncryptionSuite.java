package encryption;

/* 
 * Copyright (C) msg-global solutions 2018
 * msg-global.com
 * All rights reserved
 */


public class EncryptionSuite {

    /**
     * Provides example on how to use {@link Encryptor} class 
     */
    public static void main(String[] args) {


        String key = "*SuperSecretKey*";
        String value = "Hello there, happy coding!";
        
        String encrypted = Encryptor.encrypt(key, value);
        System.out.println("Value: " + value + " encrypted: " + encrypted);
       
        String decrypted = Encryptor.decrypt(key, encrypted);
        System.out.println("Encryption: " + encrypted + " value: " + decrypted);
        
    }
}
