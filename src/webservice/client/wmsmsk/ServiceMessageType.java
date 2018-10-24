package webservice.client.wmsmsk;

public enum ServiceMessageType {
	SHIPMENT("OrdersShipments"),
	ADVICE("AdvicesInvoice"),
	ARTICLE("SkladskoySostav"),
	MOVEMENT("OrdersMovements");

	private final String typeName;

	ServiceMessageType(String typeName){ this.typeName = typeName; }

	public String typeName(){
		return typeName;
	}

	public static ServiceMessageType[] getMessageTypesArray(){
		return ServiceMessageType.values();
	}

}
