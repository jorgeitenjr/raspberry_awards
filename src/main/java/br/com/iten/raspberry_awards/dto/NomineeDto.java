package br.com.iten.raspberry_awards.dto;

import lombok.Data;

@Data
public class NomineeDto {

    private Integer year;
    private String title;
    private String studios;
    private String producers;
    private String winner;
}
