package net.ieromenko.repository;

import net.ieromenko.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Alex Ieromenko on 03.11.14.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("select u from User u where  u.name like :name")
    public Page<User> findUserByCriteria(@Param("name") String name, Pageable pageable);
}
