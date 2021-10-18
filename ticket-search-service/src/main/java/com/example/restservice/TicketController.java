package com.example.restservice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

	@Autowired
	private TicketSearchService ticketService;

	private Set<String> attributeNameSet = new HashSet<>(Arrays.asList("id", "type", "subject", "description", "priority", "status")); 
	
	@GetMapping("/search")
	public ResponseEntity<List<Ticket>> search(@RequestParam(value = "attribute") String attribute, @RequestParam(value = "value") String value) {
		if (StringUtils.isBlank(attribute) || StringUtils.isBlank(value) || !attributeNameSet.contains(attribute)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}		
		List<Ticket> tickets = ticketService.search(attribute, value);
		return ResponseEntity.ok(tickets);
	}
	
}
