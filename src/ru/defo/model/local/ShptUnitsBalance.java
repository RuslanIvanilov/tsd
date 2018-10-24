package ru.defo.model.local;

public class ShptUnitsBalance {
	long orderId;
	int preparedUnitsCount;
	int shippedUnitsCount;

	/**@param WmOrder.orderId
	 * */
	public ShptUnitsBalance(long orderId){
		this.orderId = orderId;
	}

	/**@param WmOrder.orderId
	 * */
	public ShptUnitsBalance(long orderId, int preparedUnitsCount, int shippedUnitsCount){
		this.orderId = orderId;
		this.preparedUnitsCount = preparedUnitsCount;
		this.shippedUnitsCount = shippedUnitsCount;
	}

	/**@return WmOrder.orderId
	 * */
	public long getOrderId() {
		return orderId;
	}

	public int getPreparedUnitsCount() {
		return preparedUnitsCount;
	}

	public void setPreparedUnitsCount(int preparedUnitsCount) {
		this.preparedUnitsCount = preparedUnitsCount;
	}

	public int getShippedUnitsCount() {
		return shippedUnitsCount;
	}

	public void setShippedUnitsCount(int shippedUnitsCount) {
		this.shippedUnitsCount = shippedUnitsCount;
	}



}
