package com.easywork.mystery.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "msg_info")
public class MsgInfo implements Serializable {
    @Id
    private Long id;

    private String title;

    private Integer state;

    private String content;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public MsgInfo() {
		super();
	}

	public MsgInfo(String title, Integer state, String content) {
		super();
		this.title = title;
		this.state = state;
		this.content = content;
	}

	/**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
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

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}