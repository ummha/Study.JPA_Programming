package com.example.jpa.bookmanager.domain.listener;

import com.example.jpa.bookmanager.domain.User;
import com.example.jpa.bookmanager.domain.UserHistory;
import com.example.jpa.bookmanager.repository.UserHistoryRepository;
import com.example.jpa.bookmanager.support.BeanUtils;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class UserEntityListener {
//    @Resource
//    private UserHistoryRepository userHistoryRepository; // 작동안함

    /**
     * Entity Listener 객체는 Spring Bean을 주입받을 수 없다.
     * 그래서 사용시 스프링빈은 가져올수 있는 특별한 커스텀 빈 유틸을 정의하여 사용하여야 한다.
     * 참고: bookmanager.support.BeanUtils.java
     */

    @PrePersist
    @PreUpdate
    public void postUpdate(Object o) {
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);
        User user = (User) o;
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(user.getId());
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistoryRepository.save(userHistory);
    }
}
