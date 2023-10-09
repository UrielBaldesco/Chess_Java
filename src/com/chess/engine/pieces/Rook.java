package com.chess.engine.pieces;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.chess.engine.Alliance;

public class Rook extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDINATES={-8,-1,1,8};
    Rook(int piecePosition, Alliance pieceAlliance){
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateDestinationOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateDestinationOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, candidateDestinationOffset)) {
                    break;
                }
                candidateDestinationCoordinate += candidateDestinationOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

        private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
            return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset==-9 || candidateOffset==7);
        }
        private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
            return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset==-7 || candidateOffset==9);
        }

    }

