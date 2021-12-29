package com.draw.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "base_info")
@Data
public class BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//主键
    private String name;
    private String content;//内容

}
