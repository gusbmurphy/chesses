package com.gusmurphy.chesses.rules.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.PlayerColor;
import com.gusmurphy.chesses.rules.board.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.coordinates.BoardCoordinatesXyAdapter;
import com.gusmurphy.chesses.rules.judge.CheckRule;
import com.gusmurphy.chesses.rules.judge.Judge;
import com.gusmurphy.chesses.rules.judge.PlayerTurnRule;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceOnScreen;
import com.gusmurphy.chesses.rules.piece.PieceSelectionListener;
import com.gusmurphy.chesses.rules.piece.movement.Move;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BoardOnScreen implements PieceSelectionListener, PieceEventListener {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;
    private final Texture darkSquareTexture = new Texture("dark_square.png");
    private final Texture lightSquareTexture = new Texture("light_square.png");
    private final Rectangle bounds = new Rectangle();
    private final ArrayList<Coordinates> highlightedSpaces = new ArrayList<>();
    private final Vector2 cursorPosition = new Vector2();

    private final Map<Piece, PieceOnScreen> piecesOnScreen = new ConcurrentHashMap<>();
    private Piece selectedPiece;

    private final Judge judge;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;
    public static final float SQUARE_SIZE = 0.5f;

    public BoardOnScreen(BoardState boardState, final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();

        createPiecesOnScreenFor(boardState);
        subscribeToEventsFromPieces(boardState);

        judge = new CheckRule(
            new PlayerTurnRule(
                new Judge(boardState),
                PlayerColor.WHITE
            ));
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void render() {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPosition);

        for (PieceOnScreen piece : piecesOnScreen.values()) {
            piece.processDragging(cursorPosition);
        }
    }

    public void draw() {
        float boardWidth = boardWidth();
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();

        bounds.set(bottomLeftX, bottomLeftY, boardWidth, boardWidth);

        drawSpaces(bottomLeftX, bottomLeftY);
        drawHighlightedSpaces();

        for (PieceOnScreen piece : piecesOnScreen.values()) {
            piece.draw();
        }
    }

    public Optional<Coordinates> getBoardCoordinatesOfScreenPosition(Vector2 screenPosition) {
        if (bounds.contains(screenPosition)) {
            float xWithinBoard = screenPosition.x - bottomLeftX();
            float yWithinBoard = screenPosition.y - bottomLeftY();

            int x = (int) Math.floor(xWithinBoard / SQUARE_SIZE);
            int y = (int) Math.floor(yWithinBoard / SQUARE_SIZE);

            BoardCoordinatesXyAdapter adapter = new BoardCoordinatesXyAdapter(x, y);
            return Optional.of(adapter.coordinates());
        }

        return Optional.empty();
    }

    @Override
    public void onPieceSelected(Piece piece) {
        if (selectedPiece == null) {
            List<Coordinates> possibleMoves = judge
                .getPossibleMoves()
                .stream()
                .filter(move -> move.getMovingPiece() == piece)
                .map(Move::spot)
                .collect(Collectors.toList());
            highlightedSpaces.addAll(possibleMoves);
            selectedPiece = piece;
            PieceOnScreen pieceOnScreen = piecesOnScreen.get(piece);
            pieceOnScreen.setDragStatus(true);
        }
    }

    @Override
    public void onPieceReleased(Piece piece, Vector2 screenPosition) {
        if (selectedPiece == piece) {
            Optional<Coordinates> releaseSpot = getBoardCoordinatesOfScreenPosition(screenPosition);
            releaseSpot.ifPresent(spot -> movePieceToSpotIfLegalAndClearHighlights(piece, releaseSpot.get()));
            selectedPiece = null;
            PieceOnScreen pieceOnScreen = piecesOnScreen.get(piece);
            pieceOnScreen.setDragStatus(false);
        }
    }

    public Vector2 getScreenPositionForCenterOf(Coordinates coordinates) {
        BoardCoordinatesXyAdapter xyAdapter = new BoardCoordinatesXyAdapter(coordinates);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;

        float x = xyAdapter.x() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldWidth / 2 - boardWidth / 2;
        float y = xyAdapter.y() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldHeight / 2 - boardWidth / 2;
        return new Vector2(x, y);
    }

    private void createPiecesOnScreenFor(BoardState boardState) {
        for (Piece piece : boardState.getAllPieces()) {
            PieceOnScreen pieceOnScreen = new PieceOnScreen(piece, this);
            piecesOnScreen.put(piece, pieceOnScreen);
            pieceOnScreen.subscribeToMovement(this);
        }
    }

    private void drawSpaces(float bottomLeftX, float bottomLeftY) {
        spriteBatch.begin();

        for (int x = 0; x < BOARD_WIDTH_IN_SQUARES; x++) {
            for (int y = 0; y < BOARD_WIDTH_IN_SQUARES; y++) {
                boolean isDark = (x % 2) == (y % 2);
                Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
                float xPosition = x * SQUARE_SIZE + bottomLeftX;
                float yPosition = y * SQUARE_SIZE + bottomLeftY;
                spriteBatch.draw(texture, xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        spriteBatch.end();
    }

    private void drawHighlightedSpaces() {
        for (Coordinates space : highlightedSpaces) {
            Vector2 center = getScreenPositionForCenterOf(space);
            float xPosition = center.x - SQUARE_SIZE / 2;
            float yPosition = center.y - SQUARE_SIZE / 2;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
            shapeRenderer.end();
        }
    }

    private void movePieceToSpotIfLegalAndClearHighlights(Piece piece, Coordinates releaseSpot) {
        if (judge.getPossibleMoves().stream().filter(move -> move.getMovingPiece() == piece).anyMatch(m -> m.spot() == releaseSpot)) {
            judge.submitMove(piece, releaseSpot);
            highlightedSpaces.clear();
        }
    }

    private float boardWidth() {
        return BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;
    }

    private float bottomLeftX() {
        return viewport.getWorldWidth() / 2 - boardWidth() / 2;
    }

    private float bottomLeftY() {
        return viewport.getWorldHeight() / 2 - boardWidth() / 2;
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        if (event == PieceEvent.TAKEN) {
            piecesOnScreen.remove(piece);
        }
    }

    private void subscribeToEventsFromPieces(BoardState boardState) {
        boardState.getAllPieces().forEach(piece -> piece.subscribeToEvents(this));
    }

}
