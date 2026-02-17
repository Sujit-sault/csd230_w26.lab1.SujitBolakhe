package csd230.lab1.controllers;

import csd230.lab1.entities.DiscMagEntity;
import csd230.lab1.repositories.DiscMagEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DiscMag REST API", description = "JSON API for managing disc magazines")
@RestController
@RequestMapping("/api/rest/discmags")
@CrossOrigin(origins = "*")
public class DiscMagRestController {

    private final DiscMagEntityRepository discMagRepository;

    public DiscMagRestController(DiscMagEntityRepository discMagRepository) {
        this.discMagRepository = discMagRepository;
    }

    @Operation(summary = "Get all disc magazines as JSON")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of disc magazines")
    @GetMapping
    public List<DiscMagEntity> all() {
        return discMagRepository.findAll();
    }

    @Operation(summary = "Get a single disc magazine by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved disc magazine")
    @ApiResponse(responseCode = "404", description = "Disc magazine not found")
    @GetMapping("/{id}")
    public DiscMagEntity getDiscMag(@PathVariable Long id) {
        return discMagRepository.findById(id)
                .orElseThrow(() -> new DiscMagNotFoundException(id));
    }

    @Operation(summary = "Create a new disc magazine")
    @ApiResponse(responseCode = "201", description = "Disc magazine created successfully")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscMagEntity newDiscMag(@RequestBody DiscMagEntity newDiscMag) {
        return discMagRepository.save(newDiscMag);
    }

    @Operation(summary = "Update or Replace a disc magazine")
    @ApiResponse(responseCode = "200", description = "Disc magazine updated successfully")
    @ApiResponse(responseCode = "404", description = "Disc magazine not found")
    @PutMapping("/{id}")
    public DiscMagEntity replaceDiscMag(@RequestBody DiscMagEntity newDiscMag, @PathVariable Long id) {
        return discMagRepository.findById(id)
                .map(discMag -> {
                    discMag.setTitle(newDiscMag.getTitle());
                    discMag.setPrice(newDiscMag.getPrice());
                    discMag.setCopies(newDiscMag.getCopies());
                    discMag.setOrderQty(newDiscMag.getOrderQty());
                    return discMagRepository.save(discMag);
                })
                .orElseGet(() -> {
                    newDiscMag.setId(id);
                    return discMagRepository.save(newDiscMag);
                });
    }

    @Operation(summary = "Delete a disc magazine")
    @ApiResponse(responseCode = "204", description = "Disc magazine deleted successfully")
    @ApiResponse(responseCode = "404", description = "Disc magazine not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiscMag(@PathVariable Long id) {
        if (!discMagRepository.existsById(id)) {
            throw new DiscMagNotFoundException(id);
        }
        discMagRepository.deleteById(id);
    }
}