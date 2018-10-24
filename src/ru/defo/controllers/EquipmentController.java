package ru.defo.controllers;

import ru.defo.managers.EquipmentManager;
import ru.defo.model.WmEquipment;

public class EquipmentController {

	EquipmentManager eqMgr;
	
	public EquipmentController(){
		eqMgr = new EquipmentManager();
	}
			
	public WmEquipment getEquipmentByIp(String ipAddress){				
		return eqMgr.getEquipmentByIp(ipAddress);
	}
	
	
}
