package cz.hrajlarp.controller;

import cz.hrajlarp.dto.CalendarDto;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.service.CalendarService;
import cz.hrajlarp.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 */
@Controller
public class CalendarController {
    @Autowired
    RightsService rightsService;
    @Autowired
    CalendarService calendarService;

    /**
     * Controller for view of calendar
     * @param model model
     * @return String of .JSP file name mapped for view of calendar
     */
    @RequestMapping(value = "/kalendar", method= RequestMethod.GET)
    public String calendar(
            Model model
    ) {
        CalendarDto calendar = calendarService.showCalendar();

        model.addAttribute("futureGames", calendar.getFutureGames());
        model.addAttribute("formerGames", calendar.getPastGames());
        model.addAttribute("availableGames", calendar.getAvailableGames());
        model.addAttribute("isLogged", calendar.isLogged());
        model.addAttribute("isAdmin", calendar.isAdmin());

        return "calendar";
    }

    @RequestMapping(value = "/admin/game/list", method= RequestMethod.GET)
    public String gameList(
            Model model
    ) {
        HrajUser user = rightsService.getLoggedUser();

        if (rightsService.isEditor(user) || rightsService.isAdministrator(user)){
            CalendarDto calendar = calendarService.showCalendarAdmin(user);

            model.addAttribute("futureGames", calendar.getFutureGames());
            model.addAttribute("formerGames", calendar.getPastGames());
            model.addAttribute("unvalidatedGames", calendar.getUnconfirmedGames());
            model.addAttribute("isLogged", calendar.isLogged());

            return "/admin/game/list";
        } else {
            model.addAttribute("path", "/admin/game/list");
            return "/admin/norights";
        }
    }
}
