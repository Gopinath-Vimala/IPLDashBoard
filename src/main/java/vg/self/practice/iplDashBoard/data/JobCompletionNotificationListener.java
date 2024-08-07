package vg.self.practice.iplDashBoard.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vg.self.practice.iplDashBoard.model.Team;

import java.util.HashMap;
import java.util.Map;


@EnableTransactionManagement
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @PersistenceContext
    private EntityManager em;

    public JobCompletionNotificationListener() {
    }
    // spring boot knows to start the txn before method starts and commit the txn after this method ends
    @Transactional
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

//            jdbcTemplate
//                    .query("SELECT id, team1 ,team2, match_winner FROM match", new DataClassRowMapper<>(Match.class))
//                    .forEach(match -> System.out.println("Match ID" + match.getId() +" ,"
//                            +" "+ "First Innings Team : " + match.getTeam1() +" ,"
//                            +" "+ "Second Innings Team : " + match.getTeam2() +" ,"
//                            +" "+ "Match Winner : " + match.getMatchWinner()));


            /// Need to create team instant

            Map<String, Team> teamData = new HashMap<>();
            em.createQuery("select m.team1 , count(*) from Match m group by m.team1",Object[].class)
                    .getResultStream()
                    .forEach(objects -> teamData.put((String) objects[0],new Team((String) objects[0],(long)objects[1])));

            em.createQuery("select m.team2 , count(*) from Match m group by m.team2",Object[].class)
                    .getResultStream()
                    .forEach(objects -> teamData.get((String) objects[0]).setTotalMatches(teamData.get((String) objects[0]).getTotalMatches()+(long) objects[1]));

            em.createQuery("select m.matchWinner , count(*) from Match m group by m.matchWinner",Object[].class)
                    .getResultStream()
                    .forEach(objects -> {
                        if(!objects[0].equals( "NA"))
                           teamData.get((String) objects[0]).setWonMatches((long) objects[1]);
                    });

            teamData.forEach((key, value) -> {
//                em.persist(value);
                System.out.println(value);
//                em.persist(value);
            });

//            teamData.forEach((key, value) -> {
//                System.out.println(value);
//                em.persist(value);
//            });

//            System.out.println(teamData.get("Delhi Capitals"));

//            Team t = teamData.get("Delhi Capitals");
//            t.setId(1000L);
            Team t = new Team();
//            t.setId(80L);
            t.setTeamName("myTeam");
            t.setTotalMatches(6);
            t.setWonMatches(5);
            em.persist(t);
//            em.flush();
//            em.getTransaction().commit();
//            em.close();
            em.createQuery("from Team",Team.class)
                    .getResultStream() // need to check why its not reading
                    .forEach(team -> {
                log.info("!!! Job team {}",team);
                        System.out.println("hai");
                System.out.println(team);
            });

        }else {
            log.info("!!! Job not finished");
        }
    }

}
