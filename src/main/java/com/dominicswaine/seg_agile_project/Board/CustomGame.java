package com.dominicswaine.seg_agile_project.Board;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import com.dominicswaine.seg_agile_project.Logic.Parser;

/**
 * This class enables players to set up a custom game and to save this as a configuration
 * to play at a later date.
 *
 * @author David Mahgerefteh
 * @version 11/12/2018
 */
public class CustomGame {

    private JFrame frame;
    private JPanel containerOfEverything, containerOfBackButton, containerOfTextAndDropdown,
            containerOfSelections, containerOfSpinners, containerOfCancelTuz, containerOfSaveAndStart;
    private JComboBox dropdown;
    private ButtonGroup buttonGroup;
    private JRadioButton tuzCanceller;
    private Boolean isPlayer;
    private HashMap<String, JSpinner> mapOfSpinners;
    private int[] playerValues, opponentValues;
    private String playerTuz, opponentTuz;
    private JLabel totalKorgools, playerTuzLabel, opponentTuzLabel;
    private JOptionPane pane;
    private com.dominicswaine.seg_agile_project.Logic.Game game;

    /**
     * Construct the custom game window.
     */
    public CustomGame() {

        //Initialise the frame

        frame  = new JFrame();
        frame.setSize(new Dimension(475, 700));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Initialise necessary variables

        buttonGroup = new ButtonGroup();
        isPlayer = true;
        mapOfSpinners = new HashMap<String, JSpinner>();
        playerValues = new int[10];
        opponentValues = new int[10];
        playerTuz = "0";
        opponentTuz = "0";
        pane = new JOptionPane();

        //Make and populate the panels

        makePanels();
        populatePanels();
        containerOfEverything.add(containerOfBackButton);
        containerOfEverything.add(containerOfTextAndDropdown);
        containerOfEverything.add(containerOfSelections);
        containerOfEverything.add(containerOfSpinners);
        containerOfEverything.add(containerOfCancelTuz);
        containerOfEverything.add(containerOfSaveAndStart);
        frame.add(containerOfEverything);
        frame.setTitle("Toguz Korgool - Custom Game");
        frame.setVisible(true);

    }

    /**
     * Generate all the panels required by the class.
     */
    private void makePanels() {

        containerOfEverything = new ContainerOfEverything(System.getProperty("user.dir") + "/src/main/resources/wood_grain_custom_game.jpg");
        containerOfEverything.setLayout(new BoxLayout(containerOfEverything, BoxLayout.Y_AXIS));
        containerOfBackButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        containerOfBackButton.setOpaque(false);
        containerOfTextAndDropdown = new JPanel();
        containerOfTextAndDropdown.setLayout(new BoxLayout(containerOfTextAndDropdown, BoxLayout.Y_AXIS));
        containerOfTextAndDropdown.setOpaque(false);
        containerOfSelections = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        containerOfSelections.setOpaque(false);
        containerOfSpinners = new JPanel(new GridLayout(0,2));
        containerOfSpinners.setOpaque(false);
        containerOfCancelTuz = new JPanel(new FlowLayout());
        containerOfCancelTuz.setOpaque(false);
        containerOfSaveAndStart = new JPanel(new FlowLayout());
        containerOfSaveAndStart.setOpaque(false);

    }

    /**
     * Create the back button and add it to its container.
     */
    private void populateBackButtonContainer() {

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.addActionListener(e -> goBack());
        containerOfBackButton.add(backButton);

    }

    /**
     * Create the title, instructions, dropdown, and add them to their respective container.
     */
    private void populateTextAndDropdownContainer() {

        JLabel title = new JLabel("Custom Game");
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructions = new JLabel("<html><p style=\"text-align:justify\">To begin a custom game, first use the dropdown" +
                " to select who the parameters will apply to; " +
                "you, or your opponent. You are then able to  " +
                "specify the amount of Korgools per Kazan and Hole, " +
                "and also whether a hole is a Tuz. Note that the total " +
                "number of Korgools must be exactly 162, the two Tuzzes " +
                "cannot be the same, and no Tuz can be 9. " +
                "Press Save to be able to play this custom game at a later date. Press Start to begin the custom game.</p></html>");

        instructions.setFont(new Font("Tahoma", Font.PLAIN, 14));
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        containerOfTextAndDropdown.add(title);
        containerOfTextAndDropdown.setBorder(new EmptyBorder(0,20, 0, 20 ));
        containerOfTextAndDropdown.add(Box.createVerticalStrut(17));
        containerOfTextAndDropdown.add(instructions);
        containerOfTextAndDropdown.add(Box.createVerticalStrut(25));
        dropdown = new JComboBox();
        dropdown.setName("dropdown");
        dropdown.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dropdown.addItem("You (White Side)");
        dropdown.addItem("Opponent (Black Side)");
        dropdown.setMaximumSize(dropdown.getPreferredSize());
        dropdown.setToolTipText("Select who you want the settings to apply to");
        dropdown.addActionListener(e -> changePlayer());
        containerOfTextAndDropdown.add(dropdown);
        containerOfTextAndDropdown.add(Box.createVerticalStrut(20));

    }

    /**
     * Create the labels for the total number of korgools and the player and opponent tuz selections.
     */
    private void populateContainerOfSelections() {

        totalKorgools = new JLabel("Total Korgools: 0");
        totalKorgools.setName("totalKorgools");
        totalKorgools.setFont(new Font("Tahoma", Font.BOLD, 13));
        playerTuzLabel = new JLabel("Your Tuz: None");
        playerTuzLabel.setName("playerTuzLabel");
        playerTuzLabel.setForeground(Color.BLUE);
        playerTuzLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        opponentTuzLabel = new JLabel("Opponent Tuz: None");
        opponentTuzLabel.setName("opponentTuzLabel");
        opponentTuzLabel.setForeground(Color.RED);
        opponentTuzLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        containerOfSelections.add(playerTuzLabel);
        containerOfSelections.add(totalKorgools);
        containerOfSelections.add(opponentTuzLabel);
        containerOfSelections.setBorder(new EmptyBorder(10,0, 10, 0 ));

    }

    /**
     * Create units to be added to the container of spinners. Note, a unit is comprised of
     * a label, representing what entity the JSpinner value applies to, a JSpinner for taking
     * in user values, and finally, a radio button, to allow for tuz selection.
     */
    private void populateContainerOfSpinners() {

        for (int i = 0; i < 10; i++) {

            containerOfSpinners.add(makeUnit(i));

        }

    }

    /**
     * Generate a unit to be added to the container of spinners.
     *
     * @param i counter used to influence label for JSpinner
     * @return a JPanel that is a unit to be added to the container of spinners
     */
    private JPanel makeUnit(int i) {

        JPanel unit = new JPanel(new BorderLayout());
        unit.setOpaque(false);
        JLabel label;
        JPanel panelContainingSpinner = new JPanel(new BorderLayout());
        panelContainingSpinner.setOpaque(false);

        if (i == 0) { //For Kazan, we want no tuz radio button

            label = new JLabel("Kazan:");
            unit.add(Box.createHorizontalStrut(21), BorderLayout.EAST);

        }

        else {

            label = new JLabel("Hole " + i + ":");
            JRadioButton tuzController = new JRadioButton();
            tuzController.setOpaque(false);
            tuzController.setName("" + i); //Setting an ID for each radio button - Hole 1 has ID 1, Hole 9 has ID 9, cancel has ID 0
            tuzController.addActionListener((e -> checkRadioButtons(tuzController.getName())));
            buttonGroup.add(tuzController);
            unit.add(tuzController, BorderLayout.EAST);

        }

        label.setBorder(new EmptyBorder(0, 0 ,0 , 10));
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        SpinnerModel spinnerSettings = new SpinnerNumberModel(0, 0, 162, 1);
        JSpinner spinner = new JSpinner(spinnerSettings);
        spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
        spinner.setName("" + i); //Setting an ID for each spinner, Kazan has ID 0, Hole x has ID x
        spinner.addChangeListener(e -> obtainValue(spinner.getName()));
        mapOfSpinners.put("" + i, spinner);
        panelContainingSpinner.add(spinner, BorderLayout.CENTER);
        panelContainingSpinner.setBorder(new EmptyBorder(5,0, 5, 0 ));
        unit.add(label, BorderLayout.WEST);
        unit.add(panelContainingSpinner, BorderLayout.CENTER);
        unit.setBorder(new EmptyBorder(5,50,5,50));

        return unit;

    }

    /**
     * Create the radio button necessary for cancelling a tuz selection, along with
     * its respective panel.
     */
    private void populateContainerOfCancelTuz() {

        tuzCanceller = new JRadioButton();
        tuzCanceller.setOpaque(false);
        buttonGroup.add(tuzCanceller);
        tuzCanceller.setName("0");
        tuzCanceller.addActionListener((e -> checkRadioButtons(tuzCanceller.getName())));
        JLabel cancelTuz = new JLabel("Cancel Tuz:");
        cancelTuz.setFont(new Font("Tahoma", Font.PLAIN, 14));
        containerOfCancelTuz.add(cancelTuz);
        containerOfCancelTuz.add(tuzCanceller);

    }

    /**
     * Create the save and start buttons, along with their respective
     * panel.
     */
    private void populateContainerOfSaveAndStart() {

        JButton saveButton = new JButton("Save");
        saveButton.setName("Save");
        saveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveButton.addActionListener(e -> saveGame());
        JButton startButton = new JButton("Start");
        startButton.setName("Start");
        startButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        startButton.addActionListener(e -> checkValues());
        containerOfSaveAndStart.add(saveButton);
        containerOfSaveAndStart.add(startButton);

    }

    /**
     * Populate all the panels.
     */
    private void populatePanels() {

        populateBackButtonContainer();
        populateTextAndDropdownContainer();
        populateContainerOfSelections();
        populateContainerOfSpinners();
        populateContainerOfCancelTuz();
        populateContainerOfSaveAndStart();

    }

    //Listeners

    /**
     * Navigates back to the main menu.
     */
    private void goBack() {

        new MainMenu();
        frame.dispose();

    }

    /**
     * Called when the dropdown is used, changes the player selected
     * and populates the spinners with their old values.
     */
    private void changePlayer() {

        isPlayer =!isPlayer;

        if (isPlayer) {

            dropdown.setToolTipText("Settings apply to you");

            //Populate spinners with old values

            for (int i = 0; i < 10; i ++) {

                JSpinner currentSpinner = mapOfSpinners.get("" + i);
                currentSpinner.setValue(playerValues[i]);
            }

        }

        else {

            dropdown.setToolTipText("Settings apply to your opponent");

            for (int i = 0; i < 10; i ++) {

                JSpinner currentSpinner = mapOfSpinners.get("" + i);
                currentSpinner.setValue(opponentValues[i]);
            }


        }

        buttonGroup.clearSelection(); //Deselects the radio button

    }

    /**
     * Obtain the value from the spinner and add it to the appropriate array;
     * either the player or opponent.
     *
     * @param idOfSpinner the ID of the spinner whose value we wish to obtain
     */
    private void obtainValue(String idOfSpinner) {

        JSpinner currentSpinner = mapOfSpinners.get(idOfSpinner);
        int currentValue = (Integer)currentSpinner.getValue();

        if (isPlayer) {

            playerValues[Integer.parseInt(idOfSpinner)] = currentValue;
            //In this array, index represents the ID of the spinner. Kazan has ID/indx 0, Hole x has ID/indx x

        }

        else {

            opponentValues[Integer.parseInt(idOfSpinner)] = currentValue;

        }

        updateContainerOfSelections();

    }

    /**
     * Save the custom game configuration so that it can be used again in another game
     * at a later date.
     */
    private void saveGame() {

        int sumOfPlayerAndOpponentValues = sumOfPlayerAndOpponentValues();

        if (sumOfPlayerAndOpponentValues != 162) {

            pane.showMessageDialog(frame,
                    "Total number of Korgools must be exactly 162, please change your values.",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE);

        }

        else {

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());

            int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());

                Parser.saveCustomGame(selectedFile.getAbsolutePath(), playerTuz, opponentTuz, playerValues, opponentValues);

                pane.showMessageDialog(frame,
                        "Your game has been saved.",
                        "Saved Game",
                        JOptionPane.INFORMATION_MESSAGE);

            }

        }

    }

    /**
     * Check that the total number of korgools is exactly
     * 162, as a game cannot start until this criterion is met.
     */
    private void checkValues() {

        int sumOfPlayerAndOpponentValues = sumOfPlayerAndOpponentValues();

        if (sumOfPlayerAndOpponentValues != 162) {

            pane.showMessageDialog(frame,
                    "Total number of Korgools must be exactly 162, please change your values.",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE);

        }

        else { //All values are correct, a custom game can now be constructed

            game = new com.dominicswaine.seg_agile_project.Logic.Game(playerTuz, opponentTuz, playerValues, opponentValues);
            frame.dispose();

        }

    }

    /**
     * Ensure that the tuz selections are not the same and are not 9.
     *
     * @param radioID the ID of the radio button we wish to check
     */
    private void checkRadioButtons(String radioID) {

        if (radioID.equals("0")) { //If the tuz remains 0, it means that no tuz was selected

            if (isPlayer) {

                playerTuz = "0";

            }

            else {

                opponentTuz = "0";

            }

        }

        else if (isPlayer && !radioID.equals(opponentTuz)  && !radioID.equals("9")) {

            playerTuz = radioID;

        }

        else if (!isPlayer && !radioID.equals(playerTuz) && !radioID.equals("9")) {

            opponentTuz = radioID;

        }

        else {

            if (radioID.equals("9")) {

                pane.showMessageDialog(frame,
                        "The tuz cannot be 9.",
                        "Warning",
                        JOptionPane.ERROR_MESSAGE);

                buttonGroup.clearSelection();

            }

            else {

                pane.showMessageDialog(frame,
                        "The tuzzes cannot match.",
                        "Warning",
                        JOptionPane.ERROR_MESSAGE);

                buttonGroup.clearSelection();

            }

        }

        updateContainerOfSelections();

    }

    //Helper functions

    /**
     * Update the tuz selections and total number of korgools.
     */
    private void updateContainerOfSelections() {

        int sumOfPlayerAndOpponentValues = sumOfPlayerAndOpponentValues();
        totalKorgools.setText("Total Korgools: " + (sumOfPlayerAndOpponentValues));

        if (playerTuz.equals("0") && opponentTuz.equals("0")) {

            playerTuzLabel.setText("Your Tuz: None");
            opponentTuzLabel.setText("Opponent Tuz: None");

        }

        else if (playerTuz.equals("0") && !opponentTuz.equals("0") ) {

            playerTuzLabel.setText("Your Tuz: None");
            opponentTuzLabel.setText("Opponent Tuz: " + opponentTuz);

        }

        else if (opponentTuz.equals("0") && !playerTuz.equals("0")) {

            opponentTuzLabel.setText("Opponent Tuz: None");
            playerTuzLabel.setText("Your Tuz: " + playerTuz);

        }

        else {

            playerTuzLabel.setText("Your Tuz: " + playerTuz);
            opponentTuzLabel.setText("Opponent Tuz: " + opponentTuz);

        }

    }

    /**
     * Calculate the sum of the player and opponent values.
     *
     * @return the sum of the player and opponent values
     */
    public int sumOfPlayerAndOpponentValues() {

        int sumOfPlayerValues = 0;
        int sumOfOpponentValues = 0;

        for (int i = 0; i < 10; i++) {

            sumOfPlayerValues = sumOfPlayerValues + playerValues[i];
            sumOfOpponentValues = sumOfOpponentValues + opponentValues[i];

        }

        return sumOfPlayerValues + sumOfOpponentValues;

    }

    //Methods required in integration testing

    /**
     * Get the player or opponent's values - that is, their hole and kazan selections.
     *
     * @param isPlayer true gets player values, false gets opponent values
     * @return either the player or opponent values as an int[]
     */
    public int[] getValues(Boolean isPlayer) {

        if (isPlayer) {

            return playerValues;
        }

        return opponentValues;

    }


    /**
     * Get the player or opponent's tuz selection.
     *
     * @param isPlayer true gets player tuz, false gets opponent tuz
     * @return either the player or opponent tuz as a string
     */
    public String getTuz(Boolean isPlayer) {

        if (isPlayer) {

            return playerTuz;
        }

        return opponentTuz;

    }

    /**
     * Changes who is currently making the selections.
     *
     * @return who is currently making the selections
     */
    public Boolean changeUser() {

        isPlayer = !isPlayer;
        return isPlayer;

    }

    /**
     * Gets the frame containing all components.
     *
     * @return the frame containing all components
     */
    public JFrame getFrame() {

        return frame;

    }

    /**
     * Gets who is making the current selections.
     *
     * @return if it is the player or opponent making the selections. True if it is the player.
     */
    public Boolean getIsPlayer() {

        return isPlayer;

    }

    /**
     * Gets the hashmap containing all the spinners.
     *
     * @return the hashmap containing all the spinners
     */
    public HashMap<String, JSpinner> getMapOfSpinners() {

        return mapOfSpinners;

    }

    /**
     * Gets the custom game constructed.
     *
     * @return the custom game constructed
     */
    public com.dominicswaine.seg_agile_project.Logic.Game getGame() {

        return game;

    }

}
