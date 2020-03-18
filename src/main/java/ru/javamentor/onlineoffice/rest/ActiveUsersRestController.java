package ru.javamentor.onlineoffice.rest;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.javamentor.onlineoffice.entity.ActiveUserStore;
import ru.javamentor.onlineoffice.events.ActiveUserStoreUpdateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class ActiveUsersRestController {
    private ExecutorService threadPool = Executors
            .newCachedThreadPool();
    private final Set<SseEmitter> subscribers = new CopyOnWriteArraySet<>();
    private final ActiveUserStore activeUserStore;

    public ActiveUsersRestController(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

    @GetMapping(value = "/users/userlist")
    public List<String> showUsers() {
        return activeUserStore.getUsers();
    }

    @GetMapping(value = "/users/activeUserList")
    public SseEmitter subscribeToActiveUserStoreEvents() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        subscribers.add(emitter);

        emitter.onTimeout(() -> subscribers.remove(emitter));
        emitter.onCompletion(() -> subscribers.remove(emitter));

        return emitter;
    }

    @Async
    @EventListener
    public void handleActiveUserList(ActiveUserStoreUpdateEvent event) {
        System.out.println(event.getMessage());
        List<SseEmitter> deadEmitters = new ArrayList<>();
        subscribers.forEach(
                emitter ->
                        threadPool.execute(() -> {
                            try {
                                emitter.send(activeUserStore.getUsers());
                            } catch (Exception ex) {
                                emitter.completeWithError(ex);
                            }
                        }));
        subscribers.removeAll(deadEmitters);
    }
}
