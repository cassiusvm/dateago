package br.eti.cvm.dateago.controller;

import br.eti.cvm.dateago.dto.DateAgoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/")
@ControllerAdvice
public class DateAgoController {

    @GetMapping(name="{pastdate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DateAgoDto> returnTimeAgoJson(@RequestParam("pastdate") String pastDateString) {
        Period dateAgo;
        StringBuilder timeAgoString = new StringBuilder();

        try {
            dateAgo = Period.between(LocalDate.parse(pastDateString), LocalDate.now());
        } catch (DateTimeParseException e) {
            return new ResponseEntity("{}", HttpStatus.BAD_REQUEST);
        }

        if(dateAgo.isNegative()) {
            return new ResponseEntity("{}", HttpStatus.BAD_REQUEST);
        }

        if(dateAgo.getYears() != 0) {
            timeAgoString.append(dateAgo.getYears());

            if(dateAgo.getYears() > 1)
                timeAgoString.append(" anos");
            else
                timeAgoString.append(" ano");

            if(dateAgo.getMonths() != 0)
                timeAgoString.append(", ");
        }
        if(dateAgo.getMonths() != 0) {
            timeAgoString.append(dateAgo.getMonths());

            if(dateAgo.getMonths() > 1)
                timeAgoString.append(" meses");
            else
                timeAgoString.append(" mês");

            if(dateAgo.getDays() != 0)
                timeAgoString.append(", ");
        }
        if(dateAgo.getDays() != 0) {
            timeAgoString.append(dateAgo.getDays());

            if(dateAgo.getDays() > 1)
                timeAgoString.append(" dias");
            else
                timeAgoString.append(" dia");
        }

        DateAgoDto dateAgoDto = new DateAgoDto();
        dateAgoDto.setDateAgo(timeAgoString.toString());

        return new ResponseEntity<>(dateAgoDto, HttpStatus.OK);
    }

}
