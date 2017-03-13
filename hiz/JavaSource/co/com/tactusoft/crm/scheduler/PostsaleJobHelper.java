package co.com.tactusoft.crm.scheduler;

import javax.annotation.Resource;

import co.com.tactusoft.crm.execute.Postsale;

public class PostsaleJobHelper {

	@Resource
	private Postsale service;

	public PostsaleJobHelper(String s) {
	}

	public Postsale getService() {
		return service;
	}

}
