package com.ultra.util;

public class WaitDealBean {
	private String record;
	private String title;
	private String build;
	private String status;
	private String priority;
	private String type;
	private String begin;
	private String group;
	private String dispose;
	private String end;
	private String url;

	public WaitDealBean() {
	}

	public WaitDealBean(String record, String title, String build,
			String status, String priority, String type, String begin,
			String group, String dispose, String end, String url) {
		this.record = record;
		this.title = title;
		this.build = build;
		this.status = status;
		this.priority = priority;
		this.type = type;
		this.begin = begin;
		this.group = group;
		this.dispose = dispose;
		this.end = end;
		this.url = url;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
