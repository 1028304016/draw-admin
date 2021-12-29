package com.draw.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//主键
    private String name;//内容
    private String content;//内容
    private Date createTime;//内容

}
