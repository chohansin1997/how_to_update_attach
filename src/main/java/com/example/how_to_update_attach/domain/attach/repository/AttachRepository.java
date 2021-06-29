package com.example.how_to_update_attach.domain.attach.repository;

import com.example.how_to_update_attach.domain.attach.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;

public interface AttachRepository extends JpaRepository<Attach, Long> {

	List<Attach> findByMenu(Menu menu);
}
