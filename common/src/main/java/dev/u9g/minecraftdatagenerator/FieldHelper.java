package dev.u9g.minecraftdatagenerator;

import java.lang.reflect.Field;

public class FieldHelper {
    public static Field findStaticFieldWithValue(Class<?> clazz, Object value) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(null).equals(value)) {
                    return field;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }


        throw new IllegalArgumentException("No field with value " + value + " found in " + clazz);
    }
}
