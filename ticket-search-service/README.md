# Ticket Search Service

Ticket search service is part of a microservices architecture for an incident services. This solution creates separate service for full text search over all the tickets as answer to high load traffic where concurrent users use the same database for CRUD actions.
Ticket service which is supposed to be a service which will handle update, create and delete tickets should send them through a message queue which is highly concurrent and scalable and can process thousands of messages per second concurrently across many servers/processes.

In this implementation I used MongoDB full text search functionalities, because of a lack of full text search over a specific attribute I implemented adding an additional filtering on the application level (filtering for checking weather the word is part of required attribute).

Script for insert a test data into MongoDB: 

```
db.ticket.insertMany(
[
{
"id": 1,
"type": "incident",
"subject": "Cargo Missing",
"description": "Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.",
"priority": "high",
"status": "pending"
},
{
"id": 2,
"type": "incident",
"subject": "Booking Error",
"description": "Aliquip excepteur fugiat ex minim ea aute eu labore. Sunt eiusmod esse eu non commodo est veniam consequat.",
"priority": "low",
"status": "hold"
},
{
"id": 3,
"type": "incident",
"subject": "Payment Sent Error",
"description": "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit",
"priority": "high",
"status": "pending"
}
]
);
```

Creating text index to support text search queries on string content

```
db.ticket.createIndex( { id: "text", type: "text", subject: "text", description: "text", priority: "text", status: "text" } );
```


