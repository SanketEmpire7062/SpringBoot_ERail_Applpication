package com.empire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.empire.request.Passenger;
import com.empire.response.Ticket;
import com.empire.service.TicketService;

public class TicketServiceTest {

	@InjectMocks
	private TicketService ticketService;

	@Mock
	private AtomicInteger ticketIdGenerator = new AtomicInteger();

	@Mock
	Map<Integer, Ticket> tickets;

	@BeforeEach
	void setUp() {
		ticketService = new TicketService();
		// Initialize the tickets map with a ticket
		Ticket ticket = new Ticket();
		ticket.setTicketId(101);
		ticket.setFrom("Mumbai");
		ticket.setTo("Pune");
		ticket.setUser("Initial User");
		ticket.setEmail("initial@domain.com");
		ticket.setSeat("Section A");
		ticket.setStatus("Confirmed");
		ticket.setPricePaid("$20");

		ticketService.tickets.put(101, ticket);
	}

	@Test
	void deleteTicket_test() {
		ticketService = new TicketService();
		String result = ticketService.deleteTicket(101);
		assertNotNull(result);
	}

	@Test
	void updateTicket_ticket() {
		Integer ticketId = 101;
		Passenger passenger = new Passenger();
		passenger.setFrom("Nagpur");
		passenger.setTo("Delhi");
		passenger.setFirstName("Ramesh");
		passenger.setLastName("Suresh");
		passenger.setEmail("abc@gamil.com");

		Ticket result = ticketService.updateTicket(ticketId, passenger);
     
		assertNotNull(result);
		assertEquals("Nagpur", result.getFrom());
		assertEquals("Delhi", result.getTo());
		assertEquals("Ramesh Suresh", result.getUser());
		assertEquals("abc@gamil.com", result.getEmail());
	}
  
	
	@Test
	void updateTicket_ticketNotPresent() {
		Integer ticketId = 102; // This ticket ID does not exist
		Passenger passenger = new Passenger();
		passenger.setFrom("Nagpur");
		passenger.setTo("Delhi");
		passenger.setFirstName("Ramesh");
		passenger.setLastName("Suresh");
		passenger.setEmail("abc@gamil.com");

		Ticket result = ticketService.updateTicket(ticketId, passenger);

		assertNull(result);
	}
}
