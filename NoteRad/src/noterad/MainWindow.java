package noterad;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import java.awt.MenuItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindow {

    protected Shell shlUntitledNoterad;
    private JFileChooser fileChooser;
    private Text text;


    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.open();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shlUntitledNoterad.open();
        shlUntitledNoterad.layout();
        while (!shlUntitledNoterad.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }


    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shlUntitledNoterad = new Shell();
        shlUntitledNoterad.setImage(SWTResourceManager.getImage(
            MainWindow.class, "/noterad/notes.png"));
        shlUntitledNoterad.setSize(450, 300);
        shlUntitledNoterad.setText("untitled - NoteRad");
        shlUntitledNoterad.setLayout(new FillLayout(SWT.HORIZONTAL));

        Menu menu = new Menu(shlUntitledNoterad, SWT.BAR);
        shlUntitledNoterad.setMenuBar(menu);
        
        org.eclipse.swt.widgets.MenuItem File = new org.eclipse.swt.widgets.MenuItem(menu, SWT.NONE);
        File.setText("File");
        
        org.eclipse.swt.widgets.MenuItem edit = new org.eclipse.swt.widgets.MenuItem(menu, SWT.NONE);
        edit.setText("Edit");

        text = new Text(shlUntitledNoterad, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL
            | SWT.MULTI);

        Button btnSave = new Button(shlUntitledNoterad, SWT.NONE);
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                clickedSave();

            }

        });
        btnSave.setText("save");

        Button btnNewButton = new Button(shlUntitledNoterad, SWT.NONE);
        btnNewButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseUp(MouseEvent e) {
                load();
            }
        });
        btnNewButton.setText("New Button");
        
        Button btnNew = new Button(shlUntitledNoterad, SWT.NONE);
        btnNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                newFile();
            }
        });
        btnNew.setText("new");

    }


    private void load() {
        fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File f = fileChooser.getSelectedFile();

        if (!f.getName().contains(".txt")) {
            // throw new IllegalArgumentException("File must be a .txt");
        }
        else {
            Scanner scan = null;
            try {
                scan = new Scanner(f);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            StringBuilder builder = new StringBuilder();
            while (scan.hasNext()) {
                builder.append(scan.nextLine());
                System.out.println();
                builder.append("\n");
            }

            text.setText(builder.toString());
            shlUntitledNoterad.setText(f.getName() + " - NoteRad");
        }
    }


    /**
     * Mimics a save as function. Creates a file of
     * the typed in name with the stored text inside.
     */
    private void saveAs() {
        String name = "untitled";
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/home/me/Documents"));

        int retrival = fileChooser.showSaveDialog(null);

        if (retrival == JFileChooser.APPROVE_OPTION) {
            System.out.println(text.getText());
            try {

                FileWriter fw = new FileWriter(fileChooser.getSelectedFile()
                    + ".txt");
                fw.write(text.getText());
                fw.flush();
                fw.close();

            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private void save() {
        FileWriter fw;
        try {
            fw = new FileWriter(fileChooser.getSelectedFile());
            fw.write(text.getText());
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clickedSave() {
        try {
            save();
        }
        catch (Exception j) {
            saveAs();
        }

    }
    
    private void newFile() {
        text.setText("");
        shlUntitledNoterad.setText("untitled" + " - NoteRad");
    }
}
