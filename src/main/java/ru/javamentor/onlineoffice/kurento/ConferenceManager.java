package ru.javamentor.onlineoffice.kurento;

import org.kurento.client.KurentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConferenceManager {
	private final Logger log = LoggerFactory.getLogger(ConferenceManager.class);

	@Autowired
	private KurentoClient kurento;

	private final ConcurrentMap<String, Conference> rooms = new ConcurrentHashMap<>();

	public Conference getConference(String roomName) {
		log.debug("Searching for room {}", roomName);
		Conference conference = rooms.get(roomName);

		if (conference == null) {
			log.debug("Room {} not existent. Will create now!", roomName);
			conference = new Conference(roomName, kurento.createMediaPipeline());
			rooms.put(roomName, conference);
		}
		log.debug("Room {} found!", roomName);
		return conference;
	}

	public void removeConference(Conference conference) {
		this.rooms.remove(conference.getName());
		conference.close();
		log.info("Room {} removed and closed", conference.getName());
	}
}
