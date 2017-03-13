package co.com.tactusoft.crm.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class PostsaleJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext jobContext)
			throws JobExecutionException {
		System.out
				.println("--------------------------------------------------------------------");
		System.out.println("Job Postsale start: " + jobContext.getFireTime());
		JobDetail jobDetail = jobContext.getJobDetail();
		PostsaleJobHelper jobHelper = (PostsaleJobHelper) jobDetail
				.getJobDataMap().get("jobState");
		jobHelper.getService().execute();
		System.out.println("Job Postsale end: " + jobContext.getJobRunTime()
				+ ", key: " + jobDetail.getKey());
		System.out.println("Job Postsale next scheduled time: "
				+ jobContext.getNextFireTime());
		System.out
				.println("--------------------------------------------------------------------");
	}

}
