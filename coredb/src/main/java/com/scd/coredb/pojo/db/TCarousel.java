package com.scd.coredb.pojo.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scd.joggle.pojo.po.CarouselPo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_carousel")
public class TCarousel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;

	/**
	 * 类型 1：云社区 2：零距离
	 */
	@Column(name = "type", nullable = false, length = 11)
	private int type;
	
	/**
	 * url
	 */
	@Column(name = "url", nullable = false, length = 255)
	private String url;
	
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

	public CarouselPo createPo() {
		return new CarouselPo(
				id,
				type,
				url,
				createTime,
				updateTime
				);
	}
	
	public static List<CarouselPo> change(List<TCarousel> tList) {
		
		List<CarouselPo> poList = new ArrayList<CarouselPo>();
		for (TCarousel tCarousel : tList) {
			CarouselPo po = createPo(tCarousel);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}
	
	public static CarouselPo createPo(TCarousel tCarousel) {
		if (tCarousel == null) {
			return null;
		}
		
		return new CarouselPo(tCarousel.getId(),
				tCarousel.getType(),
				tCarousel.getUrl(),
				tCarousel.getCreateTime(),
				tCarousel.getUpdateTime());
	}
}
