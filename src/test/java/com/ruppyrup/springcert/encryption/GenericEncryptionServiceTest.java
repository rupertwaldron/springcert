package com.ruppyrup.springcert.encryption;

import com.ruppyrup.springcert.model.CredentialDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GenericEncryptionServiceTest {

    @Autowired
    IEncryptionService<CredentialDTO> genericEncryptionService;

    CredentialDTO credential1 = new CredentialDTO("Amazon", "www.amazon.com", "ruppyrup", "monkey");
    CredentialDTO credential2 = new CredentialDTO("Amazon", "www.amazon.com", "ruppyrup", "monkey");

    @Test
    void encryptShouldEncryptObjectFields() {
        CredentialDTO encryptedObject = genericEncryptionService.encrypt(credential1);
        Assertions.assertThat(encryptedObject.getPassword()).isNotEqualTo("monkey");
        Assertions.assertThat(encryptedObject.getLogin()).isNotEqualTo("ruppyrup");
        Assertions.assertThat(encryptedObject.getCredentialName()).isEqualTo("Amazon");
        Assertions.assertThat(encryptedObject.getUrl()).isEqualTo("www.amazon.com");
        CredentialDTO decryptedObject = genericEncryptionService.decrypt(encryptedObject);
        Assertions.assertThat(credential2).isEqualTo(decryptedObject);
    }

    @Test
    void decrypt() {
    }
}