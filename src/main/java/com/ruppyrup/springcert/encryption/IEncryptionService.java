package com.ruppyrup.springcert.encryption;

public interface IEncryptionService<T> {
    T encrypt(T data);

    T decrypt(T data);
}
