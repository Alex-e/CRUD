package net.ieromenko.service.jpa;

import com.google.common.collect.Lists;
import net.ieromenko.domain.SearchCriteria;
import net.ieromenko.domain.User;
import net.ieromenko.repository.UserRepository;
import net.ieromenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alex Ieromenko on 03.11.14.
 */

@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> list = Lists.newArrayList(userRepository.findAll());
        return list;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findOne(id);
    }


    public void delete(User user) {
        userRepository.delete(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<User> findAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<User> findUserByCriteria(SearchCriteria searchCriteria,
                                         Pageable pageable) {
        String name = searchCriteria.getName();
        return userRepository.findUserByCriteria(name, pageable);
    }
}
