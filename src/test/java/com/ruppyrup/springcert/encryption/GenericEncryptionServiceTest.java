package com.ruppyrup.springcert.encryption;

import com.ruppyrup.encryption.GenericEncryptionService;
import com.ruppyrup.encryption.IEncryptionService;
import com.ruppyrup.springcert.model.Credential;
import com.ruppyrup.springcert.model.CredentialDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GenericEncryptionServiceTest {
    IEncryptionService<Credential> genericEncryptionService = new GenericEncryptionService<>("secrete", "Blowfish");;

    CredentialDTO credentialDTO1 = new CredentialDTO("Amazon", "www.amazon.com", "ruppyrup", "monkey");
    CredentialDTO credentialDTO2 = new CredentialDTO("Amazon", "www.amazon.com", "ruppyrup", "monkey");
    Credential credential1 = new Credential(credentialDTO1);


    @Test
    void encryptShouldEncryptObjectFields() {
        Credential encryptedObject = genericEncryptionService.encrypt(credential1);
        Assertions.assertThat(encryptedObject.getPassword()).isNotEqualTo("monkey");
        Assertions.assertThat(encryptedObject.getLogin()).isNotEqualTo("ruppyrup");
        Assertions.assertThat(encryptedObject.getCredentialName()).isEqualTo("Amazon");
        Assertions.assertThat(encryptedObject.getUrl()).isEqualTo("www.amazon.com");
        Credential decryptedObject = genericEncryptionService.decrypt(encryptedObject);
        Assertions.assertThat(credentialDTO2).isEqualTo(new CredentialDTO(decryptedObject));
    }
}