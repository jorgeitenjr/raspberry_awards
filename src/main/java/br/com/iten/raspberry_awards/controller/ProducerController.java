package br.com.iten.raspberry_awards.controller;

import br.com.iten.raspberry_awards.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producers")
public class ProducerController {
    @Autowired
    private ProducerService producerService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAll() {
        var producerMinMaxDtoResponseEntity = producerService.getWinners();
        return new ResponseEntity<>(producerMinMaxDtoResponseEntity, HttpStatus.OK);
    }
}
