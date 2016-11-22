package com.volshell.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2364713335391202021L;
    @Id
    @Column(name = "c_id")
    private Integer c_id;
    @Column
    private String c_desc;


}
