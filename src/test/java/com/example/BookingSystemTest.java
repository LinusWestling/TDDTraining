package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingSystemTest {

    @Mock
    private TimeProvider timeProvider;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private NotificationService notificationService;

    private BookingSystem bookingSystem;

    @BeforeEach
    void setUp() throws NotificationException {
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
    }

    @Test
    @DisplayName("Should successfully book a room when all conditions are met")
    void bookRoom_Success() throws NotificationException {
        // Given
        String roomId = "room1";
        LocalDateTime now = LocalDateTime.of(2025, 1, 30, 10, 0);
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = now.plusHours(2);
        Room room = new Room(roomId, "Test Room");

        when(timeProvider.getCurrentTime()).thenReturn(now);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);

        doNothing().when(roomRepository).save(roomCaptor.capture());
        doNothing().when(notificationService).sendBookingConfirmation(bookingCaptor.capture());

        // When
        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);

        // Then
        assertThat(result).isTrue();

        Booking capturedBooking = bookingCaptor.getValue();
        Room capturedRoom = roomCaptor.getValue();

        assertThat(capturedBooking.getRoomId()).isEqualTo(roomId);
        assertThat(capturedBooking.getStartTime()).isEqualTo(startTime);
        assertThat(capturedBooking.getEndTime()).isEqualTo(endTime);

        assertThat(capturedRoom.hasBooking(capturedBooking.getId())).isTrue();
        verify(roomRepository, times(1)).save(room);
        verify(notificationService, times(1)).sendBookingConfirmation(any(Booking.class));
    }

    @ParameterizedTest
    @DisplayName("Should throw IllegalArgumentException for invalid bookRoom arguments")
    @MethodSource("invalidBookRoomArguments")
    void bookRoom_ThrowsIllegalArgumentException_WhenArgumentsAreInvalid(
            String roomId, LocalDateTime startTime, LocalDateTime endTime, String expectedMessage) {
        // Given
        lenient().when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.of(2025, 1, 30, 10, 0));

        // When/Then
        assertThatThrownBy(() -> bookingSystem.bookRoom(roomId, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);

        verifyNoInteractions(roomRepository);
        verifyNoInteractions(notificationService);
    }

    private static Stream<Arguments> invalidBookRoomArguments() {
        LocalDateTime now = LocalDateTime.of(2025, 1, 30, 10, 0);
        LocalDateTime pastTime = now.minusHours(1);
        LocalDateTime futureTime = now.plusHours(1);

        return Stream.of(
                Arguments.of("room1", null, futureTime, "Bokning kräver giltiga start- och sluttider samt rum-id"),
                Arguments.of("room1", futureTime, null, "Bokning kräver giltiga start- och sluttider samt rum-id"),
                Arguments.of(null, futureTime, futureTime.plusHours(1), "Bokning kräver giltiga start- och sluttider samt rum-id"),
                Arguments.of("room1", pastTime, futureTime, "Kan inte boka tid i dåtid"),
                Arguments.of("room1", futureTime.plusHours(1), futureTime, "Sluttid måste vara efter starttid")
        );
    }
}
