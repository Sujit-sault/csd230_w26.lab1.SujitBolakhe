package csd230.lab1.controllers;

import csd230.lab1.entities.TicketEntity;
import csd230.lab1.repositories.TicketEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ticket REST API", description = "JSON API for managing tickets")
@RestController
@RequestMapping("/api/rest/tickets")
@CrossOrigin(origins = "*")
public class TicketRestController {

    private final TicketEntityRepository ticketRepository;

    public TicketRestController(TicketEntityRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Operation(summary = "Get all tickets as JSON")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tickets")
    @GetMapping
    public List<TicketEntity> all() {
        return ticketRepository.findAll();
    }

    @Operation(summary = "Get a single ticket by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @GetMapping("/{id}")
    public TicketEntity getTicket(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Operation(summary = "Create a new ticket")
    @ApiResponse(responseCode = "201", description = "Ticket created successfully")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketEntity newTicket(@RequestBody TicketEntity newTicket) {
        return ticketRepository.save(newTicket);
    }

    @Operation(summary = "Update or Replace a ticket")
    @ApiResponse(responseCode = "200", description = "Ticket updated successfully")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @PutMapping("/{id}")
    public TicketEntity replaceTicket(@RequestBody TicketEntity newTicket, @PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setDescription(newTicket.getDescription());
                    ticket.setEventName(newTicket.getEventName());
                    ticket.setEventDate(newTicket.getEventDate());
                    ticket.setTicketPrice(newTicket.getTicketPrice());
                    return ticketRepository.save(ticket);
                })
                .orElseGet(() -> {
                    newTicket.setId(id);
                    return ticketRepository.save(newTicket);
                });
    }

    @Operation(summary = "Delete a ticket")
    @ApiResponse(responseCode = "204", description = "Ticket deleted successfully")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException(id);
        }
        ticketRepository.deleteById(id);
    }
}