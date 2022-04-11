package br.com.iten.raspberry_awards.config;

import br.com.iten.raspberry_awards.model.Nominee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Import Finished");

            jdbcTemplate.query("SELECT id, year, title, studios, winner FROM nominee",
                    (rs, row) -> new Nominee(
                            rs.getLong(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getBoolean(5)
                    )
            ).forEach(nominee -> log.debug("Found <" + nominee + "> in the database."));
        }
    }
}