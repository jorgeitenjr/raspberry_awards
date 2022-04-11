package br.com.iten.raspberry_awards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProducerStatsDto {
    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

}
