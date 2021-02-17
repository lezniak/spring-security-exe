package com.example.demo;

import com.example.demo.object.quotation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class apiController {

    private List<quotation>  quotations;

    public apiController(){
        this.quotations = new ArrayList<>();
        quotations.add(new quotation( "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma –" +
                                             " which is living with the results of other people's thinking","Steve Jobs"));
        quotations.add(new quotation("Whoever is happy will make others happy too.","Anne frank"));
    }
    @GetMapping("/api")
    public List<quotation> readQuotation() {
        return this.quotations;
    }

    @PostMapping("/api")
    public void addQuotation(@RequestBody quotation quotation) {
        this.quotations.add(quotation);
    }

    @DeleteMapping("/api")
    public void deleteQuotation(@RequestParam int deleteIndex) {
        this.quotations.remove(deleteIndex);
    }
}
