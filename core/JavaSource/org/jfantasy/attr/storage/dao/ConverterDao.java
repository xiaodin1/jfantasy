package org.jfantasy.attr.storage.dao;

import org.springframework.stereotype.Repository;

import org.jfantasy.attr.storage.bean.Converter;
import org.jfantasy.framework.dao.hibernate.HibernateDao;

@Repository
public class ConverterDao extends HibernateDao<Converter, Long>{

}
