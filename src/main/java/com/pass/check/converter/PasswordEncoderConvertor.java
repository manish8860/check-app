package com.pass.check.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PasswordEncoderConvertor implements AttributeConverter<String, String> {

    private PasswordEncoder passwordEncoder;

    public PasswordEncoderConvertor() {
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return passwordEncoder.encode(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
