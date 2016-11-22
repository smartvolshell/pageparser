package com.volshell.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "wechat")
public class WeChat implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7550360250145236861L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "c_id", referencedColumnName = "c_id")
    private Category category;
    @Column
    private String w_name;// 微信名称
    @Column
    private String w_code;// 微信号
    @Column
    private String w_png;// 二维码链接
    @Column
    private String text_short;// 短文案
    @Column(length = 625)
    private String text_long;// 长文案


}
