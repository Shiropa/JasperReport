package org.example.test.converter;

import lombok.Synchronized;
import org.example.test.database.entities.Registration;
import org.example.test.dto.RegistrationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistrationToRegistrationDTO implements Converter<Registration, RegistrationDTO> {

    private ModelMapper modelMapper;

    protected RegistrationToRegistrationDTO() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Synchronized
    @Override
    public RegistrationDTO convert(Registration registration) {
        return modelMapper.map(registration, RegistrationDTO.class);
    }

    @Synchronized
    @Override
    public List<RegistrationDTO> convert(List<Registration> e) {
        if (e.isEmpty()) {
            return Collections.emptyList();
        }
        return e.stream().map(this::convert).collect(Collectors.toList());
    }
}
