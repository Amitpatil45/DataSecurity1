package com.datasecurity.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datasecurity.entity.AadharCard;
import com.datasecurity.entity.AadharCardDTO;
import com.datasecurity.entity.AadharCardEntity;
import com.datasecurity.repo.AadharCardEntityRepo;
import com.datasecurity.repo.AadharCardRepository;

@Service
public class AadharCardService {
	@Autowired
    private AadharCardRepository aadharCardRepository;
	
	@Autowired
	private AadharCardEntityRepo aadharCardEntityRepo;
    // Simulated keys (replace with proper key management)
    private static KeyPair rsaKeyPair;
    private static SecretKey macSecretKey;

    static {
        try {
            // Generate RSA key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            rsaKeyPair = keyGen.generateKeyPair();

            // Generate MAC secret key
            KeyGenerator keyGenMac = KeyGenerator.getInstance("HmacSHA256");
            macSecretKey = keyGenMac.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 

    private String rsaEncrypt(AadharCardDTO aadharCardDTO) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaKeyPair.getPublic());
        byte[] encryptedBytes = cipher.doFinal(aadharCardDTO.toString().getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private String generateMAC(String data) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(macSecretKey);
        byte[] macBytes = hmac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(macBytes);
    }
    
    @Transactional
    public void saveAadharCard(AadharCardDTO aadharCardDTO) {
        try {
            // Convert AadharCardDTO to entity
            AadharCardEntity aadharCardEntity = new AadharCardEntity();

            // Encrypt sensitive data with RSA
            String encryptedData = rsaEncrypt(aadharCardDTO);
            aadharCardEntity.setEncryptedData(encryptedData);

            // Generate MAC for data integrity
            String mac = generateMAC(encryptedData);
            aadharCardEntity.setMac(mac);

            // Save AadharCard entity with encrypted data and MAC
            aadharCardRepository.save(aadharCardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }
    
    public List<AadharCardEntity> getAllAadharCards() {
        return aadharCardEntityRepo.findAll();
    }

  
    
}
