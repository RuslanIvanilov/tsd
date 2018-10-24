package ru.defo.managers;

import ru.defo.model.WmEquipment;

public class EquipmentManager extends ManagerTemplate {

	public EquipmentManager(){
		super();
	}
	
	public WmEquipment getEquipmentByIp(String ipAddressCode){		
		return (WmEquipment) session.createQuery("from WmEquipment where ipAddressCode = :ipAddressCode").setParameter("ipAddressCode", ipAddressCode).uniqueResult();
	}
	
}
