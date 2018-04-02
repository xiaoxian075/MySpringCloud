package com.scd.coredb.pojo.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Administrator on 2018-03-05.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_comment")
public class TComment {

    public final static String GOODS_ID = "goodsId";
    public final static String IS_SHOW = "isShow";
    public final static String COMMENT_TIME = "commentTime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    @Column(name = "account_id", nullable = false, length = 20)
    private long accountId;

    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId;

    @Column(name = "goods_id", nullable = false, length = 20)
    private long goodsId;

    @Column(name = "goods_describe", nullable = false, length = 20)
    private String goodsDescribe;

    @Column(name = "describe_star", nullable = false, length = 20)
    private int describeStar;

    @Basic(fetch=FetchType.LAZY)
    @Column(name = "content")
    private String content;

    @Column(name = "is_anonymous", nullable = false, length = 20)
    private int isAnonymous;

    @Column(name = "logistics", nullable = false, length = 20)
    private String logistics;

    @Column(name = "logistics_star", nullable = false, length = 20)
    private int logisticsStar;

    @Column(name = "service", nullable = false, length = 20)
    private String service;

    @Column(name = "service_star", nullable = false, length = 20)
    private int serviceStar;

    @Column(name = "picture_url",  length = 500)
    private String pictureUrl;

    @Column(name = "is_show", nullable = false, length = 20)
    private int isShow;

    @Column(name = "comment_time", nullable = false, length = 20)
    private long commentTime;

    @Column(name = "account",  length = 300)
    private String account;

    @Column(name = "nickname",  length = 300)
    private String nickName;

    @Column(name = "head_pic_url",  length = 300)
    private String headPicUrl;

}
