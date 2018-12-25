package edu.epam.audio.model.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Runner {
    public static void main(String[] args) {
        String e = DigestUtils.md5Hex("qwerty");
        System.out.println(e);
    }
}
