package net.ieromenko.service;

import net.ieromenko.domain.SearchCriteria;
import net.ieromenko.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Alex Ieromenko on 03.11.14.
 */
public interface UserService {
    public List<User> findAll();

    public User findById(Long id);

    public void delete(User user);

    public User save(User user); // create and update

    public Page<User> findAllByPage(Pageable pageable);

    public Page<User> findUserByCriteria(SearchCriteria searchCriteria, Pageable pageable);
}
