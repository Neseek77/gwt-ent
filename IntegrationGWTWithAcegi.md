# Introduction #

> this wiki page show how to integration gwt with acegi.

> [Acegi ](http://www.acegisecurity.org/): Acegi Security is a powerful, flexible security solution for enterprise software.

# Use acegi's implement as much as posible #

> Acegi implements for page based application, but in gwt application, there is just
one page and many small rpc request, we should holder the request.

> in this implement, we use acegi's filter as much as posible and we get the same
xml configure with acegi.

# Security Interface #
```
public interface Security {
	boolean login(String username, String password, boolean rememberMe);
	void logout(String username);
}
```

# Implement Security #

  * Login and logout
  * we implement ApplicationEventPublisherAware too, that you can listen Security relatively events
  * RememberMe service support
  * logout handler support
  * capability with acegi filters, such as
    1. httpSessionContextIntegrationFilter
    1. rememberMeProcessingFilter
    1. anonymousProcessingFilter
    1. exceptionTranslationFilter
    1. concurrentSessionFilter
    1. filterSecurityInterceptor

> For more information please see source code.


# Config SecurityAcegiImpl #

> whole xml config file is [here](here.md), all is same with acegi's config,

> the fllow is a SecurityAcegiImpl config, just use this bean in gwt rpc
> calls, and all other will work fine.

> for complete config file on my project, please see appendix.

```
	<bean id="Security" class="com.gwtent.acegi.SecurityAcegiImpl">
		<property name="authenticationManager"
			ref="authenticationManager" />
		<property name="rememberMeServices" ref="rememberMeServices" />
		<property name="logoutHandlers">
			<list>
				<ref bean="rememberMeServices" />
			</list>
		</property>
	</bean> 
```

# Configs in web.xml #
```

	<filter>
		<filter-name>AcegiFilterChainProxy</filter-name>
		<filter-class>
			org.acegisecurity.util.FilterToBeanProxy
		</filter-class>
		<init-param>
			<param-name>targetClass</param-name>
			<param-value>
				org.acegisecurity.util.FilterChainProxy
			</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>AcegiFilterChainProxy</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	<listener>
		<listener-class>
			org.springframework.web.context.ContextLoaderListener
		</listener-class>
	</listener>
	<listener>
		<listener-class>
			org.acegisecurity.ui.session.HttpSessionEventPublisher
		</listener-class>
	</listener>

```

# Using in gwt client #

## Login ##
```
signInBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e) {
            	UserLoginRpc.Util.getInstance().login(txtUsername.getText(), txtPassword.getText(), cbRememberMe.getValue(), new AsyncCallback(){

					public void onFailure(Throwable caught) {
						MessageBox.hide();
						MessageBox.alert("error", "error" + caught.getMessage());
					}

					public void onSuccess(Object result) {
						MessageBox.hide();
						if (((Boolean)result).booleanValue()){
							errorLabel.setText(AppMsgFactory.getInstance().LoginSucceed());
							dialog.hide();
						}else{
							errorLabel.setText(AppMsgFactory.getInstance().LoginFaild());
						}
					}
            		
            	});
            }
        });
```


### Logout ###
```
public void onClick(Button button, EventObject e) {
			
			UserLoginRpc.Util.getInstance().logout("", new AsyncCallback() {

				public void onFailure(Throwable caught) {
					MessageBox.alert(AppMsgFactory.getInstance().Logout(),
							caught.getLocalizedMessage());
				}

				public void onSuccess(Object result) {
					Utils.reload();
				}

			});

		}
```


### Get current user name ###
```

protected void setupUserInfo() {
		UserLoginRpc.Util.getInstance().getCurrentUserName(new AsyncCallback() {

			public void onFailure(Throwable caught) {
				welcomeItem.setVisible(false);
				loginButton.setVisible(true);
			}

			public void onSuccess(Object result) {
				String username = (String)result;
				welcomeItem.setVisible(true);
				welcomeItem.setText(AppMsgFactory.getDynamicInstance().Welcome(
						"")
						+ " : " + username);
				//TODO Hard coded here
				if ((username.length() > 0) && ( ! username.equalsIgnoreCase("ANONYMOUSUSER"))) {
					loginButton.setVisible(false);
				} else {
					loginButton.setVisible(true);
				}

			}
		});
	}

```



# Using in server side #

```
public class UserServiceImpl implements UserService {
    private Security security;

    public boolean login(String username, String password, boolean rememberMe) {
		return  security.login(username, password, rememberMe);		
	}

	public void logout(String username) {
		security.logout(username);
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public String getCurrentUserName() {
		if (SecurityContextHolder.getContext().getAuthentication() != null){
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}else{
			return null;
		}
	}

}

```


# Appendix: Complete Config file #
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- we cann't use redirect filter for example: authenticationProcessingFilter logoutFilter-->
	<!-- we use Security(the fllowing one) insteed -->
	<bean id="filterChainProxy"
		class="org.acegisecurity.util.FilterChainProxy">
		<property name="filterInvocationDefinitionSource">
			<value>
				CONVERT_URL_TO_UPPERCASE_BEFORE_COMPARISON
				PATTERN_TYPE_APACHE_ANT
				<!--  
				/**=channelProcessingFilter,httpSessionContextIntegrationFilter,authenticationProcessingFilter,logoutFilter,rememberMeProcessingFilter,anonymousProcessingFilter,exceptionTranslationFilter,concurrentSessionFilter,filterSecurityInterceptor
				-->
				/**=httpSessionContextIntegrationFilter,rememberMeProcessingFilter,anonymousProcessingFilter,exceptionTranslationFilter,concurrentSessionFilter,filterSecurityInterceptor
			</value>
		</property>
	</bean>

	<!-- our security implement, used for gwt rpc service -->
	<bean id="Security" class="com.gwtent.acegi.SecurityAcegiImpl">
		<property name="authenticationManager"
			ref="authenticationManager" />
		<property name="rememberMeServices" ref="rememberMeServices" />
		<property name="logoutHandlers">
			<list>
				<ref bean="rememberMeServices" />
			</list>
		</property>
	</bean>

	<!--
		capability with httpSessionContextIntegrationFilter
	-->
	<bean id="httpSessionContextIntegrationFilter"
		class="org.acegisecurity.context.HttpSessionContextIntegrationFilter" />

	<bean id="authenticationProcessingFilter"
		class="com.gwtent.acegi.RPCAuthenticationProcessingFilter">
		<property name="authenticationManager"
			ref="authenticationManager" />
		<property name="authenticationFailureUrl"
			value="/index.jsp?login_error=1" />
		<property name="defaultTargetUrl" value="/main.jsp" />
		<property name="filterProcessesUrl"
			value="/j_acegi_security_check" />
		<property name="rememberMeServices" ref="rememberMeServices" />
	</bean>
	
	<bean id="rpcAuthenticationProcessingFilter"
		class="com.gwtent.acegi.RPCAuthenticationProcessingFilter">
		<property name="authenticationManager"
			ref="authenticationManager" />
		<property name="authenticationFailureUrl"
			value="/index.jsp?login_error=1" />
		<property name="defaultTargetUrl" value="/main.jsp" />
		<property name="filterProcessesUrl"
			value="/userLogin.rpc" />
		<property name="rememberMeServices" ref="rememberMeServices" />
	</bean>


	<bean id="authenticationManager"
		class="org.acegisecurity.providers.ProviderManager">
		<property name="providers">
			<list>
				<ref local="daoAuthenticationProvider" />
				<bean
					class="org.acegisecurity.providers.rememberme.RememberMeAuthenticationProvider">
					<property name="key" value="coceler" />
				</bean>
				<ref local="anonymousAuthenticationProvider" />
			</list>
		</property>
		<!-- <property name="sessionController" ref="concurrentSessionController"/>
		-->
	</bean>

	<bean id="daoAuthenticationProvider"
		class="org.acegisecurity.providers.dao.DaoAuthenticationProvider">
		<property name="userDetailsService" ref="userDetailsService" />
	</bean>



	<bean id="rememberMeProcessingFilter"
		class="org.acegisecurity.ui.rememberme.RememberMeProcessingFilter">
		<property name="authenticationManager"
			ref="authenticationManager" />
		<property name="rememberMeServices" ref="rememberMeServices" />
	</bean>


	<bean id="rememberMeServices"
		class="org.acegisecurity.ui.rememberme.TokenBasedRememberMeServices">
		<property name="userDetailsService" ref="userDetailsService" />
		<property name="tokenValiditySeconds" value="2592000" />
		<property name="key" value="coceler" />
	</bean>


	<!-- capability with anonymousProcessingFilter -->
	<bean id="anonymousProcessingFilter"
		class="org.acegisecurity.providers.anonymous.AnonymousProcessingFilter">
		<property name="key" value="anonymousUser" />
		<property name="userAttribute"
			value="ANONYMOUSUSER,PRIV_ANONYMOUS" />
	</bean>
	<bean id="anonymousAuthenticationProvider"
		class="org.acegisecurity.providers.anonymous.AnonymousAuthenticationProvider">
		<property name="key" value="anonymousUser" />
	</bean>


	<bean id="userDetailsService"
		class="org.acegisecurity.userdetails.jdbc.JdbcDaoImpl">
		<property name="dataSource" ref="DataSource" />
		<property name="usersByUsernameQuery">
			<value>
				SELECT username,password,1 FROM user WHERE status='1'
				AND username = ?
			</value>
		</property>
		<property name="authoritiesByUsernameQuery">
			<value>
				SELECT u.username,p.priv_name FROM user u,user_priv p
				WHERE u.user_id =p.user_id AND u.username = ?
			</value>
		</property>
	</bean>

	<!-- 
		Md5PasswordEncoder 
		ShaPasswordEncoder
		As SHA is a one-way hash, the salt can contain any characters. The default 
		strength for the SHA encoding is SHA-1. If you wish to use higher strengths use 
		the argumented constructor. ShaPasswordEncoder(int strength) 
		The applicationContext example... 	
		<bean id="passwordEncoder" class="org.acegisecurity.providers.encoding.ShaPasswordEncoder">
		<constructor-arg value="256"/>
		</bean>
	-->
	<bean id="passwordEncoder"
		class="org.acegisecurity.providers.encoding.PlaintextPasswordEncoder" />
	<bean id="userCache"
		class="org.acegisecurity.providers.dao.cache.EhCacheBasedUserCache"
		autowire="byName">
		<property name="cache" ref="userCacheBackend" />
	</bean>

	<bean id="userCacheBackend"
		class="org.springframework.cache.ehcache.EhCacheFactoryBean">
		<property name="cacheManager" ref="cacheManager" />
		<property name="cacheName" value="userCache" />
	</bean>

	<bean id="logoutFilter"
		class="org.acegisecurity.ui.logout.LogoutFilter">
		<constructor-arg value="/index.jsp" />
		<constructor-arg>
			<list>
				<ref bean="rememberMeServices" />
				<bean
					class="org.acegisecurity.ui.logout.SecurityContextLogoutHandler" />
			</list>
		</constructor-arg>
		<property name="filterProcessesUrl" value="/j_acegi_logout" />
	</bean>


	<bean id="concurrentSessionFilter"
		class="org.acegisecurity.concurrent.ConcurrentSessionFilter">
		<property name="sessionRegistry" ref="sessionRegistry" />
		<property name="expiredUrl" value="/index.jsp" />
	</bean>

	<bean id="concurrentSessionController"
		class="org.acegisecurity.concurrent.ConcurrentSessionControllerImpl">
		<property name="maximumSessions">
			<value>1</value>
		</property>
		<property name="sessionRegistry" ref="sessionRegistry" />
	</bean>
	<bean id="sessionRegistry"
		class="org.acegisecurity.concurrent.SessionRegistryImpl" />






	<!-- AuthenticationEvent Listener -->
	<bean id="loggerListener"
		class="org.acegisecurity.event.authentication.LoggerListener" />






	<!-- process AccessDeniedException, AuthenticationException  -->
	<bean id="exceptionTranslationFilter"
		class="org.acegisecurity.ui.ExceptionTranslationFilter">
		<property name="authenticationEntryPoint">
			<ref local="authenticationProcessingFilterEntryPoint" />
		</property>
		<property name="accessDeniedHandler">
			<bean
				class="org.acegisecurity.ui.AccessDeniedHandlerImpl">
				<property name="errorPage" value="/error.jsp" />
			</bean>
		</property>
	</bean>



	<bean id="authenticationProcessingFilterEntryPoint"
		class="org.acegisecurity.ui.webapp.AuthenticationProcessingFilterEntryPoint">
		<property name="loginFormUrl">
			<value>/index.jsp</value>
		</property>
		<property name="forceHttps" value="false" />
	</bean>


	<bean id="filterSecurityInterceptor"
		class="org.acegisecurity.intercept.web.FilterSecurityInterceptor">
		<property name="authenticationManager"
			ref="authenticationManager" />
		<property name="accessDecisionManager"
			ref="accessDecisionManager" />
		<property name="objectDefinitionSource">
			<value>
				CONVERT_URL_TO_UPPERCASE_BEFORE_COMPARISON
				PATTERN_TYPE_APACHE_ANT

				/update.jsp=PRIV_1 /hello_1.jsp=PRIV_2
				/**=PRIV_ANONYMOUS,PRIV_COMMON
			</value>
		</property>
	</bean>


	<bean id="accessDecisionManager"
		class="org.acegisecurity.vote.AffirmativeBased">
		<property name="allowIfAllAbstainDecisions" value="true" />
		<property name="decisionVoters">
			<list>
				<ref bean="roleVoter" />
			</list>
		</property>
	</bean>

	<bean id="roleVoter" class="org.acegisecurity.vote.RoleVoter">
		<property name="rolePrefix" value="PRIV_" />
	</bean>



	<bean id="methodSecurityInterceptor"
		class="org.acegisecurity.intercept.method.aopalliance.MethodSecurityInterceptor">
		<property name="authenticationManager"
			ref="authenticationManager" />
		<property name="accessDecisionManager"
			ref="accessDecisionManager" />
		<property name="objectDefinitionSource">
			<value>
				com.coceler.service.BbtForum.updateForum=PRIV_2
			</value>
		</property>
	</bean>
	<bean id="bbtForum"
		class="org.springframework.aop.framework.ProxyFactoryBean">
		<property name="interceptorNames">
			<list>
				<idref bean="methodSecurityInterceptor" />
			</list>
		</property>
		<property name="proxyTargetClass" value="true" />
		<property name="target" ref="bbtForumTarget" />
	</bean>
	<bean id="bbtForumTarget" class="com.coceler.service.BbtForum" />

	<!-- 
		<bean id="methodSecurityInterceptor"
		class="org.acegisecurity.intercept.method.aopalliance.MethodSecurityInterceptor">
		<property name="authenticationManager" ref="authenticationManager" />
		<property name="accessDecisionManager" ref="accessDecisionManager" />
		<property name="objectDefinitionSource" ref="objectDefinitionSource" /> 
		<property name="objectDefinitionSource">
		<value>
		com.coceler.service.BbtForum.updateForum=PRIV_2
		</value>
		</property>
		</bean>-->


	
	<!-- 
		<bean id="objectDefinitionSource"
		class="org.acegisecurity.intercept.method.MethodDefinitionAttributes">
		<property name="attributes">
		<bean class="org.acegisecurity.annotation.SecurityAnnotationAttributes" />
		</property>
		</bean>
	-->
	<!-- 
		<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
		<property name="interceptorNames">
		<list>
		<value>methodSecurityInterceptor</value>
		</list>
		</property>
		<property name="beanNames">
		<list>
		<value>bbtForum</value>
		</list>
		</property>
		</bean>
		
		
		<bean id="bbtForum" class="com.coceler.service.BbtForum" />
	-->

	<!-- EhCache Manager -->
	<bean id="cacheManager"
		class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean">
		<property name="configLocation">
			<value>classpath:conf/ehcache.xml</value>
		</property>
	</bean>

	<!-- HTTPS -->
	<bean id="channelProcessingFilter"
		class="org.acegisecurity.securechannel.ChannelProcessingFilter">
		<property name="channelDecisionManager"
			ref="channelDecisionManager" />
		<property name="filterInvocationDefinitionSource">
			<value>
				CONVERT_URL_TO_UPPERCASE_BEFORE_COMPARISON
				\A/index.jsp.*\Z=REQUIRES_SECURE_CHANNEL
				\A/j_acegi_security_check.*\Z=REQUIRES_SECURE_CHANNEL
				\A.*\Z=REQUIRES_INSECURE_CHANNEL
			</value>
		</property>
	</bean>
	<bean id="channelDecisionManager"
		class="org.acegisecurity.securechannel.ChannelDecisionManagerImpl">
		<property name="channelProcessors">
			<list>
				<bean
					class="org.acegisecurity.securechannel.SecureChannelProcessor" />
				<bean
					class="org.acegisecurity.securechannel.InsecureChannelProcessor" />
			</list>
		</property>
	</bean>

	<bean class="com.coceler.service.LoginSuccessListener" />
</beans>
```