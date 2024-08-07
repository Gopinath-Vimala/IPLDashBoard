package vg.self.practice.iplDashBoard.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vg.self.practice.iplDashBoard.TeamRepository.TeamRepo;
import vg.self.practice.iplDashBoard.model.Team;

@RestController
public class TeamController {


    TeamRepo teamRepo;

    public  TeamController(TeamRepo teamRepo){
        this.teamRepo = teamRepo;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeamByTeamName(@PathVariable String teamName){
        Team t = new Team();
        t.setTeamName(teamName);
        t.setTotalMatches(6);
        t.setWonMatches(5);
        return this.teamRepo.findByTeamName(teamName);
    }

}
