package vg.self.practice.iplDashBoard.TeamRepository;


import org.springframework.data.repository.CrudRepository;
import vg.self.practice.iplDashBoard.model.Team;

public interface TeamRepo extends CrudRepository<Team,Long> {


    Team findByTeamName(String teamName);
}
