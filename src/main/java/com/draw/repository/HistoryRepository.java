package com.draw.repository;

import com.draw.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History,Integer> {

    @Query(value ="select *  from history order by create_time desc",nativeQuery = true)
    List<History> findAllByOrder();

}
