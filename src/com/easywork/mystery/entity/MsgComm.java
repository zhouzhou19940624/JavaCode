package com.easywork.mystery.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "msg_comm")
public class MsgComm implements Serializable {
    @Id
    private Long id;

    @Column(name = "receiverId")
    private Long receiverid;

    @Column(name = "receiverAccount")
    private String receiveraccount;

    @Column(name = "senderId")
    private Long senderid;

    @Column(name = "senderAccount")
    private String senderaccount;

    @Column(name = "msgId")
    private Long msgid;

    @Column(name = "msgTitle")
    private String msgtitle;

    private Integer readflag;

    private Date createtime;

    private Integer state;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    public MsgComm() {
		super();
	}

	public MsgComm(Long receiverid, String receiveraccount, Long senderid, String senderaccount, Long msgid,
			String msgtitle, Integer readflag, Date createtime,Integer state) {
		super();
		this.receiverid = receiverid;
		this.receiveraccount = receiveraccount;
		this.senderid = senderid;
		this.senderaccount = senderaccount;
		this.msgid = msgid;
		this.msgtitle = msgtitle;
		this.readflag = readflag;
		this.createtime = createtime;
		this.state = state;
	}

	/**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return receiverId
     */
    public Long getReceiverid() {
        return receiverid;
    }

    /**
     * @param receiverid
     */
    public void setReceiverid(Long receiverid) {
        this.receiverid = receiverid;
    }

    /**
     * @return receiverAccount
     */
    public String getReceiveraccount() {
        return receiveraccount;
    }

    /**
     * @param receiveraccount
     */
    public void setReceiveraccount(String receiveraccount) {
        this.receiveraccount = receiveraccount;
    }

    /**
     * @return senderId
     */
    public Long getSenderid() {
        return senderid;
    }

    /**
     * @param senderid
     */
    public void setSenderid(Long senderid) {
        this.senderid = senderid;
    }

    /**
     * @return senderAccount
     */
    public String getSenderaccount() {
        return senderaccount;
    }

    /**
     * @param senderaccount
     */
    public void setSenderaccount(String senderaccount) {
        this.senderaccount = senderaccount;
    }

    /**
     * @return msgId
     */
    public Long getMsgid() {
        return msgid;
    }

    /**
     * @param msgid
     */
    public void setMsgid(Long msgid) {
        this.msgid = msgid;
    }

    /**
     * @return msgTitle
     */
    public String getMsgtitle() {
        return msgtitle;
    }

    /**
     * @param msgtitle
     */
    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle;
    }

    /**
     * @return readflag
     */
    public Integer getReadflag() {
        return readflag;
    }

    /**
     * @param readflag
     */
    public void setReadflag(Integer readflag) {
        this.readflag = readflag;
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }
}