package br.com.iten.raspberry_awards.service;

import br.com.iten.raspberry_awards.dto.NomineeDto;
import br.com.iten.raspberry_awards.model.Nominee;
import br.com.iten.raspberry_awards.repository.NomineeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NomineeService {

    private final NomineeRepository nomineeRepository;
    private final ModelMapper modelMapper;

    public NomineeService(
            @Autowired NomineeRepository nomineeRepository
            , @Autowired ModelMapper modelMapper
    ) {
        this.nomineeRepository = nomineeRepository;
        this.modelMapper = modelMapper;
    }

    public List<NomineeDto> getAll() {
        return nomineeRepository.findAll().stream().map(nominee -> convertToDto(nominee)).collect(Collectors.toList());
    }

    private NomineeDto convertToDto(Nominee nominee) {
        var dto = modelMapper.map(nominee, NomineeDto.class);
        dto.setWinner(nominee.getWinner() ? "yes" : "");
        return dto;
    }
}
