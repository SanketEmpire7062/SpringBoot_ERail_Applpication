package com.empire.response;

import lombok.Data;

@Data
public class Ticket {
	
	private int ticketId;
    private String from;
    private String to;
    private String status;
    private String pricePaid;
    private String seat;
    private String user;
    private String email;

}
