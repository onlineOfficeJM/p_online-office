package ru.javamentor.onlineoffice.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.javamentor.onlineoffice.entity.GuestUrl;
import ru.javamentor.onlineoffice.entity.User;
import ru.javamentor.onlineoffice.exception.GuestUrlException;
import ru.javamentor.onlineoffice.repository.GuestUrlRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GuestAccessService {

    private final GuestUrlRepository repository;
    private final UserService userService;

    public GuestAccessService(GuestUrlRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public String listUsersUrl(String baseUrl) {
        GuestUrl guestUrl = generateUrl(baseUrl, "/guest/user/list/");
        GuestUrl foundUrl = repository.findByUrl(guestUrl.getUrl());
        if (foundUrl == null) {
            repository.save(guestUrl);
            return guestUrl.getUrl();
        }
        if (!LocalDateTime.now().isBefore(foundUrl.getExpiryDate())) {
            repository.delete(foundUrl);
            repository.save(guestUrl);
            return guestUrl.getUrl();
        }

        guestUrl = generateUrl(baseUrl, "/guest/user/list/");
        foundUrl = repository.findByUrl(guestUrl.getUrl());
        if (foundUrl == null) {
            repository.save(guestUrl);
            return guestUrl.getUrl();
        }
        throw new GuestUrlException("Can't generate guest url: 2 failed attempts!");
    }

    public List<User> listUsers(String url) {
        GuestUrl guestUrl = repository.findByUrl(url);
        if (guestUrl == null || !LocalDateTime.now().isBefore(guestUrl.getExpiryDate())) {
            throw new GuestUrlException("Illegal guest url: " + url);
        }
       return userService.listAll();
    }

    private GuestUrl generateUrl(String baseUrl, String action) {
        String random = RandomStringUtils.random(40, false, true);
        StringBuffer sb = new StringBuffer();
        sb.append(baseUrl).append(action).append(random);
        return new GuestUrl(sb.toString(), action, LocalDateTime.now().plusDays(7));
    }
}
