package com.twa.reservations.service;

import com.twa.reservations.connector.CatalogConnector;
import com.twa.reservations.connector.response.CityDTO;
import com.twa.reservations.dto.*;
import com.twa.reservations.enums.APIError;
import com.twa.reservations.exception.TWAException;
import com.twa.reservations.model.*;
import com.twa.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.twa.reservations.util.ReservationUtil.getReservationDTO;
import static com.twa.reservations.util.ReservationUtil.getReservation;

@Tag(value = "business-logic")
@DisplayName(value = "ReservationService")
class ReservationServiceTest {

    @Mock
    ReservationRepository repository;

    @Mock
    ConversionService conversionService;

    @Mock
    CatalogConnector catalogConnector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName(value = "Save should persist the reservation the return the information")
    @Test
    void save_should_persist_reservation() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        ReservationDTO reservationDTO = getReservationDTO(null, "BUE", "MIA");
        Reservation reservationModel = getReservation(3L, "BUE", "MIA");

        when(catalogConnector.getCity(any())).thenReturn(new CityDTO());
        when(conversionService.convert(reservationModel, ReservationDTO.class))
                .thenReturn(getReservationDTO(3L, "BUE", "MIA"));
        when(conversionService.convert(reservationDTO, Reservation.class))
                .thenReturn(getReservation(null, "BUE", "MIA"));
        when(repository.save(getReservation(null, "BUE", "MIA"))).thenReturn(reservationModel);

        // When
        ReservationDTO result = service.save(reservationDTO);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(3L, "BUE", "MIA"), result));
    }

    @DisplayName(value = "Save should throw an exception")
    @Test
    void save_should_throw_an_exception() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MIA");

        // When
        TWAException exception = assertThrows(TWAException.class, () -> service.save(reservationDTO));

        // Then
        assertAll(() -> assertEquals(APIError.RESERVATION_WITH_SAME_ID.getHttpStatus(), exception.getStatus()),
                () -> assertEquals(APIError.RESERVATION_WITH_SAME_ID.getMessage(), exception.getDescription()));
    }

    @DisplayName(value = "Delete should remove a reservation")
    @Test
    void delete_should_remove_a_reservation() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        when(repository.getReservationById(1L)).thenReturn(Optional.of(getReservation(1L, "BUE", "MAD")));

        // When
        service.delete(1L);
    }

    @DisplayName(value = "Delete should throw an exception")
    @Test
    void delete_should_throw_an_exception() {

        // Given
        Long reservationID = 1L;
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        when(repository.getReservationById(reservationID)).thenReturn(Optional.empty());

        // When
        TWAException exception = assertThrows(TWAException.class, () -> service.delete(reservationID));

        // Then
        assertAll(() -> assertEquals(APIError.RESERVATION_NOT_FOUND.getHttpStatus(), exception.getStatus()),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getMessage(), exception.getDescription()));
    }

    @DisplayName(value = "GetReservations should return a valid list of reservations")
    @Test
    void getReservations_should_return_a_list_of_elements() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        Reservation reservationModel = getReservation(1L, "BUE", "MAD");
        when(repository.getReservations()).thenReturn(List.of(reservationModel));

        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MAD");
        when(conversionService.convert(List.of(reservationModel), List.class)).thenReturn(List.of(reservationDTO));

        // When
        List<ReservationDTO> result = service.getReservations();

        // Then
        assertAll(() -> assertNotNull(result), () -> assertFalse(result.isEmpty()),
                () -> assertTrue(result.contains(getReservationDTO(1L, "BUE", "MAD"))));
    }

    @DisplayName(value = "GetReservation should return the information of a valid reservation")
    @Test
    void getReservation_should_return_the_information() {
        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        Reservation reservationModel = getReservation(1L, "BUE", "MAD");
        when(repository.getReservationById(1L)).thenReturn(Optional.of(reservationModel));

        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MAD");
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);

        // When
        ReservationDTO result = service.getReservationById(1L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(1L, "BUE", "MAD"), result));
    }

    @DisplayName(value = "GetReservation should not return the information of a reservation")
    @Test
    void getReservation_should_not_return_the_information() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        when(repository.getReservationById(1L)).thenReturn(Optional.empty());

        // When
        TWAException exception = assertThrows(TWAException.class, () -> service.getReservationById(1L));

        // Then
        assertAll(() -> assertEquals(APIError.RESERVATION_NOT_FOUND.getHttpStatus(), exception.getStatus()),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getMessage(), exception.getDescription()));
    }

    @DisplayName(value = "Update should change the reservation the return the information")
    @Test
    void update_should_change_reservation() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        ReservationDTO reservationDTO = getReservationDTO(3L, "BUE", "MIA");
        Reservation reservationModel = getReservation(3L, "BUE", "MIA");

        when(repository.getReservationById(3L)).thenReturn(Optional.of(reservationModel));
        when(catalogConnector.getCity(any())).thenReturn(new CityDTO());
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);
        when(conversionService.convert(reservationDTO, Reservation.class)).thenReturn(reservationModel);
        when(repository.update(3L, reservationModel)).thenReturn(reservationModel);

        // When
        ReservationDTO result = service.update(3L, reservationDTO);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(reservationDTO, result));
    }

    @DisplayName(value = "Update should throw an exception")
    @Test
    void update_should_throw_an_exception() {

        // Given
        Long reservationID = 1L;
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        when(repository.getReservationById(reservationID)).thenReturn(Optional.empty());

        // When
        TWAException exception = assertThrows(TWAException.class,
                () -> service.update(reservationID, getReservationDTO(1L, "BUE", "MAD")));

        // Then
        assertAll(() -> assertEquals(APIError.RESERVATION_NOT_FOUND.getHttpStatus(), exception.getStatus()),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getMessage(), exception.getDescription()));

    }

}
