package com.empire.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empire.request.Passenger;
import com.empire.response.Ticket;
import com.empire.service.TicketService;

import java.util.List;

@RestController
public class ErailRestController {

	@Autowired
	private TicketService ticketService;

	// To book ticket
	// localhost:8080/ticket
	@PostMapping(value = "/ticket", consumes = { "application/xml", "application/json" }, produces = {
			"application/xml", "application/json" })
	public Ticket bookTicket(@RequestBody Passenger passenger) {
		return ticketService.bookTicket(passenger);
	}

	// search ticket
	// localhost:8080/ticket/{ticketId}
	@GetMapping(value = "/ticket/{ticketId}", produces = { "application/xml", "application/json" })
	public ResponseEntity<?> getTicket(@PathVariable Integer ticketId) {
		Ticket ticket = ticketService.getTicket(ticketId);
		if (ticket != null) {
			return ResponseEntity.ok(ticket);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket with ID " + ticketId + " not found.");
	}

	// modify user data
	@PutMapping(value = "/ticket/{ticketId}", consumes = { "application/xml", "application/json" }, produces = {
			"application/xml", "application/json" })
	public Ticket updateTicket(@PathVariable Integer ticketId, @RequestBody Passenger passenger) {
		return ticketService.updateTicket(ticketId, passenger);
	}

	// To change seat
	// URL : localhost:8080/ticket/{ticketId}/update
	@PutMapping(value = "/ticket/{ticketId}/update", consumes = { "application/xml",
			"application/json" }, produces = { "application/xml", "application/json" })
	public ResponseEntity<String> updateTicketId(@PathVariable Integer ticketId) {
		return ticketService.updateTicketId(ticketId);
	}

	// show all passenger section wise
	// localhost:8080/ticket/section/Section%20B
	@GetMapping(value = "/ticket/section/{section}", produces = { "application/xml", "application/json" })
	public ResponseEntity<?> getTicketsBySection(@PathVariable String section) {
		List<Ticket> sectionTickets = ticketService.getTicketsBySection(section);
		if (sectionTickets.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tickets found in section " + section);
		} else {
			return ResponseEntity.ok(sectionTickets);
		}
	}

	// to delete a ticket
	@DeleteMapping(value = "/ticket/{ticketId}", produces = { "application/xml", "application/json" })
	public String deleteTicket(@PathVariable Integer ticketId) {
		return ticketService.deleteTicket(ticketId);
	}
}
