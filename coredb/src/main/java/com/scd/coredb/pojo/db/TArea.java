package com.scd.coredb.pojo.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.joggle.pojo.po.AreaPo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_area")
public class TArea {
	
	public final static String LEVEl = "level";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;

	@Column(name = "parent_id", nullable = false, length = 20)
	private long parentId;
	
	@Column(name = "name", nullable = false, length = 128)
	private String name;
	
	@Column(name = "level", nullable = false, length = 11)
	private int level;

	@Column(name = "post_code", nullable = false, length = 8)
	private String postCode;

	@Column(name = "full_id", nullable = false, length = 64)
	private String fullId;
	
	@Column(name = "full_name", nullable = false, length = 255)
	private String fullName;
	
	@Column(name = "state", nullable = false, length = 11)
	private String state;
	
	@Column(name = "en_name", nullable = false, length = 128)
	private String enName;
	
	@Column(name = "word_index", nullable = false, length = 1)
	private String wordIndex;

	public AreaShortPojo createShort() {
		return new AreaShortPojo(id, name);
	}
	
	public AreaPo createPo() {
		return new AreaPo(id, parentId, name, level);
	}
}
