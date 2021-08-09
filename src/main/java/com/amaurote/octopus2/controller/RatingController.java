package com.amaurote.octopus2.controller;

import com.amaurote.octopus2.repository.RatingAggregateResults;
import com.amaurote.octopus2.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequestMapping
@Validated
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/rating")
    public ResponseEntity<?> getRating(@RequestParam @NotNull @NotBlank String catalogId) {
        var rating = ratingService.getRatingByCatalogId(catalogId);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @PostMapping("/rate")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> rate(@RequestParam @NotNull @NotBlank String catalogId,
                                  @RequestParam @NotNull @Min(1) @Max(5) Integer score,
                                  Principal principal) {
        RatingAggregateResults rating = null;
        if (principal != null) {
            ratingService.rateByCatalogId(catalogId, principal.getName(), score);
            rating = ratingService.getRatingByCatalogId(catalogId);
        }

        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @DeleteMapping("/rating")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> removeRating(@NotNull @NotBlank String catalogId, Principal principal) {
        RatingAggregateResults rating = null;
        if (principal != null) {
            ratingService.removeRating(catalogId, principal.getName());
            rating = ratingService.getRatingByCatalogId(catalogId);
        }

        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

}
