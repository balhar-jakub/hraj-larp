package cz.hrajlarp.service;

import cz.hrajlarp.model.dao.AdministratorDAO;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.model.dao.UserIsEditorDAO;
import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.service.SubstitutionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailService {
	private UserIsEditorDAO userIsEditorDAO;
	private AdministratorDAO administratorDAO;
	private UserDAO userDAO;
    private SubstitutionService substitutionService;
	private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    @Autowired public MailService(UserIsEditorDAO userIsEditorDAO, AdministratorDAO administratorDAO, UserDAO userDAO, SubstitutionService substitutionService, MailSender mailSender, SimpleMailMessage templateMessage) {
        this.userIsEditorDAO = userIsEditorDAO;
        this.administratorDAO = administratorDAO;
        this.userDAO = userDAO;
        this.substitutionService = substitutionService;
        this.mailSender = mailSender;
        this.templateMessage = templateMessage;
    }

    public void sendMsgChangedToActor(HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        if(StringUtils.isNotBlank(g.getReplacementsText())){
            message.setText(substitutionService.replaceSubstitutes(g.getReplacementsText(), g, u));
        } else {
            message.setText(
                    "Ahoj " + u.getName() + " " + u.getLastName() + ",\n\n"
                            + "Na h�e " + g.getName() + ", " + "se uvolnilo m�sto. Hra se kon� " + g.getDateAsDMY() + "\n"
                            + "Pros�m potvr� n�m, �e s hrou po��t�.\n\n"
                            + "S p��n�m kr�sn�ho dne t�m HRAJ LARP");
        }
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMsgChangedToReplacement(HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
                "Ahoj " + u.getName() + " " + u.getLastName() + ",\n\n" +
                        "na h�e " + g.getName() + " jsme bohu�el museli zmen�it po�et rol�. Proto t� nyn� evidujeme jako " +
                        "n�hradn�ka a nikoliv jako ��dn�ho hr��e. D�me ti v�d�t jakmile se pro tebe uvoln� m�sto. Omlouv�me " +
                        "se za zp�soben� pot�e\n\n" +
                        "S pozdravem t�m HRAJ LARP");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMsgSignedAsRegular(HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        if (StringUtils.isNotBlank(g.getOrdinaryPlayerText())){
            message.setText(substitutionService.replaceSubstitutes(g.getOrdinaryPlayerText(), g, u));
        } else {
	        message.setText(
	            "Ahoj " + u.getName() + " " + u.getLastName() + ",\n\n"
	                + "potvrzujeme, �e jsi se �sp�n� p�ihl�sil do hry "
	                + g.getName() +". Hra se kon� " + g.getDateAsDMY() +"\n"
	                + "\n"
	                + "S p��n�m kr�sn�ho dne t�m HRAJ LARP");
        }
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMsgSignedAsReplacement (HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        if (StringUtils.isNotBlank(g.getRegisteredSubstitute())){
        	message.setText(substitutionService.replaceSubstitutes(g.getRegisteredSubstitute(), g, u));
        } else {
	        message.setText(
	        		 "V�en� u�ivateli " + u.getName() + " " + u.getLastName() + ",\n\n"
	        	                + "potvrzujeme, �e jste se �sp�n� p�ihl�sil do hry "
	        	                + g.getName() +" jako n�hrada. Pokud se V�s status zm�n� na z�vaznou roli,"
	        	                + "informujeme V�s o tom e-mailem."
	        	                + "Hra se kon� " + g.getDateAsDMY() +"\n"
	        	                + "Ov��te si pros�m tyto skute�nosti na str�nce hrajlarp.cz\n\n"
	        	                + "S p��n�m kr�sn�ho dne V� t�m HRAJ LARP");
        }
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMsgBeforeGame(HrajUserEntity u, GameEntity g) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
                "V�en� u�ivateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                        + "nezaplatil jste hru "
                        + g.getName()
                        + "Hra se kon� " + g.getDateAsDMY() +"\n");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMsgDayToRegStart (HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
        		 "V�en� u�ivateli " + u.getName() + " " + u.getLastName() + ",\n\n"
        	                + "informujeme V�s, �e p�ihla�ov�n� do hry "
        	                + g.getName() +" bude spu�t�no ji� za 24 hodin.\n"
        	                + "S p��n�m kr�sn�ho dne V� t�m HRAJ LARP");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMsgToEditors (HrajUserEntity u, GameEntity g) {
    	List<Integer> editorIds = userIsEditorDAO.getEditorIds(g);
    	List<Integer> adminIds = administratorDAO.getAdministratorIds();
    	adminIds.removeAll(editorIds);
    	editorIds.addAll(adminIds);

    	for(Integer num: editorIds){
    		HrajUserEntity reciever = userDAO.getUserById(num);
    		notifyEditor(reciever, u, g);
    	}
    }

    public void notifyEditor (HrajUserEntity r, HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(r.getEmail());
        message.setText(
        		 "Info o zm�n� stavu u�ivatele:\n\n"
        					+ "U�ivatel: " + u.getName() + " " + u.getLastName() + ",\n"
        					+ "Login: " + u.getUserName() + ",\n\n"
        	                + "Stav tohoto u�ivatele se pr�v� zm�nil z n�hrady na ��dn�ho hr��e hry "
        	                + g.getName() +", kter� prob�hne " + g.getDateAsDMY() +".\n");
        System.out.println("Sending message:\n" + message.getText() + "\n"
        					+"TO: "+ r.getUserName()+"\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void notifyAdminUserLoggedOff(HrajUserEntity loggedOffUser, GameEntity game, boolean payed){
    	SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
    	List<Integer> adminIds = administratorDAO.getAdministratorIds();

    	for(Integer adminId: adminIds){
    		HrajUserEntity reciever = userDAO.getUserById(adminId);
    		StringBuilder sb = new StringBuilder("Info o zm�n� stavu u�ivatele:\n\n"
					+ "U�ivatel: " + loggedOffUser.getName() + " " + loggedOffUser.getLastName() + "\n"
					+ "Login: " + loggedOffUser.getUserName() + "\n\n"
	                + "Tento hr�� se pr�v� odhl�sil ze hry "
	                + game.getName() +", kter� prob�hne " + game.getDateAsDMY() +".\n");
    		if (payed)
    			sb.append("Hr�� ji� zaplatil za ��ast ve h�e.\n");
    		else
    			sb.append("Hr�� nezaplatil za ��ast ve h�e.\n");

    		message.setTo(reciever.getEmail());
    		String text = sb.toString();
            message.setText(text);
            System.out.println("Sending message:\n" + message.getText() + "\n"
            					+"TO: "+ reciever.getUserName()+"\n");
            try{
                this.mailSender.send(message);
            }
            catch(MailException e) {
                System.err.println(e.getMessage());
            }

    	}
    }

    public void notifyAdminOnGameDeletion(String msg){
    	SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
    	List<Integer> adminIds = administratorDAO.getAdministratorIds();

    	for(Integer adminId: adminIds){
    		HrajUserEntity reciever = userDAO.getUserById(adminId);
    		message.setTo(reciever.getEmail());
    		message.setText(msg);
            System.out.println("Sending message:\n" + message.getText() + "\n"
            					+"TO: "+ reciever.getUserName()+"\n");
            try{
                this.mailSender.send(message);
            }
            catch(MailException e) {
                System.err.println(e.getMessage());
            }
    	}
    }

    public void sendActivation (HrajUserEntity u) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
        		 "Toto je zpr�va pro ov��en� e-mailov� adresy, kterou jste zadal/a na hrajlarp.cz.\n\n"
        				 + "Pro plnou aktivaci Va�eho ��tu klepn�te na n�sleduj�c� link:\n"
        				 + "http://hrajlarp.cz/user/activation/" + u.getActivationLink() + "\n\n"
        	             + "HRAJ LARP");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendForgottenPassword (String email, String activationLink) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(email);
        message.setText(
                "Toto je zpr�va pro reset zapomenut�ho hesla.\n\n"
                        + "Pro zm�nu hesla klikn�te na link:\n"
                        + "http://hrajlarp.cz/user/password/new/" + activationLink + "\n\n"
                        + "HRAJ LARP");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }
}
