package br.com.iten.raspberry_awards.controller;

import br.com.iten.raspberry_awards.service.NomineeService;
import br.com.iten.raspberry_awards.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/nominees")
public class NomineeController {

    @Autowired
    private NomineeService nomineeService;

    @GetMapping(produces = "text/csv")
    @ResponseBody
    public void getAll(HttpServletResponse response) throws IOException {
        var nominees = nomineeService.getAll();
        response.setCharacterEncoding("UTF-8");
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
        String[] header = Constants.CSV_HEADER;
        csvWriter.writeHeader(header);
        for (var nominee : nominees) {
            csvWriter.write(nominee, header);
        }
        csvWriter.close();
    }
}
