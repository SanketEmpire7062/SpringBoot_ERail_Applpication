package com.empire.request;

import lombok.Data;

@Data
public class Passenger {
	
	private String firstName;
	private String lastName;
	private String email;
	private String from;
	private String to;
	private Integer  tickcetCost;

}
