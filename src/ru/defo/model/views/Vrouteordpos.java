package ru.defo.model.views;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.Session;

import ru.defo.filters.CriterionFilter;
import ru.defo.util.HibernateUtil;

/**
 * Created by syn-wms on 29.11.2017.
 */
@Entity
@IdClass(VrouteordposPK.class)
public class Vrouteordpos {
    private Long orderId;
    private Long orderPosId;
    private String preOrderCode;
    private Long preOrderId;
    private Long preOrderPosId;
    private Long routeId;
    private String routeCode;
    private Timestamp expectedDate;
    private Long articleId;
    private String articleCode;
    private String articleName;
    private Long skuId;
    private String skuName;
    private Long expectQuantity;
    private Long factQuantity;
    private Long qualityStateId;
    private Long statusId;
    private String statusName;
    private Long errorId;
    private String errorComment;
    private Long createUser;
    private String firstName;
    private String surname;
    private Timestamp createDate;
    private Long vendorGroupId;
    private String vendorGroupName;
    private String carCode;

    public static List<Vrouteordpos> getVRouteOrdPosList(String vendorGroupIdTxt,String dateFilter, String carCodeFilter, String routelist){
		CriterionFilter filter = new CriterionFilter();
    	filter.addFilter("expectedDate", dateFilter, "date");

    	if(vendorGroupIdTxt != null && vendorGroupIdTxt.isEmpty()==false)
    		filter.addFilter("vendorGroupId", vendorGroupIdTxt, "eq");

    	if(carCodeFilter != null && carCodeFilter.isEmpty()==false)
    		filter.addFilter("carCode", carCodeFilter, "like");

    	if(vendorGroupIdTxt == null && carCodeFilter == null){
    		System.out.println("Нужно отработать по списку заявок на доставку все группы товаров!");
    		throw null;
    	}

    	if(routelist !=null){
    		filter.addFilter("routeId",routelist,"in");
    	}

    	List<Vrouteordpos> vrouteOrdPosList = HibernateUtil.getObjectList(Vrouteordpos.class, filter.getFilterList(), 0, true,"");
    	Session session = HibernateUtil.getSession();
    	for(Vrouteordpos routeordpos : vrouteOrdPosList){
    		session.refresh(routeordpos);
    	}

    	return vrouteOrdPosList;
	}

    @Id
    @Column(name = "order_id", nullable = true)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "order_pos_id", nullable = true)
    public Long getOrderPosId() {
        return orderPosId;
    }

    public void setOrderPosId(Long orderPosId) {
        this.orderPosId = orderPosId;
    }

    @Id
    @Column(name = "pre_order_id", nullable = true)
    public Long getPreOrderId() {
        return preOrderId;
    }

    public void setPreOrderId(Long preOrderId) {
        this.preOrderId = preOrderId;
    }

    @Id
    @Column(name = "pre_order_pos_id", nullable = true)
    public Long getPreOrderPosId() {
        return preOrderPosId;
    }

    public void setPreOrderPosId(Long preOrderPosId) {
        this.preOrderPosId = preOrderPosId;
    }

    @Basic
    @Column(name = "pre_order_code", nullable = true, length = 50)
    public String getPreOrderCode() {
        return preOrderCode;
    }

    public void setPreOrderCode(String preOrderCode) {
        this.preOrderCode = preOrderCode;
    }


    @Id
    @Column(name = "route_id", nullable = true)
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "route_code", nullable = true, length = 50)
    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    @Basic
    @Column(name = "expected_date", nullable = true)
    public Timestamp getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Timestamp expectedDate) {
        this.expectedDate = expectedDate;
    }

    @Basic
    @Column(name = "article_id", nullable = true)
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Basic
    @Column(name = "article_code", nullable = true, length = 50)
    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    @Basic
    @Column(name = "article_name", nullable = true, length = 250)
    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    @Basic
    @Column(name = "sku_id", nullable = true)
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Basic
    @Column(name = "sku_name", nullable = true, length = 250)
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Basic
    @Column(name = "expect_quantity", nullable = true)
    public Long getExpectQuantity() {
        return expectQuantity;
    }

    public void setExpectQuantity(Long expectQuantity) {
        this.expectQuantity = expectQuantity;
    }

    @Basic
    @Column(name = "fact_quantity", nullable = true)
    public Long getFactQuantity() {
        return factQuantity;
    }

    public void setFactQuantity(Long factQuantity) {
        this.factQuantity = factQuantity;
    }

    @Basic
    @Column(name = "quality_state_id", nullable = true)
    public Long getQualityStateId() {
        return qualityStateId;
    }

    public void setQualityStateId(Long qualityStateId) {
        this.qualityStateId = qualityStateId;
    }

    @Basic
    @Column(name = "status_id", nullable = true)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "status_name", nullable = true, length = 250)
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Basic
    @Column(name = "error_id", nullable = true)
    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    @Basic
    @Column(name = "error_comment", nullable = true, length = 250)
    public String getErrorComment() {
        return errorComment;
    }

    public void setErrorComment(String errorComment) {
        this.errorComment = errorComment;
    }

    @Basic
    @Column(name = "create_user", nullable = true)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 250)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "surname", nullable = true, length = 250)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "vendor_group_id", nullable = true)
    public Long getVendorGroupId() {
        return vendorGroupId;
    }

    public void setVendorGroupId(Long vendorGroupId) {
        this.vendorGroupId = vendorGroupId;
    }

    @Basic
    @Column(name = "vendor_group_name", nullable = true, length = 250)
    public String getVendorGroupName() {
        return vendorGroupName;
    }

    public void setVendorGroupName(String vendorGroupName) {
        this.vendorGroupName = vendorGroupName;
    }

    @Basic
    @Column(name = "car_code", nullable = true, length = 250)
    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vrouteordpos that = (Vrouteordpos) o;

        if (routeId != null ? !routeId.equals(that.routeId) : that.routeId != null) return false;
        if (routeCode != null ? !routeCode.equals(that.routeCode) : that.routeCode != null) return false;
        if (expectedDate != null ? !expectedDate.equals(that.expectedDate) : that.expectedDate != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(that.orderPosId) : that.orderPosId != null) return false;

        if (preOrderId != null ? !preOrderId.equals(that.preOrderId) : that.preOrderId != null) return false;
        if (preOrderPosId != null ? !preOrderPosId.equals(that.preOrderPosId) : that.preOrderPosId != null) return false;

        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (articleCode != null ? !articleCode.equals(that.articleCode) : that.articleCode != null) return false;
        if (articleName != null ? !articleName.equals(that.articleName) : that.articleName != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (skuName != null ? !skuName.equals(that.skuName) : that.skuName != null) return false;
        if (expectQuantity != null ? !expectQuantity.equals(that.expectQuantity) : that.expectQuantity != null)
            return false;
        if (factQuantity != null ? !factQuantity.equals(that.factQuantity) : that.factQuantity != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(that.qualityStateId) : that.qualityStateId != null)
            return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (statusName != null ? !statusName.equals(that.statusName) : that.statusName != null) return false;
        if (errorId != null ? !errorId.equals(that.errorId) : that.errorId != null) return false;
        if (errorComment != null ? !errorComment.equals(that.errorComment) : that.errorComment != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (vendorGroupId != null ? !vendorGroupId.equals(that.vendorGroupId) : that.vendorGroupId != null)
            return false;
        if (vendorGroupName != null ? !vendorGroupName.equals(that.vendorGroupName) : that.vendorGroupName != null)
            return false;
        if (carCode != null ? !carCode.equals(that.carCode) : that.carCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = routeId != null ? routeId.hashCode() : 0;
        result = 31 * result + (routeCode != null ? routeCode.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);

        result = 31 * result + (preOrderId != null ? preOrderId.hashCode() : 0);
        result = 31 * result + (preOrderPosId != null ? preOrderPosId.hashCode() : 0);

        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (articleCode != null ? articleCode.hashCode() : 0);
        result = 31 * result + (articleName != null ? articleName.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (expectQuantity != null ? expectQuantity.hashCode() : 0);
        result = 31 * result + (factQuantity != null ? factQuantity.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        result = 31 * result + (errorId != null ? errorId.hashCode() : 0);
        result = 31 * result + (errorComment != null ? errorComment.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (vendorGroupId != null ? vendorGroupId.hashCode() : 0);
        result = 31 * result + (vendorGroupName != null ? vendorGroupName.hashCode() : 0);
        result = 31 * result + (carCode != null ? carCode.hashCode() : 0);
        return result;
    }
}
