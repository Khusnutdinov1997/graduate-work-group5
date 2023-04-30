package ru.skypro.homework.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.RegisterReq;

@Repository
public interface UserRepository extends JpaRepository<Long, RegisterReq> {

}
