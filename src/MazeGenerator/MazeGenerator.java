package MazeGenerator;

import Vector.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator extends JPanel implements KeyListener, ActionListener {
    final int WIDTH;
    final int HEIGHT;
    final int SIZE;

    int GridWidth;
    int GridHeight;

    ArrayList<Tile> grid;
    Stack<Tile> path;

    Vector2 MazeBuilder;
    Vector2 MaxTile;
    int MaxDistance;

    final Timer clock;
    final Random rand;

    boolean DONE = false;

    public MazeGenerator(int width, int height, int size) {
        WIDTH = width;
        HEIGHT = height;
        SIZE = size;

        GridWidth = WIDTH/SIZE;
        GridHeight = HEIGHT/SIZE;

        clock = new Timer(5, this);
        rand = new Random();

        grid = new ArrayList<>();
        path = new Stack<>();

        MazeBuilder = new Vector2(0, 0);


        for(int i = 0; i < GridHeight; i++) {
            for(int j = 0; j < GridWidth; j++) {
                grid.add(new Tile(j, i, SIZE));
            }
        }

        path.push(grid.getFirst());


        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        clock.start();
    }

    public boolean buildMaze() {
        int currentTileIndex = (int) (MazeBuilder.x + GridWidth * MazeBuilder.y);

        ArrayList<Tile> AvailableMoves = new ArrayList<>();
        ArrayList<String> directions = new ArrayList<>();

        // North
        if (MazeBuilder.y > 0) {
            if (!grid.get(currentTileIndex - GridWidth).touched() && grid.get(currentTileIndex - GridWidth) != path.peek()) {
                AvailableMoves.add(grid.get(currentTileIndex - GridWidth));
                directions.add("north");
            }
        }

        // South
        if (MazeBuilder.y < GridHeight - 1) {
            if (!grid.get(currentTileIndex + GridWidth).touched() && grid.get(currentTileIndex + GridWidth) != path.peek()) {
                AvailableMoves.add(grid.get(currentTileIndex + GridWidth));
                directions.add("south");
            }
        }

        // East
        if (MazeBuilder.x < GridWidth - 1) {
            if (!grid.get(currentTileIndex + 1).touched() && grid.get(currentTileIndex + 1) != path.peek()) {
                AvailableMoves.add(grid.get(currentTileIndex + 1));
                directions.add("east");
            }
        }

        // West
        if (MazeBuilder.x > 0) {
            if (!grid.get(currentTileIndex - 1).touched() && grid.get(currentTileIndex - 1) != path.peek()) {
                AvailableMoves.add(grid.get(currentTileIndex - 1));
                directions.add("west");
            }
        }


        if (!AvailableMoves.isEmpty()) {
            int direction = rand.nextInt(0, AvailableMoves.size());
            MazeBuilder = AvailableMoves.get(direction).pos.clone();
            path.push(grid.get(currentTileIndex));

            switch (directions.get(direction)) {
                case "north" -> {
                    grid.get(currentTileIndex).northWall = false;
                    grid.get(currentTileIndex - GridWidth).southWall = false;
                    if (path.size() > MaxDistance) {
                        MaxDistance = path.size();
                        MaxTile = grid.get(currentTileIndex - GridWidth).pos;
                    }
                }
                case "south" -> {
                    grid.get(currentTileIndex).southWall = false;
                    grid.get(currentTileIndex + GridWidth).northWall = false;
                    if (path.size() > MaxDistance) {
                        MaxDistance = path.size();
                        MaxTile = grid.get(currentTileIndex + GridWidth).pos;
                    }
                }
                case "east" -> {
                    grid.get(currentTileIndex).eastWall = false;
                    grid.get(currentTileIndex + 1).westWall = false;
                    if (path.size() > MaxDistance) {
                        MaxDistance = path.size();
                        MaxTile = grid.get(currentTileIndex + 1).pos;
                    }
                }
                case "west" -> {
                    grid.get(currentTileIndex).westWall = false;
                    grid.get(currentTileIndex - 1).eastWall = false;
                    if (path.size() > MaxDistance) {
                        MaxDistance = path.size();
                        MaxTile = grid.get(currentTileIndex - 1).pos;
                    }
                }
            }



        } else if (!path.isEmpty()) {
            MazeBuilder = path.pop().pos.clone();
        } else {
            return true;
        }
        return false;
    }

    public void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(1));
        for(Tile tile : grid) {
            if (tile.touched()) {
                // draw path
                g.setColor(Color.darkGray);
                g.fillRect((int) tile.pos.x * SIZE, (int) tile.pos.y * SIZE, SIZE, SIZE);

                // draw Furthest Tile
                g.setColor(Color.red);
                g.fillRect((int)MaxTile.x * SIZE, (int)MaxTile.y * SIZE, SIZE, SIZE);

                // draw MazeBuilder
                g.setColor(Color.lightGray);
                g.fillRect((int)MazeBuilder.x * SIZE, (int)MazeBuilder.y * SIZE, SIZE, SIZE);

            }

            // draw Grid
            g.setColor(Color.gray);
            g.setStroke(new BasicStroke(3));
            tile.draw(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        draw(g2D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!DONE) {
            if(buildMaze()) {
                DONE = true;
                System.out.println("MAZE COMPLETE");
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
