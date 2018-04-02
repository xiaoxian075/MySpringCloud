package com.scd.coredb.pojo.db;

import com.scd.joggle.pojo.po.MessagePo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_message")
public class TMessage {

    public final static String ID = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
    @Column(name = "msg_type")
    private int msgType;
    @Column(name = "msg_title")
    private String msgTitle;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "msg_content")
    private String msgContent;
    @Column(name = "push")
    private int push;
    @Column(name = "del")
    private int del;
    @Column(name = "create_time")
    private long createTime;


	public static List<MessagePo> change(List<TMessage> kdList) {
		
		List<MessagePo> poList = new ArrayList<MessagePo>();
		for (TMessage tMessage : kdList) {
            MessagePo po = createPo(tMessage);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}
	
	public static MessagePo createPo(TMessage tMessage) {
		if (tMessage == null) {
			return null;
		}

		return new MessagePo(
                tMessage.getId(),
                tMessage.getMsgType(),
                tMessage.getMsgTitle(),
                tMessage.getIntroduction(),
                tMessage.getMsgContent(),
                tMessage.getPush(),
                tMessage.getDel(),
                tMessage.getCreateTime()
        );
	}
}
