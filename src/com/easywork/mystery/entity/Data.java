package com.easywork.mystery.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Data implements Serializable {
    @Id
    private Long id;

    private String account;

    private Long userid;

    @Column(name = "url_a")
    private String urlA;

    @Column(name = "url_b")
    private String urlB;

    private Long fid;

    @Column(name = "create_time")
    private Date createTime;

    private Integer type;

    @Column(name = "father_url")
    private String fatherUrl;

    private String filename;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    public Data() {
		super();
	}

	/**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return userid
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * @return url_a
     */
    public String getUrlA() {
        return urlA;
    }

    public Data(String account, Long userid, String urlA, Date createTime, Integer type, String fatherUrl) {
		super();
		this.account = account;
		this.userid = userid;
		this.urlA = urlA;
		this.createTime = createTime;
		this.type = type;
		this.fatherUrl = fatherUrl;
	}

	public Data(String account, Long userid, String urlA, Date createTime, Integer type, String fatherUrl,
			String filename) {
		super();
		this.account = account;
		this.userid = userid;
		this.urlA = urlA;
		this.createTime = createTime;
		this.type = type;
		this.fatherUrl = fatherUrl;
		this.filename = filename;
	}

	/**
     * @param urlA
     */
    public void setUrlA(String urlA) {
        this.urlA = urlA;
    }

    /**
     * @return url_b
     */
    public String getUrlB() {
        return urlB;
    }

    /**
     * @param urlB
     */
    public void setUrlB(String urlB) {
        this.urlB = urlB;
    }

    /**
     * @return fid
     */
    public Long getFid() {
        return fid;
    }

    /**
     * @param fid
     */
    public void setFid(Long fid) {
        this.fid = fid;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return father_url
     */
    public String getFatherUrl() {
        return fatherUrl;
    }

    /**
     * @param fatherUrl
     */
    public void setFatherUrl(String fatherUrl) {
        this.fatherUrl = fatherUrl;
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

	@Override
	public String toString() {
		return "Data [id=" + id + ", account=" + account + ", userid=" + userid + ", urlA=" + urlA + ", urlB=" + urlB
				+ ", fid=" + fid + ", createTime=" + createTime + ", type=" + type + ", fatherUrl=" + fatherUrl
				+ ", filename=" + filename + "]";
	}
    
}