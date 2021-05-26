package com.example.devsocial.utils;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class DecodedToken {
    private String id;
    private String name;
    private int iat;
    private int exp;

    public static DecodedToken getDecoded(String encodedToken) {
        String[] pieces = encodedToken.split("\\.");
        String payload = pieces[1];
        String jsonString = new String(Base64.getDecoder().decode(payload));

        return new Gson().fromJson(jsonString, DecodedToken.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIat() {
        return iat;
    }

    public void setIat(int iat) {
        this.iat = iat;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
