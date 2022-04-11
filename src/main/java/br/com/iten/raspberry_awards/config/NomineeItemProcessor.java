package br.com.iten.raspberry_awards.config;

import br.com.iten.raspberry_awards.dto.NomineeDto;
import br.com.iten.raspberry_awards.model.Nominee;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NomineeItemProcessor implements ItemProcessor<NomineeDto, Nominee> {


    private static final Logger log = LoggerFactory.getLogger(NomineeItemProcessor.class);

    @Override
    public Nominee process(final NomineeDto nominee) {
        final var year = nominee.getYear();
        final var title = nominee.getTitle();
        final var studios = nominee.getStudios();
        final var winner = nominee.getWinner();

        final var transformedNominee = new Nominee();
        transformedNominee.setYear(year);
        transformedNominee.setTitle(title);
        transformedNominee.setStudios(studios);
        transformedNominee.setProducers(nominee.getProducers());
        transformedNominee.setWinner(StringUtils.defaultIfBlank(winner, StringUtils.EMPTY).equalsIgnoreCase("yes"));

        log.debug("Converting (" + nominee + ") into (" + transformedNominee + ")");

        return transformedNominee;
    }

    public List<String> processProducers(String producers) {
        producers = StringUtils.defaultIfBlank(producers, StringUtils.EMPTY);
        return
                Stream.of(producers.split(", and"))
                        .map(name -> name.split(", | and"))
                        .flatMap(Stream::of)
                        .map(name -> name.trim())
                        .collect(Collectors.toCollection(ArrayList<String>::new));
    }

}