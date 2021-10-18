package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TicketSearchService {

	@Autowired
	private TicketRepository ticketRepository;

	public List<Ticket> search(String attribute, String value) {
		return checkSearchAttribute(ticketRepository.search(attribute, value), attribute, value);
	}

	/**
	 * Check every ticket if search value is in required attribute (field).
	 * It was added because of the MongoDB limitation that a collection only can have one text index.
	 * That index can cover multiple fields, hence we do not know in which attribute value was found.
	 *
	 * @param tickets
	 * @param attribute
	 * @param searchValue
	 * @return
	 */
	private List<Ticket> checkSearchAttribute(List<Ticket> tickets, String attribute, String searchValue) {
		List<Ticket> result = new ArrayList<>();
		for (Ticket ticket : tickets) {
			String v = getValue(ticket, attribute);
			if (v != null && v.toLowerCase(Locale.ROOT).contains(searchValue.toLowerCase(Locale.ROOT))) {
				result.add(ticket);
			}
		}
		return result;
	}

	/**
	 * Reads get method by field (attribute) name
	 *
	 * @param ticket
	 * @param fieldName
	 * @return
	 */
	private String getValue(Ticket ticket, String fieldName) {
		try {
			Object value = new PropertyDescriptor(fieldName, Ticket.class).getReadMethod().invoke(ticket);
			return (String) value;
		} catch (Exception e) {
		}
		return null;
	}






}
