package com.draw.repository;

import com.draw.entity.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BaseInfoRepository extends JpaRepository<BaseInfo,Integer> {

    @Transactional
    @Modifying
    @Query(value ="select name from base_info where name not in (?1) ",nativeQuery = true)
    List<String> selectListByName(List<String> nameList);

    @Modifying
    @Query(value ="select content from base_info where content not in (?1) ",nativeQuery = true)
    List<String> selectListByContent(List<String> contentList);

    @Modifying
    @Query(value ="select * from base_info where id = ?1 ",nativeQuery = true)
    BaseInfo getBaseInfo(Integer id);

    @Modifying
    @Transactional
    @Query(value="delete from base_info where id=?1",nativeQuery = true)
    void deleteOne(Integer id);

}
