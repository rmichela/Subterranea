package com.ryanmichela.subterranea;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Copyright 2013 Ryan Michela
 */
public class ReflectionUtil {
    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    public static <T> T getProtectedValue(Object obj, String field) {
        try {
            Class c = obj.getClass();
            Field f = c.getDeclaredField(field);
            f.setAccessible(true);
            return (T) f.get(obj);
        } catch (Exception ex) {
            System.out.println("*** " + ex);
            return null;
        }
    }
}
