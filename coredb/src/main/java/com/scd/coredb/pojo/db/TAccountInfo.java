package com.scd.coredb.pojo.db;

import com.scd.joggle.pojo.po.AccountPo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_account_info")
public class TAccountInfo {

    public final static String ID = "id";
	public static final String ACCOUNT = "account";
    public static final String REFEREE = "referee";
    public static final String CREATE_TIME = "createTime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    /**
     * 账号
     */
    @Column(name = "account", nullable = false, length = 64)
    private String account;

    /**
     * 昵称
     */
    @Column(name = "nick_name", nullable = false, length = 64)
    private String nickName;

    /**
     * 头像URL
     */
    @Column(name = "head_url", nullable = false, length = 255)
    private String headUrl;

    /**
     * 推荐人
     */
    @Column(name = "referee", nullable = false, length = 64)
    private String referee;

    /**
     * 性别 0：无 1：男 2：女
     */
    @Column(name = "sex", nullable = false, length = 11)
    private int sex;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false, length = 20)
    private long createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", nullable = false, length = 20)
    private long updateTime;

    /**
     * 支付密码
     */
    @Column(name = "pay_password", length = 50)
    private String payPassword;
    /**
     * 是否推送消息
     */
    @Column(name = "is_push_msg", nullable = false, length = 20)
    private int isPushMsg;
    /**
     * 账号状态
     */
    @Column(name = "account_state", nullable = false, length = 20)
    private int accountState;

    /**
     * 等级 1：普通会员 2：VIP会员
     */
    @Column(name = "level", nullable = false, length = 4)
    private int level;

    public static List<AccountPo> change(List<TAccountInfo> kdList) {

        List<AccountPo> poList = new ArrayList<AccountPo>();
        for (TAccountInfo tAccountInfo : kdList) {
            AccountPo po = createPo(tAccountInfo);
            if (po != null) {
                poList.add(po);
            }
        }
        return poList;
    }

    public static AccountPo createPo(TAccountInfo tAccountInfo) {
        if (tAccountInfo == null) {
            return null;
        }

        return new AccountPo(tAccountInfo.getId(),
                tAccountInfo.getAccount(), tAccountInfo.getNickName(), tAccountInfo.getHeadUrl(), tAccountInfo.getReferee(), tAccountInfo.getSex(), tAccountInfo.getAccountState());
    }


}
