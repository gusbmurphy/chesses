package com.gusmurphy.chesses.ui.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gusmurphy.chesses.ChessesGame;
import com.gusmurphy.chesses.rules.board.BoardState;
import com.gusmurphy.chesses.rules.board.PieceEvent;
import com.gusmurphy.chesses.rules.board.PieceEventListener;
import com.gusmurphy.chesses.rules.board.square.coordinates.Coordinates;
import com.gusmurphy.chesses.rules.board.square.coordinates.CoordinatesXyAdapter;
import com.gusmurphy.chesses.rules.judge.*;
import com.gusmurphy.chesses.rules.piece.Piece;
import com.gusmurphy.chesses.rules.piece.PieceSelectionListener;
import com.gusmurphy.chesses.ui.piece.PieceRepresentation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BoardRepresentation implements PieceSelectionListener, PieceEventListener {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;
    private final Texture darkSquareTexture = new Texture("dark_square.png");
    private final Texture lightSquareTexture = new Texture("light_square.png");
    private final Rectangle bounds = new Rectangle();
    private final ArrayList<MoveIndication> possibleMoves = new ArrayList<>();
    private final Vector2 cursorPosition = new Vector2();

    private final Map<Piece, PieceRepresentation> piecesOnScreen = new ConcurrentHashMap<>();
    private Piece selectedPiece;

    private final Judge judge;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;
    public static final float SQUARE_SIZE = 40f;

    public BoardRepresentation(BoardState boardState, final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();

        createPiecesOnScreenFor(boardState);
        subscribeToEventsFromPieces(boardState);

        judge = new DefaultJudge(boardState);
    }

    @Override
    public void onPieceEvent(PieceEvent event, Piece piece) {
        if (event == PieceEvent.TAKEN) {
            piecesOnScreen.remove(piece);
        }
    }

    @Override
    public void onPieceSelected(Piece piece) {
        if (selectedPiece == null) {
            getIndicatorsForPossibleMoves(piece);
            updateSelectedPiece(piece);
        }
    }

    @Override
    public void onPieceReleased(Piece piece, Vector2 screenPosition) {
        if (selectedPiece == piece) {
            movePieceIfMoveIsLegal(piece, screenPosition);
            unselectPiece(piece);
        }
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Judge getJudge() {
        return judge;
    }

    public void render() {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPosition);

        for (PieceRepresentation piece : piecesOnScreen.values()) {
            piece.processInput(cursorPosition);
        }
    }

    public Optional<Coordinates> getBoardCoordinatesOfScreenPosition(Vector2 screenPosition) {
        if (bounds.contains(screenPosition)) {
            float xWithinBoard = screenPosition.x - bottomLeftX();
            float yWithinBoard = screenPosition.y - bottomLeftY();

            int x = (int) Math.floor(xWithinBoard / SQUARE_SIZE);
            int y = (int) Math.floor(yWithinBoard / SQUARE_SIZE);

            CoordinatesXyAdapter adapter = new CoordinatesXyAdapter(x, y);
            return Optional.of(adapter.coordinates());
        }

        return Optional.empty();
    }

    public Vector2 getScreenPositionForCenterOf(Coordinates coordinates) {
        CoordinatesXyAdapter xyAdapter = new CoordinatesXyAdapter(coordinates);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;

        float x = xyAdapter.x() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldWidth / 2 - boardWidth / 2;
        float y = xyAdapter.y() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldHeight / 2 - boardWidth / 2;
        return new Vector2(x, y);
    }

    public void draw() {
        float boardSize = boardSize();
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();

        bounds.set(bottomLeftX, bottomLeftY, boardSize, boardSize);

        drawSpaces();
        drawHighlightedSpaces();
        drawSelectedMoveIndicator();
        drawPieces();
    }

    private void drawPieces() {
        spriteBatch.begin();
        drawNonSelectedPieces();
        drawSelectedPiece();
        spriteBatch.end();
    }

    private void drawNonSelectedPieces() {
        piecesOnScreen.entrySet()
            .stream()
            .filter(entry -> entry.getKey() != selectedPiece)
            .map(Map.Entry::getValue)
            .forEach(PieceRepresentation::draw);
    }

    private void drawSelectedPiece() {
        piecesOnScreen.entrySet()
            .stream()
            .filter(entry -> entry.getKey() == selectedPiece)
            .map(Map.Entry::getValue)
            .forEach(PieceRepresentation::draw);
    }

    private void createPiecesOnScreenFor(BoardState boardState) {
        for (Piece piece : boardState.getAllPieces()) {
            PieceRepresentation pieceRepresentation = new PieceRepresentation(piece, this);
            piecesOnScreen.put(piece, pieceRepresentation);
            pieceRepresentation.subscribeToMovement(this);
        }
    }

    private void drawSpaces() {
        spriteBatch.begin();

        for (int x = 0; x < BOARD_WIDTH_IN_SQUARES; x++) {
            drawColumnAt(x);
        }

        spriteBatch.end();
    }

    private void drawColumnAt(int x) {
        for (int y = 0; y < BOARD_WIDTH_IN_SQUARES; y++) {
            drawSquareAt(x, y);
        }
    }

    private void drawSquareAt(int x, int y) {
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();
        boolean isDark = (x % 2) == (y % 2);
        Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
        float xPosition = x * SQUARE_SIZE + bottomLeftX;
        float yPosition = y * SQUARE_SIZE + bottomLeftY;
        spriteBatch.draw(texture, xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawHighlightedSpaces() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (MoveIndication move : possibleMoves) {
            Vector2 center = getScreenPositionForCenterOf(move.getCoordinates());
            float xPosition = center.x - SQUARE_SIZE / 2;
            float yPosition = center.y - SQUARE_SIZE / 2;

            shapeRenderer.setColor(move.getColor());
            shapeRenderer.rect(xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawSelectedMoveIndicator() {
        if (selectedPiece != null) {
            getBoardCoordinatesOfScreenPosition(cursorPosition).flatMap(coordinates ->
                possibleMoves.stream()
                    .filter(moveIndication -> moveIndication.getCoordinates() == coordinates)
                    .findFirst()
                    .map(MoveIndication::getCoordinates)
            ).ifPresent(coordinatesOfMoveUnderCursor -> {
                Vector2 coordinateCenter = getScreenPositionForCenterOf(coordinatesOfMoveUnderCursor);
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                float xPosition = coordinateCenter.x - SQUARE_SIZE / 2;
                float yPosition = coordinateCenter.y - SQUARE_SIZE / 2;

                shapeRenderer.setColor(1, 0, 0, 0.5f);
                shapeRenderer.rect(xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);

                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
            });
        }
    }

    private void movePieceToCoordinatesIfLegalAndClearHighlights(Piece piece, Coordinates coordinates) {
        if (pieceCanMoveTo(piece, coordinates)) {
            judge.submitMove(piece, coordinates);
            possibleMoves.clear();
        }
    }

    // TODO: Shouldn't we be able to make the move and then the judge can tell us if it happened?
    private boolean pieceCanMoveTo(Piece piece, Coordinates coordinates) {
        return judge.getPossibleMoves()
            .stream()
            .filter(move -> move.getMovingPiece() == piece)
            .anyMatch(m -> m.coordinates() == coordinates);
    }

    private float boardSize() {
        return BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;
    }

    private float bottomLeftX() {
        return viewport.getWorldWidth() / 2 - boardSize() / 2;
    }

    private float bottomLeftY() {
        return viewport.getWorldHeight() / 2 - boardSize() / 2;
    }

    private void subscribeToEventsFromPieces(BoardState boardState) {
        boardState.getAllPieces().forEach(piece -> piece.subscribeToEvents(this));
    }

    private void getIndicatorsForPossibleMoves(Piece piece) {
        List<MoveIndication> indicators = judge
            .getPossibleMoves()
            .stream()
            .filter(move -> move.getMovingPiece() == piece)
            .map(MoveIndication::new)
            .collect(Collectors.toList());
        this.possibleMoves.addAll(indicators);
    }

    private void updateSelectedPiece(Piece piece) {
        selectedPiece = piece;
        PieceRepresentation pieceRepresentation = piecesOnScreen.get(piece);
        pieceRepresentation.setDragStatus(true);
    }

    private void movePieceIfMoveIsLegal(Piece piece, Vector2 screenPosition) {
        Optional<Coordinates> releaseCoordinates = getBoardCoordinatesOfScreenPosition(screenPosition);
        releaseCoordinates.ifPresent(coordinates -> movePieceToCoordinatesIfLegalAndClearHighlights(piece, releaseCoordinates.get()));
    }

    private void unselectPiece(Piece piece) {
        selectedPiece = null;
        PieceRepresentation pieceRepresentation = piecesOnScreen.get(piece);
        pieceRepresentation.setDragStatus(false);
    }

}
