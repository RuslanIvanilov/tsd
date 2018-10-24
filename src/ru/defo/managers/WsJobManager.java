package ru.defo.managers;


import ru.defo.model.WsJob;
import ru.defo.util.AppUtil;
import ru.defo.util.HibernateUtil;
import ru.defo.util.TimeType;

public class WsJobManager extends ManagerTemplate {

	/** НЕДОПИЛЕНО !!! раскомментить и допилить!
	 * */
	/*
	public WsJob getOrCreateByCode(String wsClientCode, Long userId)
	{

		WsJob job = (WsJob)HibernateUtil.getUniqObject(WsJob.class, restList);

		if(!(job instanceof WsJob)) return create(wsClientCode, userId);

		return job;
	}
	 */

	private WsJob create(String wsClientCode, Long userId)
	{
		WsJob job = new WsJob();
		job.setWsClientCode(wsClientCode);
		job.setUserId(userId);
		HibernateUtil.save(job);
		return job;
	}

	private void setTimestamp(WsJob job, TimeType type)
	{
		switch(type){
		case START:
			job.setStartDate(AppUtil.getCurrentTimestamp());
			break;
		case FINISH:
			job.setFinishDate(AppUtil.getCurrentTimestamp());
			break;
		}
	}

	public void init(WsJob job)
	{
		job.setStartDate(null);
		job.setFinishDate(null);
		job.setUserId(null);
		HibernateUtil.save(job);
	}

	public void start(WsJob job, Long userId)
	{
		setTimestamp(job, TimeType.START);
		job.setFinishDate(null);
		job.setUserId(userId);
		HibernateUtil.save(job);
	}

	public void finish(WsJob job, Long userId)
	{
		setTimestamp(job, TimeType.FINISH);
		HibernateUtil.save(job);
	}


}
