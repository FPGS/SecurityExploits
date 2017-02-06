package es.gorka.edu;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

import de.agilecoders.wicket.core.Bootstrap;
import es.gorka.edu.components.HomePage;
import es.gorka.edu.session.AppWebSession;

/**
 * The web application class also serves as spring boot starting point by using
 * spring boot's EnableAutoConfiguration annotation and providing the main
 * method.
 *
 */
@Component
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class WicketWebApplication extends AbstractWicketWebApplication {

	private static final Logger logger = LogManager.getLogger(WicketWebApplication.class.getName());


    @Autowired
    private ApplicationContext applicationContext;

    /**
     * spring boot main method to build context
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WicketWebApplication.class, args);

    }

    /**
     * provides page for default request
     */
    @Override
    public Class<? extends Page> getHomePage() {
		return HomePage.class;
    }

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return AppWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return HomePage.class;
	}

	@Override
	public final Session newSession(Request request, Response response) {
		return new AppWebSession(request);
	}

    /**
     * <ul>
     * <li>making the wicket components injectable by activating the
     * SpringComponentInjector</li>
     * <li>mounting the test page</li>
     * <li>logging spring service method output to showcase working
     * integration</li>
     * </ul>
     */
    @Override
    protected void init() {
        super.init();
		Bootstrap.install(this);
		getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this, applicationContext));
		new AnnotatedMountScanner().scanPackage("es.gorka.edu.components").mount(this);
		logger.info("initializated webpage");
    }

}
