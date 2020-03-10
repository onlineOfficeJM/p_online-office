package ru.javamentor.onlineoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javamentor.onlineoffice.entity.Room;
import ru.javamentor.onlineoffice.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {
	@Autowired
	private RoomRepository repository;

	public List<Room> list() {
		return repository.findAll();
	}

	public Room get(Long id) {
		return repository.findById(id).get();
	}

	public Room get(String name) {
		return repository.findByName(name);
	}

	public void save(Room room) {
		repository.save(room);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
