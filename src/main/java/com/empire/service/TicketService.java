package com.empire.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.empire.request.Passenger;
import com.empire.response.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TicketService {

    public Map<Integer, Ticket> tickets = new HashMap<>();
	private Random random = new Random();
	private AtomicInteger ticketIdGenerator = new AtomicInteger();

	public Ticket bookTicket(Passenger passenger) {
		Ticket t = new Ticket();
		int ticketId = ticketIdGenerator.incrementAndGet();
		String seat = allocateSeat();
		t.setTicketId(ticketId);
		t.setFrom(passenger.getFrom());
		t.setTo(passenger.getTo());
		t.setStatus("Conformed");
		t.setPricePaid("$20");
		t.setSeat(seat);
		t.setUser(passenger.getFirstName() + " " + passenger.getLastName());
		t.setEmail(passenger.getEmail());
		tickets.put(ticketId, t);
		System.out.println("Generated ticket id is ::" + ticketId);
		return t;
	}

	public Ticket getTicket(Integer ticketId) {
		return tickets.get(ticketId);
	}

	public Ticket updateTicket(Integer ticketId, Passenger passenger) {
		if (tickets.containsKey(ticketId)) {
			Ticket t = tickets.get(ticketId);
			t.setFrom(passenger.getFrom());
			t.setTo(passenger.getTo());
			t.setUser(passenger.getFirstName() + " " + passenger.getLastName());
			t.setEmail(passenger.getEmail());
			tickets.put(ticketId, t);
			return t;
		}
		return null;
	}

	public ResponseEntity<String> updateTicketId(Integer ticketId) {
		if (tickets.containsKey(ticketId)) {
			int newTicketId;
			do {
				newTicketId = ticketIdGenerator.incrementAndGet();
			} while (tickets.containsKey(newTicketId));
			Ticket t = tickets.get(ticketId);
			tickets.remove(ticketId);
			t.setTicketId(newTicketId);
			tickets.put(newTicketId, t);
			return ResponseEntity.ok(
					"Your ticket ID change request has been processed successfully. Updated ticket ID: " + newTicketId);
		}
		return ResponseEntity.notFound().build();
	}

	public List<Ticket> getTicketsBySection(String section) {
		return tickets.values().stream().filter(ticket -> ticket.getSeat().equalsIgnoreCase(section))
				.collect(Collectors.toList());
	}

	public String deleteTicket(Integer ticketId) {
		if (tickets.containsKey(ticketId)) {
			tickets.remove(ticketId);
			return "Ticket with ID " + ticketId + " has been deleted successfully.";
		}
		return "Ticket with ID " + ticketId + " not found.";
	}

	private String allocateSeat() {
		return random.nextBoolean() ? "Section A" : "Section B";
	}
}
