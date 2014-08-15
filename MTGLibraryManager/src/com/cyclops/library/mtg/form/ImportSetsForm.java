package com.cyclops.library.mtg.form;

import java.util.ArrayList;
import java.util.List;

import com.cyclops.library.mtg.form.bean.ImportSetFormBean;

public class ImportSetsForm {

	private List<ImportSetFormBean> importSetFormBeans = new ArrayList<>();

	public List<ImportSetFormBean> getImportSetFormBeans() {
		return importSetFormBeans;
	}

	public void setImportSetFormBeans(List<ImportSetFormBean> importSetFormBeans) {
		this.importSetFormBeans = importSetFormBeans;
	}
}
