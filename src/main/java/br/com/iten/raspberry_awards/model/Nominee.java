package br.com.iten.raspberry_awards.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "nominee")
@ToString(exclude = {"id"})
@EqualsAndHashCode
public class Nominee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private String title;
    private String studios;
    private Boolean winner;
    private String producers;

    public Nominee(long id, int year, String title, String studios, boolean winner) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.winner = winner;
    }

    public Nominee() {

    }
}
