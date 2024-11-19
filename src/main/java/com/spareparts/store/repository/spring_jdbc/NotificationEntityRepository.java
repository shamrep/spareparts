package com.spareparts.store.repository.spring_jdbc;


import com.spareparts.store.repository.entity.NotificationEntity;

import java.util.List;
import java.util.Optional;

//@Repository
public interface NotificationEntityRepository {

    Optional<NotificationEntity> findById(long id);

    List<NotificationEntity> findAll();

    long save(NotificationEntity notificationEntity);

    void update(NotificationEntity notificationEntity);

    void deleteById(long id);

    List<NotificationEntity> findUnreadNotifications();
}
