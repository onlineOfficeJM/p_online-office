package ru.javamentor.onlineoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javamentor.onlineoffice.entity.User;
import ru.javamentor.onlineoffice.service.RoomService;

@Controller
public class RoomController {
	@Autowired
	RoomService roomService;

	@GetMapping("/room")
	public String conference(Authentication authentication, Model model) {
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		model.addAttribute("rooms", roomService.list());
		return "room";
	}
}