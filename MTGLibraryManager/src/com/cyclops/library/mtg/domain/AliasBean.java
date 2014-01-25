package com.cyclops.library.mtg.domain;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class AliasBean {

	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	private String alias;
	
	@NotNull
	private String language = Locale.ENGLISH.getLanguage();
	
	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		
		hcb.append(alias);
		hcb.append(language);
		
		return hcb.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AliasBean)) {
			return false;
		}
		
		AliasBean that = (AliasBean) obj;
		
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(alias, that.getAlias());
		eb.append(language, that.getAlias());
		
		return eb.isEquals();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
