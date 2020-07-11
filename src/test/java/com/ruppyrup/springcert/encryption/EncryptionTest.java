package com.ruppyrup.springcert.encryption;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EncryptionTest {

    @Autowired
    EncryptionService encryptionService;

    @Test
    void encryptionAndThenDecryptionShouldReturnSameString() {
        String password = "tinytom";
        String data = "this is the secrete";

        String encryptedData = encryptionService.encrypt(data, password);
        String decryptedData = encryptionService.decrypt(encryptedData, password);

        Assertions.assertThat(decryptedData).isEqualTo(data);
    }
}