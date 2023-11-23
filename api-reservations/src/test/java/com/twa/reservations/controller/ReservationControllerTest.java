package com.twa.reservations.controller;

import com.twa.reservations.dto.*;
import com.twa.reservations.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import static com.twa.reservations.util.ReservationUtil.getReservationDTO;

@Tag(value = "controller")
@DisplayName(value = "ReservationController")
class ReservationControllerTest {

    @Mock
    ReservationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName(value = "GetReservations should return a valid list of reservations")
    @Test
    void getReservations_should_return_a_list_of_elements() {

        // Given
        ReservationController controller = new ReservationController(service);
        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MAD");
        when(service.getReservations()).thenReturn(List.of(reservationDTO));

        // When
        ResponseEntity<List<ReservationDTO>> result = controller.getReservations();

        // Then
        assertAll(() -> assertNotNull(result), () -> assertNotNull(result.getBody()),
                () -> assertFalse(Objects.requireNonNull(result.getBody()).isEmpty()),
                () -> assertTrue(Objects.requireNonNull(result.getBody()).contains(reservationDTO)));
    }

    @DisplayName(value = "GetReservation should return the information of a valid reservation")
    @Test
    void getReservation_should_return_the_information() {

        // Given
        ReservationController controller = new ReservationController(service);
        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MAD");
        when(service.getReservationById(1L)).thenReturn(reservationDTO);

        // When
        ResponseEntity<ReservationDTO> result = controller.getReservationById(1L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertNotNull(result.getBody()),
                () -> assertEquals(getReservationDTO(1L, "BUE", "MAD"), result.getBody()));
    }

    @DisplayName(value = "Delete should remove a reservation")
    @Test
    void delete_should_remove_a_reservation() {

        // Given
        ReservationController controller = new ReservationController(service);

        // When
        ResponseEntity<Void> result = controller.delete(1L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertNull(result.getBody()));
    }

    @DisplayName(value = "Save should persist the reservation the return the information")
    @Test
    void save_should_persist_reservation() {

        // Given
        ReservationController controller = new ReservationController(service);
        ReservationDTO reservationDTO = getReservationDTO(null, "BUE", "MIA");

        when(service.save(reservationDTO)).thenReturn(getReservationDTO(3L, "BUE", "MIA"));

        // When
        ReservationDTO result = service.save(reservationDTO);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(3L, "BUE", "MIA"), result));
    }

    @DisplayName(value = "Update should change the reservation the return the information")
    @Test
    void update_should_change_reservation() {

        // Given
        ReservationController controller = new ReservationController(service);
        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MIA");

        when(service.update(1L, reservationDTO)).thenReturn(getReservationDTO(1L, "BUE", "MIA"));

        // When
        ReservationDTO result = service.update(1L, reservationDTO);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(1L, "BUE", "MIA"), result));
    }

}
