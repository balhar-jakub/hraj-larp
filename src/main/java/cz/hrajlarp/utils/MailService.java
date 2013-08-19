package cz.hrajlarp.utils;

import java.util.List;

import cz.hrajlarp.model.dao.AdministratorDAO;
import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.model.dao.UserIsEditorDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailService {
	@Autowired
    UserIsEditorDAO userIsEditorDAO;
	@Autowired
	AdministratorDAO administratorDAO;
	@Autowired
	UserDAO userDAO;
	
	private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    
    public void sendMsgChangedToActor(HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
            "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                + "tímto Vám dáváme vědět, že Vaše úloha ve hře "
                + g.getName() +", "
                + "se změnila z náhrady na závaznou roli. Hra se koná " + g.getDateAsDMY() +"\n"
                + "Ověřte si prosím tuto skutečnost na stránce hrajlarp.cz\n\n"
                + "S přáním krásného dne Váš tým HRAJ LARP");
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
                "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                        + "tímto Vám dáváme vědět, že Vaše úloha ve hře "
                        + g.getName() +", "
                        + "se změnila ze závazné role na náhradu. Hra se koná " + g.getDateAsDMY() +"\n"
                        + "Ověřte si prosím tuto skutečnost na stránce hrajlarp.cz\n\n"
                        + "S přáním krásného dne Váš tým HRAJ LARP");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void sendMsgSignedAsRegular(HrajUserEntity u, GameEntity g, UserAttendedGameEntity uag) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        if (g.getOrdinaryPlayerText()!=null && !g.getOrdinaryPlayerText().trim().equals("")){
            message.setText(g.getOrdinaryPlayerText() + " Variabilní symbol: " + uag.getVariableSymbol());
        } else {
	        message.setText(
	            "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
	                + "potvrzujeme, že jste se úspěšně přihlásil do hry "
	                + g.getName() +". Hra se koná " + g.getDateAsDMY() +"\n"
	                + "Ověřte si prosím tuto skutečnost na stránce hrajlarp.cz\n\n"
	                + "S přáním krásného dne Váš tým HRAJ LARP");
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
        if (g.getReplacementsText()!=null && !g.getReplacementsText().trim().equals("")){
        	message.setText(g.getReplacementsText());
        } else {
	        message.setText(
	        		 "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
	        	                + "potvrzujeme, že jste se úspěšně přihlásil do hry "
	        	                + g.getName() +" jako náhrada. Pokud se Vás status změní na závaznou roli,"
	        	                + "informujeme Vás o tom e-mailem."
	        	                + "Hra se koná " + g.getDateAsDMY() +"\n"
	        	                + "Ověřte si prosím tyto skutečnosti na stránce hrajlarp.cz\n\n"
	        	                + "S přáním krásného dne Váš tým HRAJ LARP");
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
        	                + "informujeme Vás, že přihlašování do hry "
        	                + g.getName() +" bude spuštěno již za 24 hodin.\n"
        	                + "S přáním krásného dne Váš tým HRAJ LARP");
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
        		 "Info o změně stavu uživatele:\n\n"
        					+ "Uživatel: " + u.getName() + " " + u.getLastName() + ",\n"
        					+ "Login: " + u.getUserName() + ",\n\n"
        	                + "Stav tohoto uživatele se právě změnil z náhrady na řádného hráče hry "
        	                + g.getName() +", která proběhne " + g.getDateAsDMY() +".\n");
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
    		StringBuilder sb = new StringBuilder("Info o změně stavu uživatele:\n\n"
					+ "Uživatel: " + loggedOffUser.getName() + " " + loggedOffUser.getLastName() + "\n"
					+ "Login: " + loggedOffUser.getUserName() + "\n\n"
	                + "Tento hráč se právě odhlásil ze hry "
	                + game.getName() +", která proběhne " + game.getDateAsDMY() +".\n");
    		if (payed)
    			sb.append("Hráč již zaplatil za účast ve hře.\n");
    		else
    			sb.append("Hráč nezaplatil za účast ve hře.\n");
    		
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
        		 "Toto je zpráva pro ověření e-mailové adresy, kterou jste zadal/a na hrajlarp.cz.\n\n"
        				 + "Pro plnou aktivaci Vašeho účtu klepněte na následující link:\n"
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
}
