package com.twa.reservations.repository;

import com.twa.reservations.model.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static com.twa.reservations.util.ReservationUtil.getReservation;

@Tag(value = "persistence")
@DisplayName(value = "ReservationRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationRepositoryTest {

    @Order(1)
    @DisplayName(value = "GetReservations should return a valid list of reservations")
    @Test
    void getReservations_should_return_a_list_of_elements() {
        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        List<Reservation> result = repository.getReservations();

        // Then - First option
        /*
         * assertAll( ()-> assertNotNull(result), ()-> assertFalse(result.isEmpty()), ()-> assertEquals(1L,
         * result.get(0).getId()), ()-> assertNotNull(result.get(0).getItinerary()), ()->
         * assertNotNull(result.get(0).getPassengers()), ()-> assertFalse(result.get(0).getPassengers().isEmpty()) );
         */

        // Then - Second option
        assertAll(() -> assertNotNull(result), () -> assertFalse(result.isEmpty()),
                () -> assertTrue(result.contains(getReservation(1L, "EZE", "MIA"))));
    }

    @Order(1)
    @DisplayName(value = "GetReservation should return the information of a valid reservation")
    @Test
    void getReservation_should_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(1L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
    }

    @Order(1)
    @DisplayName(value = "GetReservation should not return the information of a reservation")
    @Test
    void getReservation_should_not_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(6L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertTrue(result.isEmpty()));
    }

    @DisplayName(value = "Save should persist the reservation the return the information")
    @Test
    @Order(2)
    void save_should_persist_reservation() {

        // Given
        ReservationRepository repository = new ReservationRepository();
        Reservation reservation = getReservation(null, "BUE", "MAD");

        // When
        Reservation result = repository.save(reservation);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservation(3L, "BUE", "MAD"), result));
    }

    @DisplayName(value = "Delete should remove a reservation")
    @Test
    @Order(3)
    void delete_should_remove_a_reservation() {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        repository.delete(2L);

        // It's not necessary to it. It's just to check
        Optional<Reservation> result = repository.getReservationById(2L);

        // Then
        assertTrue(result.isEmpty());
    }

    @DisplayName(value = "Update should change the reservation the return the information")
    @Test
    @Order(4)
    void update_should_change_reservation() {

        // Given
        ReservationRepository repository = new ReservationRepository();
        Reservation reservation = getReservation(1L, "BUE", "MAD");
        reservation.getItinerary().getPrice().setBasePrice(BigDecimal.TEN);

        // When
        Reservation result = repository.update(1L, reservation);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(reservation, result));
    }

}
