package ru.javamentor.onlineoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.onlineoffice.entity.GuestUrl;

public interface GuestUrlRepository extends JpaRepository<GuestUrl, Long> {
    GuestUrl findByUrl(String url);
}
