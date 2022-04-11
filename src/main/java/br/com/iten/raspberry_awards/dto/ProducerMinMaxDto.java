package br.com.iten.raspberry_awards.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ProducerMinMaxDto {

    private List<ProducerStatsDto> min = Collections.emptyList();
    private List<ProducerStatsDto> max = Collections.emptyList();
}
