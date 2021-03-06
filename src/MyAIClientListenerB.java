import netgame.client.Client;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class MyAIClientListenerB extends AIClientListener {
  static int wins = 0;
  static int plays = 0;

  public MyAIClientListenerB(String name) {
    super(name);
  }

  @Override
  public void yourTurn(AmazonsRules rules, Client<AmazonsState, AmazonsRules> client) {
    int player = this.getMyPlayerNumber();
    int oppPlayer = this.getOtherPlayerNumber();

    Heuristic mobilityHeuristic;
    Heuristic moveCountHeuristic;
    Heuristic randomHeuristic;

    PotentialMoves moves = new PotentialMoves(getMoves(rules, this.getMyPlayerNumber()));
    int size = moves.getMoves().size();

    for (Move move : moves.getMoves()) {
      randomHeuristic = new RandomHeuristic(rules, move, player, oppPlayer);
      mobilityHeuristic = new MobilityHeuristic(rules, move, player, oppPlayer);
      move.addScores(
              randomHeuristic.evaluate(),
              mobilityHeuristic.evaluate()
      );
    }
    moves.normalize(.25, 1, 1, 1);


//    System.out.println(bestMoves);
//    System.out.println(potentialMoves.getBestMove());
    client.send(C.MOVE + C.SPACE + moves.getBestMove().getMoveString());
  }

  @Override
  public void gameover(String reason) {
    plays++;

    if(Integer.parseInt(reason.split(" ")[1])==this.getMyPlayerNumber()){
      wins++;
    }
    double winRate = ((double) wins / (double) plays) * 100;
    System.out.println(new DecimalFormat("##.##").format(winRate) + "%");
    System.out.println("Game: " + plays);
  }


  private List<Move> getMoves(AmazonsRules rules, int player) {
    AmazonsState state = rules.getState();
    state.setTurnHolder(player);
    List<Move> moves = new LinkedList<>();

    for (Point piece : state.getPieces(player)) {
      if (null == piece) {
        System.out.println("Missing piece...");
        continue;
      }
      int fromX = piece.x;
      int fromY = piece.y;
      for (int toX = 0; toX < 10; toX++) {
        for (int toY = 0; toY < 10; toY++) {
          for (int shootX = 0; shootX < 10; shootX++) {
            for (int shootY = 0; shootY < 10; shootY++) {
              if (rules.canMove(fromX, fromY, toX, toY, shootX, shootY)) {
                moves.add(new Move(fromX, fromY, toX, toY, shootX, shootY));
              }
            }
          }
        }
      }
    }
    return moves;
  }


}

