package com.fantasy.swp.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * 数据接口定义
 */
@Entity
@Table(name = "SWP_DATA_INFERFACE")
public class DataInferface {

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = false, precision = 22, scale = 0)
    @GeneratedValue(generator = "fantasy-sequence")
    @GenericGenerator(name = "fantasy-sequence", strategy = "fantasy-sequence")
    private Long id;
    /**
     * 数据接口对应的模板
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID",foreignKey = @ForeignKey(name = "FK_DATA_INFERFACE_TEMPLATE"))
    private Template template;
    /**
     * 数据在模板文件中的key
     */
    @Column(name = "CODE")
    private String key;
    /**
     * 表述名称
     */
    @Column(name = "NAME")
    private String name;
    /**
     * 是否为集合
     */
    @Column(name = "is_LIST")
    private boolean list;
    /**
     * 数据对应的javaType
     */
    @Column(name = "JAVA_TYPE")
    private String javaType;
    /**
     * 数据默认值
     */
    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

    @OneToMany(mappedBy = "dataInferface", fetch = FetchType.LAZY)
    private List<Data> datas;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }
}
