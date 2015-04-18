package com.cyclops.library.mtg.service;

import java.util.Map;

import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.form.bean.SinglePriceFormBean;

public interface PriceMgtService {

	Map<String, SinglePriceFormBean> getPrices(SetFormBean setFormBean);

}
