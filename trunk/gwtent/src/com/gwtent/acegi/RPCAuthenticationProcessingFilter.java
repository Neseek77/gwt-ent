package com.gwtent.acegi;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.gwtwidgets.server.spring.ServletUtils;

import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.gwtent.acegi.gwt.RPCServletUtils;

public class RPCAuthenticationProcessingFilter extends AbstractProcessingFilter {

//	~ Static fields/initializers =====================================================================================

    public static final String ACEGI_SECURITY_FORM_USERNAME_KEY = "j_username";
    public static final String ACEGI_SECURITY_FORM_PASSWORD_KEY = "j_password";
    public static final String ACEGI_SECURITY_LAST_USERNAME_KEY = "ACEGI_SECURITY_LAST_USERNAME";

    //~ Methods ========================================================================================================

    private String username = "";
    private String password = "";
    
    public Authentication attemptAuthentication(HttpServletRequest request)
        throws AuthenticationException {
    	
    	setUpUserNameAndPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Place the last username attempted into HttpSession for views
        request.getSession().setAttribute(ACEGI_SECURITY_LAST_USERNAME_KEY, username);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * This filter by default responds to <code>/login.rpc</code>.
     *
     * @return the default
     */
    public String getDefaultFilterProcessesUrl() {
        return "/userLogin.rpc";
    }

    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * Enables subclasses to override the composition of the password, such as by including additional values
     * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the
     * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The
     * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the password that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected void setUpUserNameAndPassword(HttpServletRequest request) {
    	String requestPayload = "";
		try {
			requestPayload = RPCServletUtils.readContentAsUtf8(request);
			RPCRequest rpcRequest = RPC.decodeRequest(requestPayload);
			username = (String)rpcRequest.getParameters()[0];
			password = (String)rpcRequest.getParameters()[1];
			//System.out.println(rpcRequest.getMethod().getName());
			//System.out.println(rpcRequest.getParameters().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
