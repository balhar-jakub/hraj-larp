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
                            + "Na høe " + g.getName() + ", " + "se uvolnilo místo. Hra se koná " + g.getDateAsDMY() + "\n"
                            + "Prosím potvrï nám, že s hrou poèítáš.\n\n"
                            + "S pøáním krásného dne tým HRAJ LARP");
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
                        "na høe " + g.getName() + " jsme bohužel museli zmenšit poèet rolí. Proto tì nyní evidujeme jako " +
                        "náhradníka a nikoliv jako øádného hráèe. Dáme ti vìdìt jakmile se pro tebe uvolní místo. Omlouváme " +
                        "se za zpùsobené potíže\n\n" +
                        "S pozdravem tým HRAJ LARP");
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
	                + "potvrzujeme, že jsi se úspìšnì pøihlásil do hry "
	                + g.getName() +". Hra se koná " + g.getDateAsDMY() +"\n"
	                + "\n"
	                + "S pøáním krásného dne tým HRAJ LARP");
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
	        		 "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
	        	                + "potvrzujeme, že jste se úspìšnì pøihlásil do hry "
	        	                + g.getName() +" jako náhrada. Pokud se Vás status zmìní na závaznou roli,"
	        	                + "informujeme Vás o tom e-mailem."
	        	                + "Hra se koná " + g.getDateAsDMY() +"\n"
	        	                + "Ovìøte si prosím tyto skuteènosti na stránce hrajlarp.cz\n\n"
	        	                + "S pøáním krásného dne Váš tým HRAJ LARP");
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
                "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                        + "nezaplatil jste hru "
                        + g.getName()
                        + "Hra se koná " + g.getDateAsDMY() +"\n");
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
        		 "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
        	                + "informujeme Vás, že pøihlašování do hry "
        	                + g.getName() +" bude spuštìno již za 24 hodin.\n"
        	                + "S pøáním krásného dne Váš tým HRAJ LARP");
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
        		 "Info o zmìnì stavu uživatele:\n\n"
        					+ "Uživatel: " + u.getName() + " " + u.getLastName() + ",\n"
        					+ "Login: " + u.getUserName() + ",\n\n"
        	                + "Stav tohoto uživatele se právì zmìnil z náhrady na øádného hráèe hry "
        	                + g.getName() +", která probìhne " + g.getDateAsDMY() +".\n");
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
    		StringBuilder sb = new StringBuilder("Info o zmìnì stavu uživatele:\n\n"
					+ "Uživatel: " + loggedOffUser.getName() + " " + loggedOffUser.getLastName() + "\n"
					+ "Login: " + loggedOffUser.getUserName() + "\n\n"
	                + "Tento hráè se právì odhlásil ze hry "
	                + game.getName() +", která probìhne " + game.getDateAsDMY() +".\n");
    		if (payed)
    			sb.append("Hráè již zaplatil za úèast ve høe.\n");
    		else
    			sb.append("Hráè nezaplatil za úèast ve høe.\n");

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
        		 "Toto je zpráva pro ovìøení e-mailové adresy, kterou jste zadal/a na hrajlarp.cz.\n\n"
        				 + "Pro plnou aktivaci Vašeho úètu klepnìte na následující link:\n"
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
                "Toto je zpráva pro reset zapomenutého hesla.\n\n"
                        + "Pro zmìnu hesla kliknìte na link:\n"
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
