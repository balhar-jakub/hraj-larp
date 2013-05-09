package cz.hrajlarp.model;

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
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 24.4.13
 * Time: 19:08
 */
@Service
public class Bank {
    @Autowired
    UserAttendedGameDAO userAttendedGameDAO;

    @Scheduled(cron="1 0 * * * *")
    @Transient
    public void loadData() {
        try {
            Document doc = Jsoup.connect("https://www.fio.cz/scgi-bin/hermes/dz-transparent.cgi?ID_ucet=2300302640").get();
            Elements linesToParse = doc.select("table.main tbody tr:not(.last)");
            for(Element element: linesToParse){
                Elements cells = element.select("td");
                String price = cells.get(1).text();
                String vs = cells.get(4).text();

                if(price.equals("100,00")) {
                    UserAttendedGameEntity payingPlayer = userAttendedGameDAO.getByVS(vs);
                    if(payingPlayer != null){
                        payingPlayer.setPayed(true);
                        payingPlayer.setAutomatic(true);
                        userAttendedGameDAO.editUserAttendedGame(payingPlayer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
