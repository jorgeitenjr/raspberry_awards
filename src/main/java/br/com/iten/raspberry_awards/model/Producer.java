package br.com.iten.raspberry_awards.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "producer")
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id", "name"})
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name", unique = true)
    private String name;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "nominee_producers",
            joinColumns = @JoinColumn(name = "producer_id"),
            inverseJoinColumns = @JoinColumn(name = "nominee_id")
    )
    private Set<Nominee> nomineeList;

    public Producer(String name) {
        this.name = name;
    }

    public Producer() {

    }
}
