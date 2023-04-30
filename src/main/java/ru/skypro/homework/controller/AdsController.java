package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ads;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    @PostMapping
    public Ads addAd(@RequestBody Ads ads) {
        return new Ads();
    }

    @GetMapping
    public Ads getAllAds(@RequestBody Ads ads) {
        return new Ads();
    }

    @PostMapping("/{id}/commeents")
    public Ads addComments(@RequestBody Ads ads) {
        return new Ads();
    }

    @GetMapping("/{id}/commeents")
    public Ads getComments(@RequestBody Ads ads) {
        return new Ads();
    }

    @GetMapping("/{id}")
    public Ads getAds(@RequestBody Ads ads) {
        return new Ads();
    }

    @DeleteMapping("/{id}")
    public Ads removeAd(@RequestBody Ads ads) {
        return new Ads();
    }
}
