package cz.hrajlarp.service;

import cz.hrajlarp.dao.UserDAO;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public class MailService {
	@Autowired
	UserDAO userDAO;
    @Autowired
    GameService gameService;
    @Autowired
    DateService dateService;
	
	private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    
    public void sendMsgChangedToActor(HrajUser u, Game g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        if(g.getSubstitutesText() != null && !g.getSubstitutesText().trim().equals("")){
            message.setText(g.getSubstitutesText());
        } else {
            message.setText(
            "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                + "tímto Vám dáváme vědět, že Vaše úloha ve hře "
                + g.getName() +", "
                + "se změnila z náhrady na závaznou roli. Hra se koná " + dateService.getDateAsDMY(g.getDate()) +"\n"
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

    public void sendMsgChangedToReplacement(HrajUser u, Game g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
                "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                        + "tímto Vám dáváme vědět, že Vaše úloha ve hře "
                        + g.getName() +", "
                        + "se změnila ze závazné role na náhradu. Hra se koná " + dateService.getDateAsDMY(g.getDate()) +"\n"
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
    
    public void sendMsgSignedAsRegular(UserAttendedGame userAttendedGame) {
        Game game = userAttendedGame.getAttendedGame();
        HrajUser user = userAttendedGame.getUserAttended();

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(user.getEmail());
        if (game.getOrdinaryPlayerText()!=null && !game.getOrdinaryPlayerText().trim().equals("")){
            message.setText(game.getOrdinaryPlayerText() + " Variabilní symbol: " + userAttendedGame.getVariableSymbol());
        } else {
	        message.setText(
	            "Vážený uživateli " + user.getName() + " " + user.getLastName() + ",\n\n"
	                + "potvrzujeme, že jste se úspěšně přihlásil do hry "
	                + game.getName() +". Hra se koná " + dateService.getDateAsDMY(game.getDate()) +"\n"
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
    
    public void sendMsgSignedAsReplacement (HrajUser u, Game g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        if (g.getRegisteredSubstitute()!=null && !g.getRegisteredSubstitute().trim().equals("")){
        	message.setText(g.getRegisteredSubstitute());
        } else {
	        message.setText(
	        		 "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
	        	                + "potvrzujeme, že jste se úspěšně přihlásil do hry "
	        	                + g.getName() +" jako náhrada. Pokud se Vás status změní na závaznou roli,"
	        	                + "informujeme Vás o tom e-mailem."
	        	                + "Hra se koná " + dateService.getDateAsDMY(g.getDate()) +"\n"
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

    public void sendMsgBeforeGame(HrajUser u, Game g) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
                "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                        + "nezaplatil jste hru "
                        + g.getName()
                        + "Hra se koná " + dateService.getDateAsDMY(g.getDate()) +"\n");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void sendMsgDayToRegStart (HrajUser u, Game g) {

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
    
    public void sendMsgToEditors (HrajUser user, Game game) {
    	List<HrajUser> editors = game.getEditors();
    	List<HrajUser> adminIds = userDAO.getAdmins();
    	adminIds.removeAll(editors);
    	editors.addAll(adminIds);
    	
    	for(HrajUser receiver: editors){
    		notifyEditor(receiver, user, game);
    	}
    }
    
    public void notifyEditor (HrajUser r, HrajUser u, Game g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(r.getEmail());
        message.setText(
        		 "Info o změně stavu uživatele:\n\n"
        					+ "Uživatel: " + u.getName() + " " + u.getLastName() + ",\n"
        					+ "Login: " + u.getUserName() + ",\n\n"
        	                + "Stav tohoto uživatele se právě změnil z náhrady na řádného hráče hry "
        	                + g.getName() +", která proběhne " + dateService.getDateAsDMY(g.getDate()) +".\n");
        System.out.println("Sending message:\n" + message.getText() + "\n"
        					+"TO: "+ r.getUserName()+"\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());            
        }
    }
    
    public void notifyAdminUserLoggedOff(HrajUser loggedOffUser, Game game, boolean payed){
    	SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
    	List<HrajUser> admins = userDAO.getAdmins();
    	
    	for(HrajUser admin : admins){
    		StringBuilder sb = new StringBuilder("Info o změně stavu uživatele:\n\n"
					+ "Uživatel: " + loggedOffUser.getName() + " " + loggedOffUser.getLastName() + "\n"
					+ "Login: " + loggedOffUser.getUserName() + "\n\n"
	                + "Tento hráč se právě odhlásil ze hry "
	                + game.getName() +", která proběhne " + dateService.getDateAsDMY(game.getDate()) +".\n");
    		if (payed)
    			sb.append("Hráč již zaplatil za účast ve hře.\n");
    		else
    			sb.append("Hráč nezaplatil za účast ve hře.\n");
    		
    		message.setTo(admin.getEmail());
    		String text = sb.toString();
            message.setText(text);
            System.out.println("Sending message:\n" + message.getText() + "\n"
            					+"TO: "+ admin.getUserName()+"\n");
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
    	List<HrajUser> admins = userDAO.getAdmins();
    	
    	for(HrajUser admin : admins){
    		message.setTo(admin.getEmail());
    		message.setText(msg);
            System.out.println("Sending message:\n" + message.getText() + "\n"
            					+"TO: "+ admin.getUserName()+"\n");
            try{
                this.mailSender.send(message);
            }
            catch(MailException e) {
                System.err.println(e.getMessage());            
            }
    	}
    }
    
    public void sendActivation (HrajUser u) {

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

    public void sendInfoAboutNoPlace(String email, Game game) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(email);
        String messageText = String.format("V systému u hry %s není zadané místo, ačkoliv je hra za méně než 14 dní. " +
                "Je potřeba místo doplnit. Dokud nebude hra doplněna, bude odesílán tento email každý den.", game.getName());
        message.setText(messageText);
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendInfoAboutUnfinishedAccount(String email, Game game) {
        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(email);
        String messageText = String.format("V systému u hry %s není zaškrtnuté předané účetnictví, ačkoliv hra byla před více než 14 dny. " +
                "Je potřeba předat účetnictví. Dokud nebude předáno, bude odesílán tento email každý den.", game.getName());
        message.setText(messageText);
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());
        }
    }
}
