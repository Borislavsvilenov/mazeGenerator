import MazeGenerator.MazeGenerator;

import javax.swing.*;

public static void main() {
    int Width = 800;
    int Height = 800;
    int TileSIze = 10;

    JFrame frame = new JFrame("MazeGenerator");
    frame.setSize(Width, Height);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    MazeGenerator maze = new MazeGenerator(Width, Height, TileSIze);
    frame.add(maze);
    frame.pack();

    frame.setVisible(true);

    maze.requestFocus();
}