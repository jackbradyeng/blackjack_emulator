package Model.Table.Processors;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;
import Model.Table.Validators.SplitBetValidator;

public class SplitBetProcessor implements BetProcessor {

    private final Player player;
    private final PlayerPosition position;
    private final PlayerHand hand;
    private final SplitBetValidator validator;

    public SplitBetProcessor(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                              Player player, PlayerPosition position, PlayerHand hand) {
        this.player = player;
        this.position = position;
        this.hand = hand;
        this.validator = new SplitBetValidator(isSimulation, players, playerPositions, player, position, hand);
    }

    public void process() {
         if(validator.isValid()) {
             double amount = validator.getOriginalBet(player);

             // creates new hand, bet, and pair instances for the split
             PlayerHand splitHand = new PlayerHand(position);
             Bet splitBet = new Bet(amount);
             Map.Entry<Player, Bet> splitPair = Map.entry(player, splitBet);

             // sets the acting player for the new hand to be the current player
             splitHand.setActingPlayer(player);

             // dispenses chips from the player for the new hand
             player.dispenseChips(amount);
             System.out.println("Hand split. Additional bet booked for " + (int) amount + " chips on second hand.");

             // removes the split card from the main hand, adds it to the new one
             splitHand.getCards().add(hand.getCards().removeLast());

             // adds the new player-bet pair to the split hand
             splitHand.getPairs().add(splitPair);
         }
    }
}
