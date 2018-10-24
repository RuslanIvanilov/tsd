package ru.defo.managers;

import java.math.BigInteger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ru.defo.controllers.HistoryController;
import ru.defo.model.WmCar;
import ru.defo.util.HibernateUtil;

public class CarManager extends ManagerTemplate {

	public CarManager(){
		super(WmCar.class);
	}

	public WmCar parse1cFormat(String carData1C)
    {
    	WmCar car = new WmCar();
    	String carData = carData1C.trim();
    	if(carData.length()-1>1){
	    	for(int i=0; i<carData.length()-1; i++){
	    		if(Character.isWhitespace(carData.charAt(i)) && (car.getCarMark() == null)){
	    			car.setCarMark(carData.substring(0, i));
	    			car.setCarCode(carData.substring(i, carData.length()).trim());
	    			return car;
	    		}
	    	}

    	}
    	return car;

    }

	public WmCar getCarById(Long carId){
		if(carId == null) return new WmCar();
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmCar.class);
		criteria.add(Restrictions.eq("carId", carId));
		criteria.setMaxResults(1);
		WmCar car = (WmCar) criteria.uniqueResult();
		//session.close();
		if(!(car instanceof WmCar)) return new WmCar();
		return car;
	}

	public WmCar getCarByCode(String carCode){
		this.criteria.add(Restrictions.eq("carCode", carCode));
		this.criteria.setMaxResults(1);
		return (WmCar) this.criteria.uniqueResult();
	}


	public long getNextCarId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_car_id')").uniqueResult()).longValue();
	}

	public WmCar saveCar(WmCar car){
		WmCar carBase = getCarByCode(car.getCarCode());
		long nextCarId = getNextCarId();
		if(!(carBase instanceof WmCar)){
			carBase = car;
			carBase.setCarId(nextCarId);
			HibernateUtil.persist(carBase, false);
			return carBase;
		} else{
			car.setCarId(nextCarId);
		return car;
		}
	}

	public WmCar createOrUpdate(Session session, WmCar car)
	{
		boolean needUpdate = false;

		WmCar car0 = getCarByCode(car.getCarCode());
		if(car0 instanceof WmCar){
			if(car.getCarMark() != null && !(car.getCarMark().equals(car0.getCarMark()))) {
				car0.setCarMark(car.getCarMark());
				needUpdate = true;
			}
			if(car.getCarModel() != null && !(car.getCarModel().equals(car0.getCarModel()))) {
				car0.setCarModel(car.getCarModel());
				needUpdate = true;
			}
			if(needUpdate) session.update(car0);
		} else {
			car0 = new WmCar();
			car0.setCarId(getNextCarId());
			car0.setCarCode(car.getCarCode());
			car0.setCarMark(car.getCarMark());
			car0.setCarModel(car.getCarModel());
			session.persist(car0);
			new HistoryController().addLoadDataEntry(session, 0L, "WmCar/Автомобиль", car0.getCarId()+" ["+car0.getCarCode()+"]");
		}


		return car0;
	}

}
