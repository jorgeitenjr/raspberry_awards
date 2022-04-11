package br.com.iten.raspberry_awards.config;

import br.com.iten.raspberry_awards.dto.NomineeDto;
import br.com.iten.raspberry_awards.model.Nominee;
import br.com.iten.raspberry_awards.utils.Constants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    EntityManagerFactory emf;

    @Value("${file.to.import}")
    private String fileToImport;

    @Bean
    public FlatFileItemReader<NomineeDto> reader() {
        return new FlatFileItemReaderBuilder<NomineeDto>()
                .name("NomineeItemReader")
                .resource(new ClassPathResource(fileToImport))
                .linesToSkip(1)
                .encoding("UTF-8")
                .delimited()
                .delimiter(Constants.DELIMITER)
                .names(Constants.CSV_HEADER)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(NomineeDto.class);
                }})
                .build();
    }

    @Bean
    public NomineeItemProcessor processor() {
        return new NomineeItemProcessor();
    }

    @Bean
    public JpaItemWriter<Nominee> writer() {
        return new JpaItemWriterBuilder<Nominee>()
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    public Job importNomineeJob(JobCompletionNotificationListener listener, Step stepImport) {
        return jobBuilderFactory.get("importNomineeJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepImport)
                .end()
                .build();
    }

    @Bean
    public Step stepImport() {
        return stepBuilderFactory.get("stepImport")
                .<NomineeDto, Nominee>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }


}