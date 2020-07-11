package com.ruppyrup.springcert.encryption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;

@Service
public class GenericEncryptionService<T> implements IEncryptionService<T> {

    @Autowired
    @Qualifier("stringEncryption")
    IEncryptionService stringEncryptionService;


    @Override
    public T encrypt(T data) {
        Field[] fields = data.getClass().getDeclaredFields();

        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Encrypt.class))
                .forEach(safeEncryptor(data));

        return data;
    }

    @Override
    public T decrypt(T data) {
        Field[] fields = data.getClass().getDeclaredFields();

        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Encrypt.class))
                .forEach(safeDencryptor(data));

        return data;
    }


    Consumer<Field> safeEncryptor(T data) {
        return field -> {
            try {
                field.setAccessible(true);
                field.set(data, stringEncryptionService.encrypt(field.get(data)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        };
    }

    Consumer<Field> safeDencryptor(T data) {
        return field -> {
            try {
                field.setAccessible(true);
                field.set(data, stringEncryptionService.decrypt(field.get(data)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        };
    }


}
