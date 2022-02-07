package org.example.test.converter;

import lombok.Synchronized;
import org.example.test.database.entities.Madrasah;
import org.example.test.dto.MadrasahDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MadrasahToMadrasahDTO implements Converter<Madrasah, MadrasahDTO> {

    private ModelMapper modelMapper;

    protected MadrasahToMadrasahDTO() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Synchronized
    @Override
    public MadrasahDTO convert(Madrasah madrasah) {
        return modelMapper.map(madrasah, MadrasahDTO.class);
    }

    @Synchronized
    @Override
    public List<MadrasahDTO> convert(List<Madrasah> e) {
        if (e.isEmpty()) {
            return Collections.emptyList();
        }
        return e.stream().map(this::convert).collect(Collectors.toList());
    }
}
