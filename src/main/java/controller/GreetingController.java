package controller;

import dao.SpiceDAO;
import model.Spice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ejb.EJB;
import java.util.List;


@RestController
public class GreetingController {

    @EJB
    private SpiceDAO spiceDAO;

    @RequestMapping("/greeting")
    public String greeting() {
        List<Spice> spices = spiceDAO.findAll();
        return spices.get(0).getName();
    }
}