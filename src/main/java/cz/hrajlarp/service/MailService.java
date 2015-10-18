package cz.hrajlarp.service;

import cz.hrajlarp.model.dao.AdministratorDAO;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.model.dao.UserIsEditorDAO;
import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {
	private UserIsEditorDAO userIsEditorDAO;
    private AdministratorDAO administratorDAO;
    private UserDAO userDAO;
    private SubstitutionService substitutionService;
	private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    @Autowired
    public MailService(UserIsEditorDAO userIsEditorDAO, AdministratorDAO administratorDAO, UserDAO userDAO,
                       SubstitutionService substitutionService, MailSender mailSender, SimpleMailMessage templateMessage) {
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
                            + "Na hře " + g.getName() + ", " + "se uvolnilo místo. Hra se koná " + g.getDateAsDMY() + "\n"
                            + "Prosím potvrď nám, že s hrou počítáš.\n\n"
                            + "S přáním krásného dne tým HRAJ LARP");
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
                        "na hře " + g.getName() + " jsme bohužel museli zmenšit počet rolí. Proto tě nyní evidujeme jako " +
                        "náhradníka a nikoliv jako řádného hráče. Dáme ti vědět jakmile se pro tebe uvolní místo. Omlouváme " +
                        "se za způsobené potíže\n\n" +
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
	                + "potvrzujeme, že jsi se úspěšně přihlásil do hry "
	                + g.getName() +". Hra se koná " + g.getDateAsDMY() +"\n"
	                + "\n"
	                + "S přáním krásného dne tým HRAJ LARP");
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
                        + "Hra se koná " + g.getDateAsDMY() + "\n");
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
                        + g.getName() + ", která proběhne " + g.getDateAsDMY() + ".\n");
        System.out.println("Sending message:\n" + message.getText() + "\n"
                + "TO: " + r.getUserName() + "\n");
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

    public void sendForgottenPassword (String email, String activationLink) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(email);
        message.setText(
                "Toto je zpráva pro reset zapomenutého hesla.\n\n"
                        + "Pro změnu hesla klikněte na link:\n"
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

    public void sendInfoAboutNoGame(String email, String mailText) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(email);
        message.setText(mailText);
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendInfoAboutNoPlace(String email, GameEntity gameEntity) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(email);
        String messageText = String.format("V systému u hry %s není zadané místo, ačkoliv je hra za méně než 14 dní. " +
                "Je potřeba místo doplnit. Dokud nebude hra doplněna, bude odesílán tento email každý den.", gameEntity.getName());
        message.setText(messageText);
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendInfoAboutUnfinishedAccount(String email, GameEntity gameEntity) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(email);
        String messageText = String.format("V systému u hry %s není zaškrtnuté předané účetnictví, ačkoliv hra byla před více než 14 dny. " +
                "Je potřeba předat účetnictví. Dokud nebude předáno, bude odesílán tento email každý den.", gameEntity.getName());
        message.setText(messageText);
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }
}
