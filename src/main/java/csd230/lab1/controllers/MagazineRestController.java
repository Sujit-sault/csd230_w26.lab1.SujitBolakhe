package csd230.lab1.controllers;

import csd230.lab1.entities.MagazineEntity;
import csd230.lab1.repositories.MagazineEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Magazine REST API", description = "JSON API for managing magazines")
@RestController
@RequestMapping("/api/rest/magazines")
@CrossOrigin(origins = "*")
public class MagazineRestController {

    private final MagazineEntityRepository magazineRepository;

    public MagazineRestController(MagazineEntityRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    @Operation(summary = "Get all magazines as JSON")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of magazines")
    @GetMapping
    public List<MagazineEntity> all() {
        return magazineRepository.findAll();
    }

    @Operation(summary = "Get a single magazine by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved magazine")
    @ApiResponse(responseCode = "404", description = "Magazine not found")
    @GetMapping("/{id}")
    public MagazineEntity getMagazine(@PathVariable Long id) {
        return magazineRepository.findById(id)
                .orElseThrow(() -> new MagazineNotFoundException(id));
    }

    @Operation(summary = "Create a new magazine")
    @ApiResponse(responseCode = "201", description = "Magazine created successfully")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MagazineEntity newMagazine(@RequestBody MagazineEntity newMagazine) {
        return magazineRepository.save(newMagazine);
    }

    @Operation(summary = "Update or Replace a magazine")
    @ApiResponse(responseCode = "200", description = "Magazine updated successfully")
    @ApiResponse(responseCode = "404", description = "Magazine not found")
    @PutMapping("/{id}")
    public MagazineEntity replaceMagazine(@RequestBody MagazineEntity newMagazine, @PathVariable Long id) {
        return magazineRepository.findById(id)
                .map(magazine -> {
                    magazine.setTitle(newMagazine.getTitle());
                    magazine.setPrice(newMagazine.getPrice());
                    magazine.setCopies(newMagazine.getCopies());
                    magazine.setOrderQty(newMagazine.getOrderQty());
                    return magazineRepository.save(magazine);
                })
                .orElseGet(() -> {
                    newMagazine.setId(id);
                    return magazineRepository.save(newMagazine);
                });
    }

    @Operation(summary = "Delete a magazine")
    @ApiResponse(responseCode = "204", description = "Magazine deleted successfully")
    @ApiResponse(responseCode = "404", description = "Magazine not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMagazine(@PathVariable Long id) {
        if (!magazineRepository.existsById(id)) {
            throw new MagazineNotFoundException(id);
        }
        magazineRepository.deleteById(id);
    }
}