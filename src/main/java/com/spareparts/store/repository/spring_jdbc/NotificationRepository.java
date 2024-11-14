package com.spareparts.store.repository.spring_jdbc;


import com.spareparts.store.model.Notification;

import java.util.List;
import java.util.Optional;

//@Repository
public interface NotificationRepository {

    Optional<Notification> findById(long id);

    List<Notification> findAll();

    long save(Notification notification);

    void update(Notification notification);

    void deleteById(long id);

    List<Notification> findUnreadNotifications();
}
