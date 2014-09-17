package com.fantasy.attr.bean;

import com.fantasy.framework.dao.BaseBusEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Attribute 转换器
 *
 * @author 李茂峰
 * @version 1.0
 * @since 2013-2-1 下午03:09:05
 */
@Entity
@Table(name = "ATTR_CONVERTER")
public class Converter extends BaseBusEntity {

    private static final long serialVersionUID = -1025801007920540587L;

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(generator = "fantasy-sequence")
    @GenericGenerator(name = "fantasy-sequence", strategy = "fantasy-sequence")
    private Long id;

    @Column(name = "TYPE_CONVERTER", length = 200)
    private String typeConverter;

    @Column(name = "NAME", length = 200)
    private String name;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @OneToMany(mappedBy = "converter", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<AttributeType> attributeTypes;

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeConverter() {
        return typeConverter;
    }

    public void setTypeConverter(String typeConverter) {
        this.typeConverter = typeConverter;
    }

    public List<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public void setAttributeTypes(List<AttributeType> attributeTypes) {
        this.attributeTypes = attributeTypes;
    }

}
