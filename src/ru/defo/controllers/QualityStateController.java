package ru.defo.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import ru.defo.managers.QualityStateManager;
import ru.defo.model.WmQualityState;

public class QualityStateController {

	List<WmQualityState> listQualityState;

	public List<WmQualityState> getQualityStateList()
	{
		return new QualityStateManager().getAll();
	}

	public String getQualityStateDescriptionById(Long qualityStateId){
		return getQStateById(qualityStateId).getDescription();
	}

	public WmQualityState getQStateById(Long qualityStateId){
		return new QualityStateManager().getQualityStateById(qualityStateId);
	}

	public HashMap<Integer, String> getQualityStateMapAll()
	{
		HashMap<Integer, String> qltStateMap = new HashMap<Integer, String>();
		List<WmQualityState> stateList = getQualityStateList();
		for(WmQualityState state : stateList){
			qltStateMap.put(state.getQualityStateId().intValue(), state.getDescription());
		}

		return qltStateMap;
	}

	private WmQualityState getState(Object obj){
		Long stateId = Long.valueOf(obj.toString());
		return new QualityStateManager().getQualityStateById(stateId);
	}


	public WmQualityState getState(HttpSession httpSession){
		return getState(httpSession.getAttribute("state"));
	}

}