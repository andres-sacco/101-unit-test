package com.twa.reservations.repository;

import com.twa.reservations.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// 1- It's not necessary use 'public'
// 2- Add the tag and the display name
public class ReservationRepositoryTest {

    // 0- Declare explicit each section on the test
    // 1- It's not necessary use 'public'
    // 2- Remove the word 'test' for the method
    // 3- Indicate the idea of the test with the name of the method
    // 4- Include assertions
    // 5- Use display name to be more user-friendly
    @Test
    public void testGetReservations() {
        ReservationRepository repository = new ReservationRepository();

        repository.getReservations();
    }

    // 0- Declare explicit each section on the test
    // 1- It's not necessary use 'public'
    // 2- Remove the word 'test' for the method
    // 3- Indicate the idea of the test with the name of the method
    // 4- Include assertions
    // 5- Use display name to be more user-friendly
    // 6- Not exists a test when not exist a reservation
    @Test
    public void testGetReservationById() {
        ReservationRepository repository = new ReservationRepository();

        repository.getReservationById(1L);
    }

    // 0- Declare explicit each section on the test
    // 1- It's not necessary use 'public'
    // 2- Remove the word 'test' for the method
    // 3- Indicate the idea of the test with the name of the method
    // 4- Include assertions
    // 5- Use display name to be more user-friendly
    // 6- Extract the setup to an external method
    @Test
    public void testSave() {

        ReservationRepository repository = new ReservationRepository();

        Passenger passenger = new Passenger();
        passenger.setFirstName("Andres");
        passenger.setLastName("Sacco");
        passenger.setId(1L);
        passenger.setDocumentType("DNI");
        passenger.setDocumentNumber("12345678");
        passenger.setBirthday(LocalDate.of(1985, 1, 1));

        Price price = new Price();
        price.setBasePrice(BigDecimal.ONE);
        price.setTotalTax(BigDecimal.ZERO);
        price.setTotalPrice(BigDecimal.ONE);

        Segment segment = new Segment();
        segment.setArrival("2025-01-01");
        segment.setDeparture("2024-12-31");
        segment.setOrigin("EZE");
        segment.setDestination("MIA");
        segment.setCarrier("AA");
        segment.setId(1L);

        Itinerary itinerary = new Itinerary();
        itinerary.setId(1L);
        itinerary.setPrice(price);
        itinerary.setSegment(List.of(segment));

        Reservation reservation = new Reservation();
        reservation.setPassengers(List.of(passenger));
        reservation.setItinerary(itinerary);

        repository.save(reservation);
    }

    // 0- Declare explicit each section on the test
    // 1- It's not necessary use 'public'
    // 2- Remove the word 'test' for the method
    // 3- Indicate the idea of the test with the name of the method
    // 4- Include assertions
    // 5- Use display name to be more user-friendly
    // 6- Check the order
    @Test
    public void testDelete() {
        ReservationRepository repository = new ReservationRepository();

        repository.delete(2L);
    }
}
