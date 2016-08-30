package cz.hrajlarp.model;

import cz.hrajlarp.model.dao.UserAttendedGameDAO;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import org.hibernate.annotations.SourceType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.io.IOException;

/**
 *  It handles information from bank.
 */
@Service
public class Bank {
    @Autowired
    UserAttendedGameDAO userAttendedGameDAO;

    @Scheduled(fixedRate = 600000)
    @Transient
    public void loadData() {
        try {
            System.out.println("BANK: Loading Data.");
            Document doc = Jsoup.connect("https://www.fio.cz/ib2/transparent?a=2600445512").get();
            Elements linesToParse = doc.select("table#id8 tbody tr");
            for(Element element: linesToParse){
                Elements cells = element.select("td");
                String price = cells.get(1).text();
                String vs = cells.get(6).text();

                if(price.startsWith("150,00")) {
                    UserAttendedGameEntity payingPlayer = userAttendedGameDAO.getByVS(vs);
                    if(payingPlayer != null){
                        payingPlayer.setPayed(true);
                        payingPlayer.setAutomatic(true);
                        userAttendedGameDAO.editUserAttendedGame(payingPlayer);
                    } else {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
