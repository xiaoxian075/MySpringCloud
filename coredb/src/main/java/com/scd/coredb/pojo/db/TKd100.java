package com.scd.coredb.pojo.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scd.joggle.pojo.po.Kd100Po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_kd100")
public class TKd100 {

    public final static String ID = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "name", nullable = false, length = 128)
	private String name;
	
	@Column(name = "code", nullable = false, length = 64)
	private String code;
	
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


	public static List<Kd100Po> change(List<TKd100> kdList) {
		
		List<Kd100Po> poList = new ArrayList<Kd100Po>();
		for (TKd100 tKd100 : kdList) {
			Kd100Po po = createPo(tKd100);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}
	
	public static Kd100Po createPo(TKd100 tKd100) {
		if (tKd100 == null) {
			return null;
		}
		
//		return new Kd100Po(tKd100.getId(),
//				tKd100.getName(),
//				tKd100.getCode(),
//				tKd100.getCreateTime(),
//				tKd100.getUpdateTime());
		return new Kd100Po(
				tKd100.getId(),
				tKd100.getName(),
				tKd100.getCode(),
				tKd100.getCreateTime(),
				tKd100.getUpdateTime());
	}
}
