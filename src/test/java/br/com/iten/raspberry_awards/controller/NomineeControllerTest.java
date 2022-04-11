package br.com.iten.raspberry_awards.controller;

import br.com.iten.raspberry_awards.repository.NomineeRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NomineeControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private NomineeRepository nomineeRepository;
    @Value("${file.to.import}")
    private String fileToImport;

    @Test
    public void inputCsvShouldMatchOutputCsv()
            throws Exception {
        var stream = NomineeControllerTest.class.getResourceAsStream(String.format("/%s", fileToImport));
        var text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        MvcResult result = mvc.perform(get("/nominees")
                        .contentType("text/csv"))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andReturn();
        var content = StringUtils.removeEnd(result.getResponse().getContentAsString(), StringUtils.LF);
        Assertions.assertEquals(text, content);

    }
}
