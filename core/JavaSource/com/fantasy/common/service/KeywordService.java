package com.fantasy.common.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fantasy.common.bean.HotKeywords;
import com.fantasy.common.bean.Keywords;
import com.fantasy.common.bean.enums.TimeUnit;
import com.fantasy.common.dao.HotKeywordsDao;
import com.fantasy.common.dao.KeywordsDao;
import com.fantasy.framework.dao.Pager;
import com.fantasy.framework.dao.hibernate.PropertyFilter;
import com.fantasy.framework.util.common.DateUtil;
import com.fantasy.framework.util.common.ObjectUtil;
import com.fantasy.framework.util.common.StringUtil;

/**
 * 热门搜索关键词
 */
@Service
@Transactional
public class KeywordService implements InitializingBean {

	@Resource
	private KeywordsDao keywordsDao;
	@Resource
	private HotKeywordsDao hotKeywordsDao;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * 获取热门关键字
	 * 
	 * @功能描述
	 * @param timeUnit
	 * @param target
	 * @param size
	 * @return
	 */
	public String[] getKeywords(String key, TimeUnit timeUnit, String time, int size) {
		List<HotKeywords> hotSearch = this.hotKeywordsDao.find(new Criterion[] { Restrictions.eq("key", key), Restrictions.eq("timeUnit", timeUnit), Restrictions.eq("time", time) }, "hitCount", "desc", 0, size);
		return ObjectUtil.toFieldArray(hotSearch, "Keywords", String.class);
	}

	/**
	 * 关键词
	 * 
	 * @功能描述 该功能会自动将关键词保存到 HotKeywords
	 * @param key
	 * @param text
	 */
	@SuppressWarnings("unchecked")
	public List<String> analyze(String key, String text) {
		if (StringUtil.isBlank(text)) {
			return Collections.EMPTY_LIST;
		}
		List<String> keywords = ObjectUtil.analyze(text);
		for (String words : keywords) {
			this.addHotKeywords(key, words);
		}
		return keywords;
	}

	public void addKeywords(String keywords) {

	}

	public void removeKeywords(String keywords) {

	}

	/**
	 * 保存单个关键字
	 * 
	 * @功能描述
	 * @param key
	 * @param keywords
	 */
	public void addHotKeywords(String key, String keywords) {
		String time = DateUtil.format("yyyyMMdd");
		HotKeywords hotKeywords = this.hotKeywordsDao.findUnique(Restrictions.eq("key", key), Restrictions.eq("keywords", keywords), Restrictions.eq("timeUnit", TimeUnit.day), Restrictions.eq("time", time));
		if (hotKeywords == null) {
			hotKeywords = new HotKeywords();
			hotKeywords.setKey(key);
			hotKeywords.setKeywords(keywords);
			hotKeywords.setTimeUnit(TimeUnit.day);
			hotKeywords.setTime(time);
			hotKeywords.setHitCount(0);
		}
		hotKeywords.setHitCount(hotKeywords.getHitCount() + 1);
		this.hotKeywordsDao.save(hotKeywords);
	}
	
	public Pager<Keywords> findPager(Pager<Keywords> pager,List<PropertyFilter> filters){
		return this.keywordsDao.findPager(pager, filters);
	}

}