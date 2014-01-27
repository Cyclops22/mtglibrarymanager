package com.cyclops.library.mtg.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class LibrarySetBean {

	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne
	@JoinColumn(name="set_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private SetBean referencedSet;
	
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name="library_set_id", referencedColumnName="id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<LibraryCardBean> cards;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SetBean getReferencedSet() {
		return referencedSet;
	}

	public void setReferencedSet(SetBean referencedSet) {
		this.referencedSet = referencedSet;
	}

	public List<LibraryCardBean> getCards() {
		return cards;
	}

	public void setCards(List<LibraryCardBean> cards) {
		this.cards = cards;
	}
}
