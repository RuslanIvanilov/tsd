package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.controllers.UserController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmJob;
import ru.defo.model.WmJobType;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmUser;
import ru.defo.util.HibernateUtil;

public class JobManager extends ManagerTemplate {

	public long getNextJobId()
	{
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_job_id')").uniqueResult()).longValue();
	}

	public WmJob initJob(WmJobType jobType, WmOrder order, WmArticle article, WmBin bin, int quantity, WmUser executor)
	{
		WmJob job = new WmJob();
		job.setJobId(getNextJobId());
		job.setJobPosId(1L);
		job.setJobTypeId(jobType.getJobTypeId());
		job.setDocumentId(order.getOrderId());
		job.setArticleId(article.getArticleId());
		if(bin != null) job.setBinId(bin.getBinId());
		job.setQuantity(Long.valueOf(quantity));
		job.setExecuteUser(executor.getUserId());
		job.setExecuteDate(new Timestamp(System.currentTimeMillis()));

		return job;
	}

	public List<WmJob> getJobList(WmUser user, WmJobType jobType){
		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("jobTypeId", jobType.getJobTypeId().toString(), "eq");
    	filter.addFilter("executeUser", user.getUserId().toString(), "eq");
		List<WmJob> jobList = HibernateUtil.getObjectList(WmJob.class, filter.getFilterList(), 0, false, "jobId");

		return jobList;
	}

	public List<WmJob> getJobList(WmUser user, WmJobType jobType, WmOrder order, WmArticle article, int quantity)
	{
		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("jobTypeId", jobType.getJobTypeId().toString(), "eq");
    	filter.addFilter("documentId", order.getOrderId().toString(), "eq");
    	filter.addFilter("articleId", article.getArticleId().toString(), "eq");
    	filter.addFilter("executeUser", user.getUserId().toString(), "eq");
		List<WmJob> jobList = HibernateUtil.getObjectList(WmJob.class, filter.getFilterList(), 0, false, null);

		return jobList;
	}

	public void setJob(WmUser user, WmJobType jobType, WmOrder order, WmArticle article, int quantity)
	{
		WmJob job = null;

		List<WmJob> jobList = getJobList(user, jobType, order, article, quantity);
		if(jobList.size()==0){
			job = initJob(jobType, order, article, null, quantity, user);
			HibernateUtil.persist(job, false);
		}

	}

	public WmOrderPos getOrderPosJob(WmUser user, WmOrder order, WmJobType jobType)
	{
		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("documentId", order.getOrderId().toString(), "eq");
		filter.addFilter("executeUser", user.getUserId().toString(), "eq");
		filter.addFilter("jobTypeId", jobType.getJobTypeId().toString(), "eq");
		List<WmJob> jobList = HibernateUtil.getObjectList(WmJob.class, filter.getFilterList(), 0, false, null);

		if(jobList.size()==0) return null;

		WmArticle article =  new ArticleManager().getArticleById(jobList.get(0).getArticleId());
		List<WmOrderPos> posList = new OrderPosManager().getOrderPosListByOrderArticle(order, article);

		for(WmOrderPos pos : posList){
			return pos;
		}

		return null;
	}

	public List<WmJob> getJobForAnyUser(WmJobType jobType, WmOrder order, WmArticle article, WmUser user){
		CriterionFilter filter = new CriterionFilter();
    	filter.addFilter("jobTypeId", jobType.getJobTypeId().toString(), "eq");
    	filter.addFilter("documentId", order.getOrderId().toString(), "eq");
    	filter.addFilter("articleId", article.getArticleId().toString(), "eq");
    	filter.addFilter("executeUser", user.getUserId().toString(), "ne");

		List<WmJob> jobList = HibernateUtil.getObjectList(WmJob.class, filter.getFilterList(), 0, false, null);

		return jobList;
	}

	public WmJobType getJobTypeById(long jobTypeId){
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("jobTypeId", Long.valueOf(jobTypeId)));
		WmJobType jobType = (WmJobType)HibernateUtil.getUniqObject(WmJobType.class, restList, false);
		return jobType;
	}

	public void closeJob(Session session, WmUser user, WmJobType jobType){

		List<WmJob> jobList = getJobList(user, jobType);
		for(WmJob job : jobList){
			session.delete(job);
		}

	}

	public void closeJob(String userIdTxt, Long jobTypeId)
	{
		WmUser user = new UserController().getUserById(userIdTxt);
		WmJobType jobType = new JobManager().getJobTypeById(jobTypeId);
		Session sessionH = HibernateUtil.getSession();
		Transaction trn = sessionH.getTransaction();
		trn.begin();
		new JobManager().closeJob(sessionH, user, jobType);
		trn.commit();
		sessionH.close();
	}

}
