package br.com.iten.raspberry_awards.service;

import br.com.iten.raspberry_awards.dto.ProducerMinMaxDto;
import br.com.iten.raspberry_awards.dto.ProducerStatsDto;
import br.com.iten.raspberry_awards.dto.ProducerWinnerDto;
import br.com.iten.raspberry_awards.repository.NomineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProducerService {


    private final NomineeRepository nomineeRepository;

    public ProducerService(
            @Autowired NomineeRepository nomineeRepository
    ) {
        this.nomineeRepository = nomineeRepository;
    }


    public ProducerMinMaxDto getWinners() {
        var producerStatsDto = new ProducerMinMaxDto();
        List<ProducerStatsDto> results = getAllWinners();
        if (!results.isEmpty()) {
            var groupedByInterval = results.stream()
                    .collect(Collectors.groupingBy(producerStats -> producerStats.getInterval()));
            var minInterval = groupedByInterval.keySet().stream().min(Integer::compareTo);
            var minIntervalList = groupedByInterval.get(minInterval.orElseThrow());
            minIntervalList.sort(Comparator.comparing(ProducerStatsDto::getPreviousWin));
            var maxInterval = groupedByInterval.keySet().stream().max(Integer::compareTo);
            var maxIntervalList = groupedByInterval.get(maxInterval.orElseThrow());
            maxIntervalList.sort(Comparator.comparing(ProducerStatsDto::getPreviousWin));
            producerStatsDto.setMin(minIntervalList);
            producerStatsDto.setMax(maxIntervalList);
        }
        return producerStatsDto;
    }

    private List<ProducerStatsDto> getAllWinners() {
        return nomineeRepository.findByWinnerIsTrue().stream().map(nominee ->
                        Arrays.stream(nominee.getProducers().split(", and"))
                                .map(name -> name.split(", | and"))
                                .flatMap(Stream::of)
                                .map(name -> name.trim())
                                .map(producer -> new ProducerWinnerDto(producer, nominee.getYear()))
                )
                .flatMap(l -> l)
                .collect(Collectors.groupingBy(producerStats -> producerStats.getName()))
                .entrySet().stream().
                filter(entry -> entry.getValue().size() > 1).
                map(entry -> {
                    var list = entry.getValue().stream().map(producerWinnerDto -> producerWinnerDto.getYear()).collect(Collectors.toList());
                    var min = list.stream().min(Integer::compareTo).orElseThrow();
                    var max = list.stream().max(Integer::compareTo).orElseThrow();
                    var interval = max - min;
                    var producer = new ProducerStatsDto(entry.getKey(), interval, min, max);
                    return producer;
                }).collect(Collectors.toList());
    }
}
