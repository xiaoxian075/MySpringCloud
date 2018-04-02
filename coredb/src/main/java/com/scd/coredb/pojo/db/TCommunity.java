package com.scd.coredb.pojo.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.po.CommunityPo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_community")
public class TCommunity {
	
	public final static String ID = "id";
	public final static String TITLE = "title";
	public final static String TYPE = "type";
	public final static String URL = "url";
	public final static String HIT_NUM = "hitNum";
	public final static String PRAISE_NUM = "praiseNum";
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	/**
	 * 标题
	 */
	@Column(name = "title", nullable = false, length = 255)
	private String title;
	
	/**
	 * 类型 1：学习教育 2：美食分享 3：生活健康 4：健身健美
	 */
	@Column(name = "type", nullable = false, length = 11)
	private int type;
	
	/**
	 * url
	 */
	@Column(name = "url", nullable = false, length = 255)
	private String url;
	
	/**
	 * 点击量
	 */
	@Column(name = "hit_num", nullable = false, length = 20)
	private long hitNum;
	
	/**
	 * 点赞量
	 */
	@Column(name = "praise_num", nullable = false, length = 20)
	private long praiseNum;
	
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
	
	public CommunityPo createPo() {
		return createPo(false);
	}

	/**
	 * 
	 * @param isPraise 是否已点赞
	 * @return
	 */
	public CommunityPo createPo(boolean isPraise) {
		int hasPraise = Type.PRAISE_CANCEL;
		if (isPraise) {
			hasPraise = Type.PRAISE_HIT;
		}
		return new CommunityPo(
				id,
				title,
				type,
				url,
				hasPraise,
				hitNum,
				praiseNum,
				createTime,
				updateTime);
	}

	public static TCommunity createNew(CommunityPo communityPo) {
		return new TCommunity(
				communityPo.getId(),
				communityPo.getTitle(),
				communityPo.getType(),
				communityPo.getUrl(),
				communityPo.getHitNum(),
				communityPo.getPraiseNum(),
				communityPo.getCreateTime(),
				communityPo.getUpdateTime());
	}

	public static List<CommunityPo> change(List<TCommunity> tList) {
		List<CommunityPo> poList = new ArrayList<CommunityPo>();
		for (TCommunity tCommunity : tList) {
			CommunityPo po = createPo(tCommunity);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}

	private static CommunityPo createPo(TCommunity tCommunity) {
		return new CommunityPo(
				tCommunity.getId(),
				tCommunity.getTitle(),
				tCommunity.getType(),
				tCommunity.getUrl(),
				Type.PRAISE_CANCEL,
				tCommunity.getHitNum(),
				tCommunity.getPraiseNum(),
				tCommunity.getCreateTime(),
				tCommunity.getUpdateTime());
	}
}
