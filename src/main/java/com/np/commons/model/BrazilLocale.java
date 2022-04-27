package com.np.commons.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Locale", description = "SchemaInfo (to Data Transfer): Data Model Specifications")
public class BrazilLocale 
{
	@ApiModelProperty(value = "name for this locale country", required = true)
	private String countryName;

	@ApiModelProperty(value = "code for this locale country", required = true)
	private String countryCode;

	@ApiModelProperty(value = "abbreviation for this locale country", required = true)
	private String countryAbbreviation;

	@ApiModelProperty(value = "name for this locale (con)federation unit", required = true)
	private String fuName;

	@ApiModelProperty(value = "code for this locale (con)federation unit", required = true)
	private String fuCode;

	@ApiModelProperty(value = "abbreviation for this locale (con)federation unit", required = true)
	private String fuAbbreviation;

	@ApiModelProperty(value = "name for this locale county", required = true)
	private String countyName;

	@ApiModelProperty(value = "code for this locale county", required = true)
	private String countyCode;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}

	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}

	public String getFuName() {
		return fuName;
	}

	public void setFuName(String fuName) {
		this.fuName = fuName;
	}

	public String getFuCode() {
		return fuCode;
	}

	public void setFuCode(String fuCode) {
		this.fuCode = fuCode;
	}

	public String getFuAbbreviation() {
		return fuAbbreviation;
	}

	public void setFuAbbreviation(String fuAbbreviation) {
		this.fuAbbreviation = fuAbbreviation;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

}
