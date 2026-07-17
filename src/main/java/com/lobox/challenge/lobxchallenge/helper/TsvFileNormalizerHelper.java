package com.lobox.challenge.lobxchallenge.helper;

public class TsvFileNormalizerHelper {

    private static final String NULL = "\\N";

    public static String normalizeString(String value){
        if (value==null || NULL.equals(value)){
            return null;
        } else {
            return value;
        }
    }

    public static Integer normalizeInteger(String value){
        String normilizedToString = normalizeString(value);
        if (normilizedToString==null){
            return null;
        }else {
            return Integer.parseInt(normilizedToString);
        }
    }

    public static boolean normalizedBooleanValue(String value) {
        return "1".equals(value);
    }

    public static String[] normalizeArray(String value){
        String normalizedToString = normalizeString(value);
        if (normalizedToString == null || normalizedToString.isBlank()){
            return new String[0];
        }
        return normalizedToString.split(",");
    }


}
