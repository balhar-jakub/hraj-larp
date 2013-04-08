package cz.hrajlarp.utils;

import cz.hrajlarp.model.GameEntity;
import cz.hrajlarp.model.HrajUserEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailService {
	
	private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    
    public void sendMessage(HrajUserEntity u, GameEntity g) {

        SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
        message.setTo(u.getEmail());
        message.setText(
            "Vážený uživateli " + u.getName() + " " + u.getLastName() + ",\n\n"
                + "tímto Vám dáváme vědět, že Vaše úloha ve hře "
                + g.getName() +", "
                + "se změnila z náhrady na závaznou roli. Hra se koná " + g.getDateAsDMY() +"\n"
                + "Ověřte si prosím tuto skutečnost na stránce hrajlarp.cz\n\n"
                + "S přáním krásného dne Váš tým hrajlarp");
        System.out.println("Sending message:\n" + message.getText() + "\n");
        try{
            this.mailSender.send(message);
        }
        catch(MailException e) {
            System.err.println(e.getMessage());            
        }
    }
}
