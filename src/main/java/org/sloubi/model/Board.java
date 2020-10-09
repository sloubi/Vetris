package org.sloubi.model;

import org.sloubi.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board implements ActionListener {

    // Statuts du jeu
    public enum GameState {
        InGame("En jeu"), Paused("Pause"), Over("Perdu");
        String name;

        GameState(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Shape shape;
    private Shape nextShape;

    private final Random random = new Random();
    private final Square[][] map = new Square[22][10];
    private GameState state = GameState.InGame;
    private Score score;
    private final List<BoardListener> listeners = new ArrayList<>();
    private final Timer gameTimer = new Timer(1000, this);
    private final Timer clockTimer = new Timer(1000, this);
    private int seconds = 0;
    private final HighScores highscores;

    // Pour l'animation de game over
    private final Timer endTimer = new Timer(5, this);
    private int endX;
    private int endY;

    public Board() {
        init();
        highscores = HighScores.load();
    }

    public void init() {
        state = GameState.InGame;

        endTimer.stop();

        score = new Score();
        for (BoardListener listener : listeners) {
            listener.scoreChanged();
        }

        // Initialisation des cases à 'vide'
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                map[y][x] = new Square(Square.State.Empty);
            }
        }

        for (BoardListener listener : listeners) {
            listener.boardChanged();
        }

        seconds = 0;
        for (BoardListener listener : listeners) {
            listener.clockChanged();
        }

        gameTimer.start();
        clockTimer.start();

        // Choix de la première pièce
        chooseNextShape();
        // Affichage de la première pièce
        nextShape();
    }

    public int getHeight() {
        return map.length;
    }

    public int getWidth() {
        return map[0].length;
    }

    /**
     * Récupère une case
     * @param x Abscisse
     * @param y Ordonnée
     * @return La case
     */
    public Square getSquare(int x, int y) {
        return map[y][x];
    }

    /**
     * La pièce peut-elle se déplacer de moveX ou moveY ?
     * @param moveX Déplacement X
     * @param moveY Déplacement Y
     * @return Vrai si la pièce peut bouger
     */
    private boolean shapeCanMove(int moveX, int moveY) {
        int newX, newY;
        for (int y = 0; y < shape.getHeight(); y++) {
            for (int x = 0; x < shape.getWidth(); x++) {
                newX = x + shape.getX() + moveX;
                newY = y + shape.getY() + moveY;
                if (newY < 0 || newX < 0 || newY >= getHeight() || newX >= getWidth() ||
                        shape.getSquare(x, y) == 1 && map[newY][newX].getState() == Square.State.Filled) {
                    return false;
                }
            }
        }
        return true;
    }

    private void moveShape(int moveX, int moveY) {
        // On efface la pièce à l'ancienne position
        updateSquares(Square.State.Empty, null);

        // On met à jour les coordonnées avec le mouvement demandé
        shape.setX(shape.getX() + moveX);
        shape.setY(shape.getY() + moveY);

        // On déplace
        updateSquares(Square.State.Current, shape.getColor());
    }

    /**
     * La pièce est lachée en bas directement
     */
    public void hardDrop() {
        if (state == GameState.InGame) {
            int moveY = 1;
            while (shapeCanMove(0, moveY)) {
                moveY++;
            }

            moveShape(0, --moveY);
            step();
        }
    }

    /**
     * Vérification si des lignes sont complètes
     */
    private void checkLines() {
        int nbOfFullLines = 0;
        for (int y = shape.getY(); y < shape.getY() + shape.getHeight(); y++) {
            if (isFullLine(y)) {
                nbOfFullLines++;
                removeLine(y);
            }
        }

        if (nbOfFullLines == 4) {
            MainFrame.clipWow.setFramePosition(0);
            MainFrame.clipWow.start();
        }

        increaseScore(nbOfFullLines);
    }

    /**
     * Est-ce que la ligne y est complète ?
     * @param y L'ordonnée de la ligne à vérifier
     * @return Vrai si ligne complète
     */
    private boolean isFullLine(int y) {
        for (int x = 0; x < getWidth(); x++) {
            if (map[y][x].getState() == Square.State.Empty)
                return false;
        }
        return true;
    }

    /**
     * Suppression d'une ligne complète et décalage de tout ce qui se trouve au dessus
     * @param yToRemove Ordonnée de la ligne complète
     */
    private void removeLine(int yToRemove) {
        for (int y = yToRemove; y >= 0; y--) {
            for (int x = 0; x < getWidth(); x++) {
                if (y == 0) {
                    map[y][x].setState(Square.State.Empty);
                    map[y][x].setColor(null);
                }
                else {
                    map[y][x].setState(map[y - 1][x].getState());
                    map[y][x].setColor(map[y - 1][x].getColor());
                }
            }
        }
    }

    /**
     * On choisit la prochaine pièce aléatoirement (dans n'importe quel sens)
     */
    private void chooseNextShape() {
        // 1 chance sur 20 d'avoir la pièce en V
        int choiceVShape = random.nextInt(20);
        if (choiceVShape == 1) {
            nextShape = new Shape(Tetromino.VShape);
        }
        else {
            Tetromino[] tetrominos = Tetromino.values();
            int choice = random.nextInt(tetrominos.length - 1);

            nextShape = new Shape(tetrominos[choice]);
            nextShape.randomRotate();
        }

        nextShape.setX(4);

        for (BoardListener listener : listeners) {
            listener.nextShapeChanged();
        }
    }

    /**
     * On change de pièce, on l'affiche et on choisit la prochaine
     */
    private void nextShape() {
        shape = nextShape;

        showShape();

        chooseNextShape();
    }

    /**
     * Apparition d'une pièce
     */
    private void showShape() {
        updateSquares(Square.State.Current, shape.getColor());
    }

    /**
     * Passage des cases de la pièce de Current à Filled
     */
    private void stopShape() {
        updateSquares(Square.State.Filled, shape.getColor());

        // Si une ou plusieurs lignes sont complètes, on les enlève
        checkLines();
    }

    /**
     * Applique un statut et une couleur sur les cases de la pièce
     * @param state Statut à appliquer
     * @param color Couleur à appliquer
     */
    private void updateSquares(Square.State state, Color color) {
        for (int y = 0; y < shape.getHeight(); y++) {
            for (int x = 0; x < shape.getWidth(); x++) {
                if (shape.getSquare(x, y) == 1) {
                    map[y + shape.getY()][x + shape.getX()].setState(state);
                    map[y + shape.getY()][x + shape.getX()].setColor(color);
                }
            }
        }
    }

    /**
     * Rotation de la pièce courante
     * @param clockwise Sens
     */
    public void rotateShape(boolean clockwise) {
        if (state == GameState.InGame) {
            updateSquares(Square.State.Empty, null);
            shape.rotate(clockwise);

            // Pour éviter le débordement du tableau, si la pièce dépasse, on la décale vers la gauche
            while (shape.getX() + shape.getWidth() > getWidth()) {
                shape.setX(shape.getX() - 1);
            }

            updateSquares(Square.State.Current, shape.getColor());

            for (BoardListener listener : listeners) {
                listener.boardChanged();
            }
        }
    }

    /**
     * Étape à chaque tick
     * La pièce descend d'un cran si elle peut.
     * Sinon on passe à la pièce suivante. Si la pièce suivante est bloqué, perdu...
     */
    public void step() {
        // Si on peut, on déplace la pièce vers le bas
        if (shapeCanMove(0, 1)) {
            moveShape(0, 1);
        }
        else {
            // La pièce s'arrête de descendre
            stopShape();
            // On passe à la pièce suivante
            nextShape();

            // La nouvelle pièce est bloquée avant d'avoir pu descendre, c'est perdu
            if (!shapeCanMove(0, 1)) {
                gameOver();
            }
        }

        for (BoardListener listener : listeners) {
            listener.boardChanged();
        }
    }

    /**
     * Un mouvement est demandé quand une touche de déplacement est pressée
     * @param moveX Déplacement X
     * @param moveY Déplacement Y
     */
    public void tryToMove(int moveX, int moveY) {
        if (state == GameState.InGame) {
            if (shapeCanMove(moveX, moveY)) {
                moveShape(moveX, moveY);
            }

            for (BoardListener listener : listeners) {
                listener.boardChanged();
            }
        }
    }

    public Score getScore() {
        return score;
    }

    public void addListener(BoardListener listener) {
        listeners.add(listener);
    }

    /**
     * Augmentation du score en fonction du nombre de lignes supprimés d'un seul coup
     * @param nbOfLines Nombre de lignes complétées
     */
    private void increaseScore(int nbOfLines) {
        if (nbOfLines > 0) {
            score.addLines(nbOfLines);

            if (score.updateLevel()) {
                gameTimer.setDelay(getDelayByLevel());
            }

            for (BoardListener listener : listeners) {
                listener.scoreChanged();
            }
        }
    }

    private int getDelayByLevel() {
        return 1000 - score.getLevel() * 100;
    }

    private void gameOver() {
        state = GameState.Over;
        gameTimer.stop();
        clockTimer.stop();

        for (BoardListener listener : listeners) {
            listener.stateChanged();
        }

        // Animation de fin
        endX = 0;
        endY = getHeight() - 1;
        endTimer.start();

        handleHighScore();
    }

    private void handleHighScore() {
        if (highscores.isHighScore(score)) {
            for (BoardListener listener : listeners) {
                score.setTime(seconds);
                listener.newHighScore(score);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(gameTimer))
            step();
        else if (e.getSource().equals(endTimer))
            fillBoard();
        else if (e.getSource().equals(clockTimer))
            clock();
    }

    private void clock() {
        seconds++;
        for (BoardListener listener : listeners) {
            listener.clockChanged();
        }
    }

    public void pause() {
        if (state == GameState.InGame) {
            state = GameState.Paused;
            gameTimer.stop();
            clockTimer.stop();
        }
        else if (state == GameState.Paused) {
            state = GameState.InGame;
            gameTimer.start();
            clockTimer.start();
        }

        for (BoardListener listener : listeners) {
            listener.stateChanged();
        }
    }

    public GameState getState() {
        return state;
    }

    /**
     * Animation de fin
     */
    private void fillBoard() {
        map[endY][endX].setState(Square.State.Filled);
        map[endY][endX].setColor(new Color(50, 50, 50));

        for (BoardListener listener : listeners) {
            listener.boardChanged();
        }

        if (endY == 0 && endX == getWidth() - 1) {
            endTimer.stop();
        }
        else if (endX ==  getWidth() - 1) {
            endX = 0;
            endY--;
        }
        else {
            endX++;
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public Shape getNextShape() {
        return nextShape;
    }

    public HighScores getHighscores() {
        return highscores;
    }
}
