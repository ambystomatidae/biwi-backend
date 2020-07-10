package org.biwi.rest.services;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class ImageEncoderUtil {
    public static byte[] decode(String base64encodedImg) {
        byte[] decodedString = null;
        try {
            decodedString = Base64.getDecoder().decode(new String(base64encodedImg).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedString;
    }
}
