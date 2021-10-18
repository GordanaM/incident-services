package com.example.restservice;

import java.util.List;

public interface TicketCustomRepository {
	List<Ticket> search(String attribute, String value);
}
