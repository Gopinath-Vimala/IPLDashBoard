package vg.self.practice.iplDashBoard.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import vg.self.practice.iplDashBoard.model.Match;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(MatchInput matchInput) throws Exception {
        Match match = new Match();
        match.setId(Long.parseLong(matchInput.getId()));
        match.setSeason(matchInput.getSeason());
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        match.setMatchType(matchInput.getMatch_type());
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());
        String firstInningsTeam ,secondInningsTeam;
        if((matchInput.getToss_winner().equals(matchInput.getTeam1()) &&
                "bat".equals(matchInput.getToss_decision())) ||
                matchInput.getToss_winner().equals(matchInput.getTeam2()) &&
                        "field".equals(matchInput.getToss_decision())){
            firstInningsTeam = matchInput.getTeam1();
            secondInningsTeam = matchInput.getTeam2();
        }else {
            firstInningsTeam = matchInput.getTeam2();
            secondInningsTeam = matchInput.getTeam1();
        }
        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);
        match.setMatchWinner(matchInput.getWinner());
        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setTargetRuns(matchInput.getTarget_runs());
        match.setTargetOvers(matchInput.getTarget_overs());
        match.setSuperOver(matchInput.getSuper_over());
        match.setMethod(matchInput.getMethod());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        return match;
    }
}
