package org.jfantasy.website.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.jfantasy.framework.dao.BaseBusEntity;
import org.jfantasy.system.bean.Website;
import org.jfantasy.website.bean.enums.PageType;

import java.util.List;

/**
 * 模板
 * <p/>
 * 用于生成静态页面时的thml模板配置
 *
 * @author 李茂峰
 * @version 1.0
 * @since 2013-12-25 上午9:21:05
 */
@Entity
@Table(name = "SWP_TEMPLATE", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_TEMPLATE", columnNames = {"PATH", "WEBSITE_ID"})})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "dataInferfaces", "webSite"})
public class Template extends BaseBusEntity {

    private static final long serialVersionUID = 6516821318292532274L;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = false, precision = 22, scale = 0)
    @GeneratedValue(generator = "fantasy-sequence")
    @GenericGenerator(name = "fantasy-sequence", strategy = "fantasy-sequence")
    private Long id;
    /**
     * 模板名称
     */
    @Column(name = "NAME")
    private String name;
    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;
    /**
     * 站点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WEBSITE_ID")
    private Website webSite;
    /**
     * 模板文件存放的位置
     */
    @Lob
    @Column(name = "CONTENT")
    private String content;
    /**
     * 数据接口定义
     */
    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
    @OrderBy(value = "id desc")
    private List<DataInferface> dataInferfaces;
    /**
     * 模版路径
     */
    @Column(name = "PATH", nullable = false)
    private String path;
    /**
     * 页面类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PAGE_TYPE")
    private PageType pageType;
    /**
     * Key
     */
    @Column(name = "DATA_KEY")
    private String dataKey;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataInferface> getDataInferfaces() {
        return dataInferfaces;
    }

    public void setDataInferfaces(List<DataInferface> dataInferfaces) {
        this.dataInferfaces = dataInferfaces;
    }

    public Website getWebSite() {
        return webSite;
    }

    public void setWebSite(Website webSite) {
        this.webSite = webSite;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}