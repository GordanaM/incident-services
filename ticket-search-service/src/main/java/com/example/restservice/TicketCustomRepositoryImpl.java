package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketCustomRepositoryImpl implements TicketCustomRepository {
	@Autowired
	MongoTemplate mongoTemplate;

	public List<Ticket> search(String attribute, String value) {
		TextCriteria criteria = TextCriteria
				.forDefaultLanguage()
				.matchingPhrase(value);

		Query query = TextQuery.queryText(criteria);

		List<Ticket> tickets = mongoTemplate.find(query, Ticket.class);

		return tickets;

	}

}
