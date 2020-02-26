package models;

import java.io.Serializable;

/**
 * This class describes the OrderDetails object.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class OrderDetails implements Serializable {

    private Integer detailsId;
    private Integer orderId;
    private Integer productId;

    public OrderDetails() {
    }

    public OrderDetails(Integer orderId, Integer productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public OrderDetails(Integer detailsId, Integer orderId, Integer productId) {
        this.detailsId = detailsId;
        this.orderId = orderId;
        this.productId = productId;
    }

    public Integer getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Integer detailsId) {
        this.detailsId = detailsId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return String.format("OrderDetails: Id = %s, order Id = %s, product Id = %s.", detailsId, orderId, productId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result += prime * detailsId.hashCode();
        result += prime * orderId.hashCode();
        result += prime * productId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof OrderDetails))
            return false;
        OrderDetails other = (OrderDetails) obj;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        if (detailsId == null) {
            if (other.detailsId != null)
                return false;
        } else if (!detailsId.equals(other.detailsId))
            return false;
        return true;
    }

}