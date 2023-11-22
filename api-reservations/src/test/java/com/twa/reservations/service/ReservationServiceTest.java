package com.twa.reservations.service;

import com.twa.reservations.connector.CatalogConnector;
import com.twa.reservations.connector.configuration.HttpConnectorConfiguration;
import com.twa.reservations.dto.*;
import com.twa.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// 1- It's not necessary use 'public'
// 2- Add the tag and the display name
public class ReservationServiceTest {

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
        ConversionService conversionService = null;
        CatalogConnector catalogConnector = new CatalogConnector(new HttpConnectorConfiguration());

        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        PassengerDTO passenger = new PassengerDTO();
        passenger.setFirstName("Andres");
        passenger.setLastName("Sacco");
        passenger.setDocumentType("DNI");
        passenger.setDocumentNumber("12345678");
        passenger.setBirthday(LocalDate.of(1985, 1, 1));

        PriceDTO price = new PriceDTO();
        price.setBasePrice(BigDecimal.ONE);
        price.setTotalTax(BigDecimal.ZERO);
        price.setTotalPrice(BigDecimal.ONE);

        SegmentDTO segment = new SegmentDTO();
        segment.setArrival("2025-01-01");
        segment.setDeparture("2024-12-31");
        segment.setOrigin("MAD");
        segment.setDestination("FCO");
        segment.setCarrier("AA");

        ItineraryDTO itinerary = new ItineraryDTO();
        itinerary.setPrice(price);
        itinerary.setSegment(List.of(segment));

        ReservationDTO reservation = new ReservationDTO();
        reservation.setPassengers(List.of(passenger));
        reservation.setItinerary(itinerary);

        service.save(reservation);
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
        ConversionService conversionService = null;
        CatalogConnector catalogConnector = new CatalogConnector(new HttpConnectorConfiguration());

        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        service.delete(2L);
    }

    // 0- Declare explicit each section on the test
    // 1- It's not necessary use 'public'
    // 2- Remove the word 'test' for the method
    // 3- Indicate the idea of the test with the name of the method
    // 4- Include assertions
    // 5- Use display name to be more user-friendly
    @Test
    public void testGetReservations() {
        ReservationRepository repository = new ReservationRepository();
        ConversionService conversionService = null;
        CatalogConnector catalogConnector = new CatalogConnector(new HttpConnectorConfiguration());

        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        service.getReservations();
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
        ConversionService conversionService = null;
        CatalogConnector catalogConnector = new CatalogConnector(new HttpConnectorConfiguration());

        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        service.getReservationById(1L);
    }

}
