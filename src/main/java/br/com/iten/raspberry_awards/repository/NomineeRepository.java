package br.com.iten.raspberry_awards.repository;

import br.com.iten.raspberry_awards.model.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NomineeRepository extends JpaRepository<Nominee, Long> {
    List<Nominee> findByWinnerIsTrue();
}