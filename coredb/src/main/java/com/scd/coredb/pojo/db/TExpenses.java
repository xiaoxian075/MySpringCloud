package com.scd.coredb.pojo.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import com.scd.joggle.pojo.po.ExpensesPo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_expenses")
public class TExpenses {

    public final static String ID = "id";
    public final static String COMMODITY_ADDR_ID = "commodityAddrId";
    public final static String BULL_ADDR_ID = "bullAddrId";
    public final static String CREATE_TIME = "createTime";
    public final static String UPDATE_TIME = "updateTime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    @Column(name = "bull_addr_id", nullable = false, length = 20)
    private long bullAddrId;

    @Column(name = "bull_addr_name", nullable = false, length = 255)
    private String bullAddrName;

    @Column(name = "commodity_addr_id", nullable = false, length = 20)
    private long commodityAddrId;

    @Column(name = "commodity_addr_name", nullable = false, length = 255)
    private String commodityAddrName;

    @Digits(integer = 11, fraction = 2)
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "create_time", nullable = false, length = 20)
    private long createTime;

    @Column(name = "update_time", nullable = false, length = 20)
    private long updateTime;

    public static List<ExpensesPo> change(List<TExpenses> kdList) {

        List<ExpensesPo> poList = new ArrayList<ExpensesPo>();
        for (TExpenses tExpenses : kdList) {
            ExpensesPo po = createPo(tExpenses);
            if (po != null) {
                poList.add(po);
            }
        }
        return poList;
    }

    public static ExpensesPo createPo(TExpenses tExpenses) {
        if (tExpenses == null) {
            return null;
        }

        return new ExpensesPo(tExpenses.getId(),
                tExpenses.getBullAddrId(), tExpenses.getBullAddrName(), tExpenses.getCommodityAddrId(), tExpenses.getCommodityAddrName(), tExpenses.getPrice(), tExpenses.getCreateTime(), tExpenses.getUpdateTime());
    }
}

