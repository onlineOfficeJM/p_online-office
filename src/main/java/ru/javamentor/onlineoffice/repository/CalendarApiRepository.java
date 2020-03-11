package ru.javamentor.onlineoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.onlineoffice.entity.UserCalendar;

public interface CalendarApiRepository extends JpaRepository<UserCalendar, Long> {
    UserCalendar findById(long id);
}
