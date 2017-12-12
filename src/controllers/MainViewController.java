package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;

/**
 * Controls resurces/fxml_files/MainView.fxml 's media and actions
 * Adds some functionality too.
 * @author Rustem Azimov
 */
public class MainViewController {

    /**
     * The button that listens to drag files function
     *
     */
    @FXML
    private Button dragFilesHereButton;

    /**
     * A Text object that represents
     * the path(url) of the dragged file
     * in order to prompt user about he's/she's
     * selected the file that he/she wanted
     */
    @FXML
    private ListView<String> filePathListView;

    /**
     *  Handles drag file action
     *      1)Takes dragged files
     *      2)Puts paths in a String
     *      3)Shows that String
     *      4)Copies that String into Clipboard
     *
     * @param event Contains detailed info
     *              about drag file event
     *              such as corresponding
     *              Dragboard which have
     *              file objects that's been
     *              dragged
     */
    @FXML
    private void handleDragEnteredAction(DragEvent event) {
        /*
         * Delete all items from file paths list
         * that were the remainders of preceding action
         */
        filePathListView.getItems().clear();
        Dragboard db = event.getDragboard();
        if(db.hasFiles())
        {
            //Get dragged files
            List<File> files = db.getFiles();

            /*
             * Create a StringBuilder in order to gather
             * all paths into one String object as each path in a new line
             * like e.g.
             *      C:\Users\Rustem\Desktop\1.docx
             *      C:\Users\Rustem\Desktop\2.docx
             *      C:\Users\Rustem\Desktop\3.docx
             *      C:\Users\Rustem\Desktop\4.docx
             *      C:\Users\Rustem\Desktop\5.docx
             */
            StringBuilder copyUrlBuilder = new StringBuilder();

            /*
             * Iterate over loop in order to
             * add all paths into ^copyUrlBuilder^
             * add to the ^pathList^ that'll show file paths
             */
            for (File file : files) {
                //Get each file's path
                String path = file.getAbsolutePath();

                //Add each path to ^copyUrlBuilder^ and take pointer to the next line
                copyUrlBuilder.append(path + "\n");

                //Add each path to file paths' list
                filePathListView.getItems().add(path);
            }
            //Assign ^pathText^ to ^copyUrlBuilder^'s String representation
            String pathText = copyUrlBuilder.toString();

            //Copy the path automatically to Clipboard
            copy(pathText);
        }
        event.consume();
    }

    @FXML
    private void initialize() {
        /*
         * Invoke the method in order to add copy functionality
         * for mouse's right button action on a list item
         */
        setListCellFactory();
    }

    /**
     * When the mouse's right button was fired on an item
     * of the file paths list. Copy function will appear
     * as a menu. And if the copy's fired copy on an item of
     * the list. Just copy the items text to Clipboard
     */
    private void setListCellFactory() {
        filePathListView.setCellFactory(lv -> {

            ListCell<String> cell = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();

            //Create an item(copy functionality)
            MenuItem copy = new MenuItem("Copy");

            //Handle copy items action
            copy.setOnAction(event -> {
                //Copy the selected text to Clipboard
                copy(cell.getItem());
            });

            //Add copy item into the menu
            contextMenu.getItems().add(copy);

            cell.textProperty().bind(cell.itemProperty());

            /*
             * Show menu if and only if clicked item isn't empty
             */
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell ;
        });
    }

    /**
     *
     * @param txt contains text that's going to be copied into Clipboard
     */
    private void copy(String txt){
        //Copy ^txt^ into Clipboard
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(txt), null);

        //Shows a dialog in order to prompt user about Coping
        showMessage("Copied to Clipboard", "Info");
    }

    /**
     *
     * @param txt will be shown in the dialog
     * @param title of the dialog
     */
    private void showMessage(String txt, String title){
        JOptionPane.showMessageDialog(null, txt, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
