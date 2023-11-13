package com.chen.srs;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.SQLException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;


public class MyDataSource {

    public static Connection getConnection() throws NamingException, SQLException {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:jboss/datasources/H2_784_JNDI");
        Connection conn = (Connection)ds.getConnection();
        return conn;
    }

    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /*public static String getPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt=getSalt();
        StringBuilder saltString=new StringBuilder();
        for(int i=0; i<salt.length; i++){
            saltString.append(String.valueOf(salt[i]));
            if(i<salt.length-1) saltString.append(",");
        }

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        StringBuilder hashString=new StringBuilder();
        for(int i=0; i<hash.length; i++){
            hashString.append(String.valueOf(hash[i]));
            if(i<hash.length-1) saltString.append(",");
        }

        String password_hash= saltString+"$"+hashString;
        return password_hash;
    }*/

    public static String getPasswordHash(byte[] salt,String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(password==null || password.isEmpty())throw new InvalidKeySpecException();
        StringBuilder saltString=new StringBuilder();
        for(int i=0; i<salt.length; i++){
            saltString.append(String.valueOf(salt[i]));
            if(i<salt.length-1) saltString.append("%");
        }

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        StringBuilder hashString=new StringBuilder();
        for(int i=0; i<hash.length; i++){
            if(hash[i]<0)hash[i]=(byte)-hash[i];
            hashString.append(String.valueOf(hash[i]));
        }

        String passwordHash= saltString+"$"+hashString;
        return passwordHash;
    }

    public static boolean checkPasswordHash(String password_hash,String password) throws SQLException, NamingException, NoSuchAlgorithmException, InvalidKeySpecException {
        String[] passwordHashToArr=password_hash.split("\\$");
        String saltString=passwordHashToArr[0];
        String[] saltStringToArr=saltString.split("%");
        byte[] salt=new byte[saltStringToArr.length];
        for(int i=0; i<saltStringToArr.length; i++){
            salt[i]=(byte)Integer.parseInt(saltStringToArr[i]);
        }
        String computedHash=getPasswordHash(salt,password);
        return password_hash.equals(computedHash);
    }


}
