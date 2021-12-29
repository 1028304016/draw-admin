package com.draw.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "draw_open")
@Data
public class DrawOpen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//主键
    private String open;
    private String num;//内容


}
