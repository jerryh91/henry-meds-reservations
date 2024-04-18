1. Download VS Code
2. Open project, and Click Run in any of the .java class
3. By default will run at localhost:8080

4. API contracts below:

Provider: submit available times
POST http://localhost:8080/api-reservations/v3/providers/timeslots
Request
{
    "provider" : {
        "firstName" : "jerry",
        "lastName" : "hou",
        "providerId" : "1"
    },
    "startDateTime" : "2024-04-19T09:00-05:00",
    "endDateTime" : "2024-04-19T17:00-05:00"
}

Clients: request available timeslots
GET http://localhost:8080/api-reservations/v3/providers/timeslots
Response:
[
    "2024-04-19T19:30:00Z",
    "2024-04-19T19:45:00Z",
    "2024-04-19T20:00:00Z",
    "2024-04-19T20:15:00Z",
    "2024-04-19T20:30:00Z",
    "2024-04-19T20:45:00Z",
    "2024-04-19T21:00:00Z",
    "2024-04-19T21:15:00Z",
    "2024-04-19T21:30:00Z",
    "2024-04-19T21:45:00Z"
]

Clients: Reserve a time slot:
POST http://localhost:8080/api-reservations/v3/providers/reservations
{
 "startDateTime" : "2024-04-19T19:30Z"
}

Clients: Confirm a time slot:
POST http://localhost:8080/api-reservations/v3/providers/bookings
{
    "provider": {
        "providerId": "1"
    },
    "startTime": {
        "startDateTime": "2024-04-19T19:30Z"
    }
}
