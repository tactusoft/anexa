package co.com.tactusoft.crm.view.backing;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import co.com.tactusoft.crm.controller.bo.ParameterBo;
import co.com.tactusoft.crm.controller.bo.SecurityBo;
//import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmParameter;
import co.com.tactusoft.crm.security.UserData;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.util.GlobalSession;

@Named
@Scope("session")
public class LoginBacking {

	@Inject
	private ParameterBo parameterBo;

	@Inject
	private SecurityBo securityBo;

	@Autowired
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Inject
	@Qualifier("sas")
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;

	private Authentication authentication;

	private String userName;
	private String password;
	private Integer idUser;
	private boolean authenticated;
	private String message;

	public LoginBacking() {
	}

	@PostConstruct
	public void init() {
		// TimerBacking timerBacking = FacesUtil.findBean("timerBacking");
		// timerBacking.execute();
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public String doLogin() throws Exception {
		String page = null;
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp;
		authentication = null;

		boolean multiplesSessions = false;
		RequestContext context = RequestContext.getCurrentInstance();
		this.authenticated = false;

		try {
			Authentication authRequest = new UsernamePasswordAuthenticationToken(
					this.getUserName(), this.getPassword());
			authentication = authenticationManager.authenticate(authRequest);
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);

			httpReq = (HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest();
			httpResp = (HttpServletResponse) FacesContext.getCurrentInstance()
					.getExternalContext().getResponse();

			sessionAuthenticationStrategy.onAuthentication(authentication,
					httpReq, httpResp);

			if (securityBo.getListCrmRoleByUser(this.userName).isEmpty()) {
				message = FacesUtil.getMessage("log_msg_validate_credentials");
				throw new AccessDeniedException(message);
			}

			UserData user = (UserData) authentication.getPrincipal();
			idUser = user.getUser().getId().intValue();

			this.password = null;
			this.authenticated = true;

			page = user.getPageDefault() + "?faces-redirect=true";

			SessionBacking sessionBacking = FacesUtil
					.findBean("sessionBacking");

			List<CrmParameter> list = parameterBo
					.getListParameterByGroup("AMBIENTE");
			sessionBacking.setIpWeb(FacesUtil.getServerIP());
			for (CrmParameter row : list) {
				if (row.getCode().equals("ENV_AMBIENTE")) {
					sessionBacking.setEnvironment(row.getTextValue());
				} else if (row.getCode().equals("ENV_VERSION")) {
					sessionBacking.setVersion(row.getTextValue());
				}
			}

			GlobalSession.httpSessionMap.put(httpReq.getSession().getId(),
					httpReq.getSession());
		} catch (BadCredentialsException badCredentialsException) {
			message = FacesUtil.getMessage("log_msg_validate_credentials");
			FacesUtil.addWarn(message);
		} catch (LockedException lockedException) {
			message = FacesUtil.getMessage("log_msg_validate_credentials");
			FacesUtil.addWarn(message);
		} catch (DisabledException disabledException) {
			message = FacesUtil.getMessage("log_msg_enabled");
			FacesUtil.addWarn(message);
		} catch (NullPointerException nullPointerException) {
			message = FacesUtil.getMessage("log_msg_validate_credentials");
			FacesUtil.addWarn(message);
		} catch (AuthenticationServiceException authenticationServiceException) {
			message = FacesUtil.getMessage("log_msg_validate_credentials");
			FacesUtil.addWarn(message);
		} catch (SessionAuthenticationException sae) {
			multiplesSessions = true;
		}

		context.addCallbackParam("validate", multiplesSessions);
		return page;
	}

	public String expireSessions() throws Exception {
		final List<SessionInformation> sessions = sessionRegistry
				.getAllSessions(authentication.getPrincipal(), false);
		for (SessionInformation sessionInformation : sessions) {
			sessionInformation.expireNow();
			sessionRegistry.removeSessionInformation(sessionInformation
					.getSessionId());
			HttpSession httpSession = GlobalSession.httpSessionMap
					.get(sessionInformation.getSessionId());
			httpSession.invalidate();
			GlobalSession.httpSessionMap.remove(sessionInformation
					.getSessionId());
		}
		return doLogin();
	}

	public String logout() {
		this.userName = null;
		this.password = null;
		this.authenticated = false;

		FacesUtil.logout();
		return "/pages/public/login?faces-redirect=true";
	}

	public List<String> getRole() {
		return FacesUtil.getCurrentRoles();
	}

}
