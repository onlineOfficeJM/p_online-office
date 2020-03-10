package ru.javamentor.onlineoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.onlineoffice.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findByName(String name);
}
