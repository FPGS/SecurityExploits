package es.gorka.edu.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import de.agilecoders.wicket.core.markup.html.bootstrap.heading.Heading;
import es.gorka.edu.dto.UserDTO;
import es.gorka.edu.service.UserService;

@MountPath("home.html")
public class HomePage extends BaseUserPage {

	private static final Logger logger = LogManager.getLogger(HomePage.class.getName());

	@SpringBean(name = "userService")
	private UserService userService;

	@SpringBean
	UserDTO userDto;

	public HomePage() {

		add(new Heading("title", getString("title")));
		Form<UserDTO> form = new Form<UserDTO>("formLogin",
				new CompoundPropertyModel<UserDTO>(userDto)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				super.onSubmit();

				if (userService.userExist(getModelObject())) {
					PageParameters pageParameters = new PageParameters();
					boolean authResult = AuthenticatedWebSession.get().signIn(getModelObject().getName(),
							getModelObject().getPassword());
					setResponsePage(MainPage.class, pageParameters);
				} else {
					getFeedbackMessages().add(new FeedbackMessage(this, "El usuario no existe", FeedbackMessage.ERROR));
				}
			}
		};
		fillUserForm(form);
		add(new BookmarkablePageLink<String>("instructionLink", InstructionPage.class));
		add(new BookmarkablePageLink<String>("signUpLink", SignUpPage.class));

		add(form);
	}


}
