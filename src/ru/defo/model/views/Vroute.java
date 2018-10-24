package ru.defo.model.views;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.util.HibernateUtil;

/**
 * Created by syn-wms on 26.11.2017.
 */
@Entity
@Table(name = "vroute", schema = "public", catalog = "wms")
public class Vroute {
    private Long routeId;
    private String routeCode;
    private Timestamp expectedDate;
    private Long statusId;
    private String statusName;
    private Long carId;
    private String carCode;
    private String carMark;
    private Long preOrderCount;
    private Long orderLinkCount;
    private Timestamp printDate;
    private Long commentId;

    public static Vroute getByRouteId(Long routeId){
    	List<SimpleExpression> restList = new LinkedList<SimpleExpression>();
		restList.add(Restrictions.eq("routeId", routeId));
		Vroute vroute = (Vroute) HibernateUtil.getUniqObject(Vroute.class,restList, true);
    	if(!(vroute instanceof Vroute)) return null;
    	return vroute;
    }

    public static List<Vroute> getVrouteList(String dateFilter, String routeFilter, String statusFilter, String carCodeFilter, String carMarkFilter, String preOrderCntFilter, String orderLinkCntFilter
    		,String printFilter, String commentFilter, int rowCount){

    	CriterionFilter filter = new CriterionFilter();
    	filter.addFilter("expectedDate", dateFilter, "date");
    	filter.addFilter("preOrderCount", preOrderCntFilter, "<>");
    	filter.addFilter("orderLinkCount", orderLinkCntFilter, "<>");
    	filter.addFilter("routeCode", routeFilter, "like");
    	filter.addFilter("statusName", statusFilter, "like");
    	filter.addFilter("carCode", carCodeFilter, "like");
    	filter.addFilter("carMark", carMarkFilter, "like");
    	filter.addFilter("printDate", printFilter, "nullDate");
    	filter.addFilter("commentId", commentFilter, "<>");

    	List<Vroute> vrouteList = HibernateUtil.getObjectList(Vroute.class, filter.getFilterList(), rowCount, true, "");

    	return vrouteList;
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
    @Column(name = "car_id", nullable = true)
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "car_code", nullable = true, length = 250)
    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    @Basic
    @Column(name = "car_mark", nullable = true, length = 250)
    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    @Basic
    @Column(name = "pre_order_count", nullable = true)
    public Long getPreOrderCount() {
        return preOrderCount;
    }

    public void setPreOrderCount(Long preOrderCount) {
        this.preOrderCount = preOrderCount;
    }

    @Basic
    @Column(name = "order_link_count", nullable = true)
    public Long getOrderLinkCount() {
        return orderLinkCount;
    }

    public void setOrderLinkCount(Long orderLinkCount) {
        this.orderLinkCount = orderLinkCount;
    }


    @Basic
    @Column(name = "print_date", nullable = true)
    public Timestamp getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Timestamp printDate) {
		this.printDate = printDate;
	}

	@Basic
    @Column(name = "comment_id", nullable = true)
	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vroute vroute = (Vroute) o;

        if (routeId != null ? !routeId.equals(vroute.routeId) : vroute.routeId != null) return false;
        if (routeCode != null ? !routeCode.equals(vroute.routeCode) : vroute.routeCode != null) return false;
        if (expectedDate != null ? !expectedDate.equals(vroute.expectedDate) : vroute.expectedDate != null)
            return false;
        if (statusId != null ? !statusId.equals(vroute.statusId) : vroute.statusId != null) return false;
        if (statusName != null ? !statusName.equals(vroute.statusName) : vroute.statusName != null) return false;
        if (carId != null ? !carId.equals(vroute.carId) : vroute.carId != null) return false;
        if (carCode != null ? !carCode.equals(vroute.carCode) : vroute.carCode != null) return false;
        if (carMark != null ? !carMark.equals(vroute.carMark) : vroute.carMark != null) return false;
        if (preOrderCount != null ? !preOrderCount.equals(vroute.preOrderCount) : vroute.preOrderCount != null)
            return false;
        if (orderLinkCount != null ? !orderLinkCount.equals(vroute.orderLinkCount) : vroute.orderLinkCount != null)
            return false;
        if (printDate != null ? !printDate.equals(vroute.printDate) : vroute.printDate != null)
            return false;
        if (commentId != null ? !commentId.equals(vroute.commentId) : vroute.commentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = routeId != null ? routeId.hashCode() : 0;
        result = 31 * result + (routeCode != null ? routeCode.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        result = 31 * result + (carCode != null ? carCode.hashCode() : 0);
        result = 31 * result + (carMark != null ? carMark.hashCode() : 0);
        result = 31 * result + (preOrderCount != null ? preOrderCount.hashCode() : 0);
        result = 31 * result + (orderLinkCount != null ? orderLinkCount.hashCode() : 0);
        result = 31 * result + (printDate != null ? printDate.hashCode() : 0);
        result = 31 * result + (commentId != null ? commentId.hashCode() : 0);
        return result;
    }
}
