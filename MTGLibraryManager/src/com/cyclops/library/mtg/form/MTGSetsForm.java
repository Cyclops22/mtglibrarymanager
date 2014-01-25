package com.cyclops.library.mtg.form;

import java.util.List;

import com.cyclops.library.mtg.form.bean.MTGSetFormBean;

public class MTGSetsForm {

	private List<MTGSetFormBean> sets;

	public List<MTGSetFormBean> getSets() {
		return sets;
	}

	public void setSets(List<MTGSetFormBean> sets) {
		this.sets = sets;
	}
}
