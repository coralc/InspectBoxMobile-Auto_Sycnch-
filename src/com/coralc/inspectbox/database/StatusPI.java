package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = StatusPI.STATUTPI_TABLE_NAME, daoClass = StatusPIDao.class)
public class StatusPI {
public static final String STATUTPI_TABLE_NAME = "StatutPI";
	
	public static final String IDTAG_FIELD_NAME = "IdTag";
	public static final String AREATAG_FIELD_NAME = "AreaTag";
	public static final String TAGNAME_FIELD_NAME = "TagName";
	public static final String TAGDESCRIPTION_FIELD_NAME = "TagDescription";
	public static final String TAGUNIT_FIELD_NAME = "TagUnit";
	public static final String VALUE_FIELD_NAME = "Value";
	public static final String STATUS_FIELD_NAME= "Status";
	public static final String LOWLIMIT_FIELD_NAME = "LowLimit";
	public static final String HIGHLIMIT_FIELD_NAME = "HighLimit";
	public static final String LOWBORDER_FIELD_NAME = "LowBorder";
	public static final String HIGHBORDER_FIELD_NAME = "HighBorder";
	public static final String DATEINFORMATION_FIELD_NAME = "Timestamp";
	public static final String CLIENTNAME_FIELD_NAME = "ClientName";
	
	@DatabaseField(generatedId = true, columnName = IDTAG_FIELD_NAME)
	private int idTag;
	@DatabaseField(columnName = AREATAG_FIELD_NAME)
	private String areaTag;
	@DatabaseField(columnName = TAGNAME_FIELD_NAME)
	private String tagName;
	@DatabaseField(columnName = TAGDESCRIPTION_FIELD_NAME)
	private String tagDescription;
	@DatabaseField(columnName = TAGUNIT_FIELD_NAME)
	private String tagUnit;
	@DatabaseField(columnName = VALUE_FIELD_NAME)
	private String value;
	@DatabaseField(columnName = STATUS_FIELD_NAME)
	private int status;
	@DatabaseField(columnName = LOWLIMIT_FIELD_NAME)
	private String lowLimit;
	@DatabaseField(columnName = HIGHLIMIT_FIELD_NAME)
	private String highLimit;
	@DatabaseField(columnName = LOWBORDER_FIELD_NAME)
	private String lowBorder;
	@DatabaseField(columnName = HIGHBORDER_FIELD_NAME)
	private String highBorder;
	@DatabaseField(columnName = DATEINFORMATION_FIELD_NAME)
	private String timestamp;
	@DatabaseField(columnName = CLIENTNAME_FIELD_NAME)
	private String clientName;
	
	
	
	public int getIdTag() {
		return idTag;
	}
	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}
	public String getAreaTag() {
		return areaTag;
	}
	public void setAreaTag(String areaTag) {
		this.areaTag = areaTag;
	}
	public String getTagName() {
		return tagName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagDescription() {
		return tagDescription;
	}
	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}
	public String getTagUnit() {
		return tagUnit;
	}
	public void setTagUnit(String tagUnit) {
		this.tagUnit = tagUnit;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
	public String getLowLimit() {
		return lowLimit;
	}
	public void setLowLimit(String lowLimit) {
		this.lowLimit = lowLimit;
	}
	public String getHighLimit() {
		return highLimit;
	}
	public void setHighLimit(String highLimit) {
		this.highLimit = highLimit;
	}
	public String getLowBorder() {
		return lowBorder;
	}
	public void setLowBorder(String lowBorder) {
		this.lowBorder = lowBorder;
	}
	public String getHighBorder() {
		return highBorder;
	}
	public void setHighBorder(String highBorder) {
		this.highBorder = highBorder;
	}
	public StatusPI() {
		// needed by ormlite
	}
public StatusPI(StatusPI i) {
		
		this.setAreaTag(i.getAreaTag());
		this.setTagName(i.getTagName());
		this.setTagDescription(i.getTagDescription());
		this.setTagUnit(i.getTagUnit());
		this.setStatus(i.getStatus());
		this.setValue(i.getValue());
		this.setLowLimit(i.getLowLimit());
		this.setHighLimit(i.getHighLimit());
		this.setLowBorder(i.getLowBorder());
		this.setHighBorder(i.getHighBorder());
		this.setTimestamp(i.getTimestamp());
		this.setClientName(i.getClientName());
		
	}
}
