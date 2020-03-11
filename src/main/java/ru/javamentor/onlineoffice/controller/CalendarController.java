package ru.javamentor.onlineoffice.controller;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.javamentor.onlineoffice.service.CalendarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @GetMapping("/login/google")
    public RedirectView googleConnectionStatus() throws Exception {
        return new RedirectView(calendarService.getAuthorizeURL());
    }

    @GetMapping(value = "/login/google", params = "code")
    public String oauth2Callback(@RequestParam String code, Model model) throws IOException, GeneralSecurityException {
        calendarService.setCode(code);
        model.addAttribute("calendars", calendarService.getCalendarList());
        return "/calendar";
    }

    @GetMapping("/login/google/{calendar}")
    public ResponseEntity calendarListEvents(@PathVariable String calendar) throws IOException, GeneralSecurityException {

        List<Event> items = calendarService.getEventList(calendar);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/calendar")
    public String calendarView() {
        return "calendar";
    }

    @PostMapping("/login/google/notifyall")
    public String notifyAll( @RequestParam String summary, @RequestParam(value = "description") String description, @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate, @RequestParam String startTime, @RequestParam String endTime) throws GeneralSecurityException, IOException {
        DateTime startDateTime = new DateTime(startDate + "T" + startTime + ":00+03:00");
        DateTime endDateTime = new DateTime(endDate + "T" + endTime + ":00+03:00");
        calendarService.notifyAll( summary, description, startDateTime, endDateTime);
        return "calendar";
    }

    @PostMapping("/calendar")
    public String createEvent(@RequestParam(value = "summary") String summary, @RequestParam(value = "description") String description, @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate, @RequestParam String startTime, @RequestParam String endTime, HttpServletResponse response) throws IOException, GeneralSecurityException {
        DateTime startDateTime = new DateTime(startDate + "T" + startTime + ":00+03:00");
        DateTime endDateTime = new DateTime(endDate + "T" + endTime + ":00+03:00");
        calendarService.createEvent("primary", summary, description, startDateTime, endDateTime);
        return "calendar";
    }
}
