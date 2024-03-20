package com.mysite.samteam.repository;

import com.mysite.samteam.vo.FirebaseVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirebaseRepository extends JpaRepository<FirebaseVO, String> {
}
