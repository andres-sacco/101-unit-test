package com.twa.reservations.controller;

import com.twa.reservations.connector.CatalogConnector;
import com.twa.reservations.connector.configuration.HttpConnectorConfiguration;
import com.twa.reservations.repository.ReservationRepository;
import com.twa.reservations.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;

// 1- It's not necessary use 'public'
// 2- Add the tag and the display name
public class ReservationControllerTest {

    // 0- Declare explicit each section on the test
    // 1- It's not necessary use 'public'
    // 2- Remove the word 'test' for the method
    // 3- Indicate the idea of the test with the name of the method
    // 4- Include assertions
    // 5- Use mocks instead of the real classes
    // 6- Use display name to be more user-friendly
    @Test
    public void testGetReservations() {

        ReservationRepository repository = new ReservationRepository();
        ConversionService conversionService = null;
        CatalogConnector catalogConnector = new CatalogConnector(new HttpConnectorConfiguration());

        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        ReservationController controller = new ReservationController(service);

        controller.getReservations();
    }

    // 0- Declare explicit each section on the test
    // 1- It's not necessary use 'public'
    // 2- Remove the word 'test' for the method
    // 3- Indicate the idea of the test with the name of the method
    // 4- Include assertions
    // 5- Use mocks instead of the real classes
    // 6- Use display name to be more user-friendly
    @Test
    public void testGetReservationById() {

        ReservationRepository repository = new ReservationRepository();
        ConversionService conversionService = null;
        CatalogConnector catalogConnector = new CatalogConnector(new HttpConnectorConfiguration());

        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        ReservationController controller = new ReservationController(service);

        controller.getReservationById(1L);
    }
}
