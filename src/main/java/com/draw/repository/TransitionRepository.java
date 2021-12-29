package com.draw.repository;

import com.draw.entity.Transition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransitionRepository extends JpaRepository<Transition,Integer> {
}
