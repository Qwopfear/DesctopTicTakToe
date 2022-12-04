package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicTacToe extends JFrame {
    int turn = 0;

    public static Lock lock = new ReentrantLock();
    Map<String , Component> componentsMap = new HashMap<>();
    Map<String , Cell> cellMap = new HashMap<>();
    JPanel gameField;
    JPanel toolbar;
    JPanel resetField;

    String player1 = "Human";
    String player2 = "Human";
    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 450);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        toolbar = new JPanel();
        toolbar.setLayout(new BorderLayout());
        resetField = new JPanel();
        gameField = new JPanel();



        gameField.setVisible(true);
        gameField.setLayout(new GridLayout(3,3,10,10));
        gameField.setBounds(10,50,this.getWidth()-10,100);
        addCells();


        add(gameField,BorderLayout.CENTER);




        resetField.setLayout(new BorderLayout());

        JLabel statusBar = new JLabel("Game is not started");
        statusBar.setName("LabelStatus");
        JButton resetButton = new JButton("Start");
        resetButton.setName("ButtonStartReset");
        resetButton.setBackground(new Color(1,1,1));
        resetButton.setForeground(new Color(255,255,255));

        JButton buttonPlayer1 = new JButton(player1);
        buttonPlayer1.setName("ButtonPlayer1");
        buttonPlayer1.addActionListener(e -> {
            if (player1.equals("Human")) {
                player1 = "Robot";
            } else {
                player1 = "Human";
            }
            buttonPlayer1.setText(player1);
        });

        JButton buttonPlayer2 = new JButton(player2);
        buttonPlayer2.setName("ButtonPlayer2");
        buttonPlayer2.addActionListener( e -> {
            if (player2.equals("Human")) {
                player2 = "Robot";
            } else {
                player2 = "Human";
            }
            buttonPlayer2.setText(player2);
        });


        Font f = new Font("Arial",Font.BOLD,12);
        resetButton.setFont(f);
        resetButton.addActionListener(e -> {
            turn = 0;
            for (Component c: gameField.getComponents()) {
                if (c instanceof JButton) {
                   ((JButton) c).setText(" ");
                    c.setEnabled(true);
                }
            }

            if (resetButton.getText().equals("Start")) {
                resetButton.setText("Reset");
                statusBar.setText("The turn of " + player1 + " Player (X)");
                buttonPlayer1.setEnabled(false);
                buttonPlayer2.setEnabled(false);
            } else {
                resetButton.setText("Start");
                statusBar.setText("Game is not started");
                buttonPlayer1.setEnabled(true);
                buttonPlayer2.setEnabled(true);
            }
        });

        componentsMap.put("LabelStatus",statusBar);
        componentsMap.put("ButtonStartReset",resetButton);


        resetField.add(statusBar,BorderLayout.WEST);
        toolbar.add(buttonPlayer1,BorderLayout.WEST);
        toolbar.add(resetButton, BorderLayout.CENTER);
        toolbar.add(buttonPlayer2,BorderLayout.EAST);

        add(toolbar,BorderLayout.NORTH);
        add(resetField,BorderLayout.SOUTH);

        JMenuBar jMenuBar = new JMenuBar();

        JMenu jMenu = new JMenu("Game");
        jMenu.setName("MenuGame");






        JMenuItem item1 = new JMenuItem("Human vs Human");
        item1.setName("MenuHumanHuman");
        item1.addActionListener(e -> {
            player1 = "Human";
            player2 = "Human";
            buttonPlayer1.setText(player1);
            buttonPlayer2.setText(player2);

            if (statusBar.getText().equals("Game is not started")) {
                resetButton.doClick();
            } else {
                resetButton.doClick();
                resetButton.doClick();
            }
        });
        JMenuItem item2 = new JMenuItem("Human vs Robot");
        item2.setName("MenuHumanRobot");
        item2.addActionListener(e -> {
            player1 = "Human";
            player2 = "Robot";
            buttonPlayer1.setText(player1);
            buttonPlayer2.setText(player2);
            if (statusBar.getText().equals("Game is not started")) {
                resetButton.doClick();
            } else {
                resetButton.doClick();
                resetButton.doClick();
            }
        });
        JMenuItem item3 = new JMenuItem("Robot vs Human");
        item3.setName("MenuRobotHuman");
        item3.addActionListener(e -> {
            player1 = "Robot";
            player2 = "Human";
            buttonPlayer1.setText(player1);
            buttonPlayer2.setText(player2);
            if (statusBar.getText().equals("Game is not started")) {
                resetButton.doClick();
            } else {
                resetButton.doClick();
                resetButton.doClick();
            }
        });
        JMenuItem item4 = new JMenuItem("Robot vs Robot");
        item4.setName("MenuRobotRobot");
        item4.addActionListener(e -> {
            player1 = "Robot";
            player2 = "Robot";
            buttonPlayer1.setText(player1);
            buttonPlayer2.setText(player2);
            if (statusBar.getText().equals("Game is not started")) {
                resetButton.doClick();
            } else {
                resetButton.doClick();
                resetButton.doClick();
            }
        });
        JMenuItem item5 = new JMenuItem("Exit");
        item5.setName("MenuExit");
        item5.addActionListener(e -> {
            System.exit(0);
        });

        jMenuBar.add(jMenu);

        jMenu.add(item1);
        jMenu.add(item2);
        jMenu.add(item3);
        jMenu.add(item4);
        jMenu.addSeparator();
        jMenu.add(item5);








        toolbar.add(jMenuBar,BorderLayout.NORTH);

        Thread t1 = new Thread(() -> {
                while (true) {
                    if (player1.equals("Robot") && statusBar.getText().equals("The turn of " + player1 + " Player (X)"))
                        if (turn % 2 == 0) {
                            if (cellMap.get("ButtonB2").getText().equals(" ")) {
                                cellMap.get("ButtonB2").setText("X");

                            }else {
                                    cellMap.values().stream()
                                            .filter(el -> el.getText().equals(" ")).findFirst().get().setText("X");
                            }
                            checkSells();
                            turn++;

                    }
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

        });

        Thread t2 = new Thread(() -> {
            while (true) {
                if (player2.equals("Robot") && statusBar.getText().equals("The turn of " + player2 + " Player (O)"))
                    if (turn % 2 != 0) {
                        if (cellMap.get("ButtonB2").getText().equals(" ")) {
                            cellMap.get("ButtonB2").setText("O");

                        }else {
                            cellMap.values().stream()
                                    .filter(el -> el.getText().equals(" ")).findFirst().get().setText("O");
                        }
                        checkSells();
                        turn++;

                    }
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }


        });

        Thread turnChecker = new Thread(() -> {
            while (true) {
                while (!statusBar.getText().startsWith("The turn of")) {
                    lock.lock();
                    lock.unlock();
                }

                if (turn % 2 != 0) {
                    statusBar.setText("The turn of " + player2 + " Player (O)");
                } else {
                    statusBar.setText("The turn of " + player1 + " Player (X)");
                }
                checkSells();


            }

        });

        turnChecker.start();
        t2.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        t1.start();

    }


    void addCells(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell button = new Cell(((char) (65 + j)) + String.valueOf(3 - i));
                button.setEnabled(false);
                button.addActionListener(e -> {
                    if (button.getText().equals(" ") && !((JLabel) resetField.getComponent(0)).getText().equals("Game is not started")) {
                        ((JButton) componentsMap.get("ButtonStartReset")).setText("Reset");
                        if (turn % 2 == 0) {
                            button.setText("X");
                        } else {
                            button.setText("O");
                        }
                        turn++;
                        checkSells();
                    }
                });
                cellMap.put(button.getName(), button);
                gameField.add(button);
            }
        }

    }

    private void checkSells() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Component c: gameField.getComponents()) {
            if (c instanceof JButton){
                stringBuilder.append(((JButton) c).getText());
            }
        }

//

        JLabel statusBar = (JLabel) componentsMap.get("LabelStatus");

        if (stringBuilder.charAt(0) == stringBuilder.charAt(3) && stringBuilder.charAt(3) == stringBuilder.charAt(6)
        && !List.of(stringBuilder.charAt(0),stringBuilder.charAt(3),stringBuilder.charAt(6)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(0) == 'X' ? player1 : player2)   + " Player (" + stringBuilder.charAt(0) + ") wins");
        } else if (stringBuilder.charAt(1) == stringBuilder.charAt(4) && stringBuilder.charAt(4) == stringBuilder.charAt(7)
                && !List.of(stringBuilder.charAt(1),stringBuilder.charAt(4),stringBuilder.charAt(7)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(1) == 'X' ? player1 : player2)   + " Player (" + stringBuilder.charAt(1) + ") wins");
        } else if (stringBuilder.charAt(2) == stringBuilder.charAt(5) && stringBuilder.charAt(5) == stringBuilder.charAt(8)
                && !List.of(stringBuilder.charAt(2),stringBuilder.charAt(5),stringBuilder.charAt(8)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(2) == 'X' ? player1 : player2)   + " Player (" + stringBuilder.charAt(2) + ") wins");
        } else if (stringBuilder.charAt(0) == stringBuilder.charAt(1) && stringBuilder.charAt(1) == stringBuilder.charAt(2)
                && !List.of(stringBuilder.charAt(0),stringBuilder.charAt(1),stringBuilder.charAt(2)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(0) == 'X' ? player1 : player2)   + " Player " + stringBuilder.charAt(0) + " wins");
        } else if (stringBuilder.charAt(3) == stringBuilder.charAt(4) && stringBuilder.charAt(4) == stringBuilder.charAt(5)
                && !List.of(stringBuilder.charAt(3),stringBuilder.charAt(4),stringBuilder.charAt(5)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(3) == 'X' ? player1 : player2)   + " Player (" + stringBuilder.charAt(3) + ") wins");
        } else if (stringBuilder.charAt(6) == stringBuilder.charAt(7) && stringBuilder.charAt(7) == stringBuilder.charAt(8)
                && !List.of(stringBuilder.charAt(6),stringBuilder.charAt(7),stringBuilder.charAt(8)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(6) == 'X' ? player1 : player2)   + " Player (" + stringBuilder.charAt(6) + ") wins");
        } else if (stringBuilder.charAt(0) == stringBuilder.charAt(4) && stringBuilder.charAt(4) == stringBuilder.charAt(8)
                && !List.of(stringBuilder.charAt(0),stringBuilder.charAt(4),stringBuilder.charAt(8)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(0) == 'X' ? player1 : player2)   + " Player (" + stringBuilder.charAt(0) + ") wins");
        } else if (stringBuilder.charAt(2) == stringBuilder.charAt(4) && stringBuilder.charAt(4) == stringBuilder.charAt(6)
                && !List.of(stringBuilder.charAt(2),stringBuilder.charAt(4),stringBuilder.charAt(6)).contains(' ')) {
            statusBar.setText("The " +( stringBuilder.charAt(2) == 'X' ? player1 : player2)   + " Player (" + stringBuilder.charAt(2) + ") wins");
        } else if (!stringBuilder.toString().contains(" ")) {
            statusBar.setText("Draw");
        }

        if (statusBar.getText().equals("Draw") ||
                statusBar.getText().contains("wins")) {
            toolbar.getComponents()[0].setEnabled(true);
            toolbar.getComponents()[2].setEnabled(true);
            ((JButton) toolbar.getComponents()[1]).setText("Reset");
            for (Component component : gameField.getComponents()) {
                component.setEnabled(false);
            }
        }
    }




}

class Cell extends JButton{
    public Cell(String field){
        setBackground(new Color(248, 176, 47));
        setText(" ");
        setFont(new Font("Courier",Font.BOLD,24));
        setName("Button"+field);
    }
}


