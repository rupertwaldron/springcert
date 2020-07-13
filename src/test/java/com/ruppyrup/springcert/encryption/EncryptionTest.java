package com.ruppyrup.springcert.encryption;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EncryptionTest {

    @Autowired
    IEncryptionService<String> encryptionService;

    @Test
    void encryptionAndThenDecryptionShouldReturnSameString() throws IllegalAccessException, NoSuchFieldException {
        String data = "this is the secrete";

        String encryptedData = encryptionService.encrypt(data);
        String decryptedData = encryptionService.decrypt(encryptedData);

        Assertions.assertThat(decryptedData).isEqualTo(data);
    }
}