import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class UI extends JPanel implements ActionListener
{
    // timer
    Timer timer;

    // ui components
    JFileChooser fileChooser;
    JLabel filenameLabel;
    JTextArea fileContentTextArea, logsTextArea;
    JButton runAutomataFastButton, runAutomataSlowButton;
    JTable errorsTable;
    JScrollPane errorsScrollPane;

    // variables
    ArrayList<String> words;
    int indexCurrentWord;
    String contentOfFile;
    final String keyWords[] = new String[]{"if", "else", "switch", "case", "default", "for", "while", "break", "int", "String", "double", "char"};

    // automata
    Automata automata = new Automata();

    public UI()
    {
        setPreferredSize(new Dimension(1200, 800));
        setLayout(null);

        // ---------------------------------------------------------------------------------------------

        JLabel titleLabel = new JLabel("Autómatas");
        titleLabel.setBounds(new Rectangle(40, 10, 300, 40));
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        add(titleLabel);

        JLabel optionsLabel = new JLabel("Opciones");
        optionsLabel.setBounds(new Rectangle(40, 60, 300, 40));
        optionsLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        add(optionsLabel);

        JLabel devLabel = new JLabel("Desarrolladores: Sarahí Salazar 20110332 - Jorge Dávalos 20110354");
        devLabel.setBounds(new Rectangle(780, 760, 600, 40));
        devLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        add(devLabel);

        // ---------------------------------------------------------------------------------------------

        JButton fileButton = new JButton("Subir archivo");
        fileButton.addActionListener(e -> selectFile());
        fileButton.setBounds(new Rectangle(40, 100, 400, 30));
        add(fileButton);

        JLabel fileContentLabel = new JLabel("Contenido");
        fileContentLabel.setBounds(new Rectangle(40, 130, 300, 40));
        fileContentLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        add(fileContentLabel);

        fileContentTextArea = new JTextArea();
        fileContentTextArea.setEditable(false);
        fileContentTextArea.setBounds(new Rectangle(40, 170, 400, 540));
        JScrollPane fileContentScrollPane = new JScrollPane(fileContentTextArea);
        fileContentScrollPane.setBounds(new Rectangle(40, 170, 400, 540));
        add(fileContentScrollPane);

        filenameLabel = new JLabel("nombre_de_archivo.txt");
        filenameLabel.setBounds(new Rectangle(40, 700, 300, 40));
        filenameLabel.setFont(new Font("Serif", Font.ITALIC, 14));
        add(filenameLabel);

        // ---------------------------------------------------------------------------------------------

        JLabel logsLabel = new JLabel("Logs");
        logsLabel.setBounds(new Rectangle(470, 160, 340, 40));
        logsLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        add(logsLabel);

        runAutomataSlowButton = new JButton("Correr autómata (lento)");
        runAutomataSlowButton.addActionListener(e -> checkFile(false));
        runAutomataSlowButton.setBounds(new Rectangle(470, 100, 240, 30));
        add(runAutomataSlowButton);

        runAutomataFastButton = new JButton("Correr autómata (rápido)");
        runAutomataFastButton.addActionListener(e -> checkFile(true));
        runAutomataFastButton.setBounds(new Rectangle(470, 132, 240, 30));
        add(runAutomataFastButton);

        logsTextArea = new JTextArea();
        logsTextArea.setEditable(false);
        logsTextArea.setBounds(new Rectangle(0,0, 180, 440));
        JScrollPane logsScrollPane = new JScrollPane(logsTextArea);
        logsScrollPane.setBounds(new Rectangle(470, 200, 240, 510));
        add(logsScrollPane);

        // ---------------------------------------------------------------------------------------------

        JLabel errorsLabel = new JLabel("Errores");
        errorsLabel.setBounds(new Rectangle(740, 60, 300, 40));
        errorsLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        add(errorsLabel);

        String data[][] =
                {
                        {"Palabras reservadas","",""},
                        {"Identificador","",""},
                        {"Operador racional","",""},
                        {"Operador lógico","",""},
                        {"Operador aritmético","",""},
                        {"Asignación","",""},
                        {"Número entero","",""},
                        {"Número decimal","",""},
                        {"Comentario","",""},
                        {"Paréntesis","",""},
                        {"Llave","",""},
                };

        String column[] = {"Tokens","Número de ocurrencias", "Número de errores"};

        errorsTable = new JTable(data,column);
        errorsTable.setEnabled(false);
        for(int i=0; i<data.length; i++) errorsTable.setRowHeight(i, 30);
        errorsTable.getTableHeader().setFont(new Font("sansserif", Font.PLAIN, 12));

        errorsScrollPane = new JScrollPane(errorsTable);
        errorsScrollPane.setBounds(new Rectangle(740, 100, 430, 353));
        add(errorsScrollPane);

        JTextArea explanationTextArea = new JTextArea();
        explanationTextArea.setEditable(false);
        String explanationContent = "\t\t~ INFORMACIÓN ~\n"
                + " * Palabras reservadas: \n"
                + "   if, else, switch, case, default, for, while, break, int, String, double, char \n"
                + " * Identificador: \n"
                + "   Inicia con letra, sin espacios y caracteres especiales, excepto guión bajo \n"
                + " * Operador racional:   (<, <=, >, >=, ==, !=) \n"
                + " * Operador lógico:   (&&, ||, !) \n"
                + " * Operador aritmético:   (+, -, *, /, %) \n"
                + " * Asignación:   ( = ) \n"
                + " * Número entero:   (989) \n"
                + " * Número decimal:   (24.35) \n"
                + " * Comentario:   ( Con el formato /* */) \n"
                + " * Paréntesis:   ( (,) ) \n"
                + " * Llave:   ( {, } ) \n";

        explanationTextArea.setText(explanationContent);

        JScrollPane explanationScrollPane = new JScrollPane(explanationTextArea);
        explanationScrollPane.setBounds(new Rectangle(740, 460, 430, 252));
        add(explanationScrollPane);

        // ---------------------------------------------------------------------------------------------

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Cargar archivo de texto", "txt");
        fileChooser.setFileFilter(filter);

        words = new ArrayList<>();
        indexCurrentWord = 0;
        contentOfFile = "";
    }

    public void selectFile()
    {
        try
        {
            int result = fileChooser.showOpenDialog(this);
            if (result != JFileChooser.CANCEL_OPTION)
            {
                File fileName = fileChooser.getSelectedFile();
                if ((fileName == null) || (fileName.getName().equals("")))
                {
                    filenameLabel.setText("???");
                }
                else
                {
                    filenameLabel.setText(fileName.getAbsolutePath());
                    contentOfFile = Files.readString(Path.of(fileName.getAbsolutePath()));
                    fileContentTextArea.setText(contentOfFile);
                }
            }
        }
        catch(Exception e){}
    }

    public void checkFile(boolean fast)
    {
        try
        {
            recolectWords();
            runAutomata(fast);
        }
        catch (Exception e){}
    }

    public void recolectWords() throws Exception
    {
        if(!contentOfFile.isEmpty())
        {
            runAutomataFastButton.setEnabled(false);
            runAutomataSlowButton.setEnabled(false);

            indexCurrentWord = 0;
            words.clear();
            logsTextArea.setText("");

            Monitor.clearTokens();

            logsTextArea.append("\n     ~ LEER ARCHIVO ~\n\n");

            StringBuilder word = new StringBuilder();
            for(int i=0; i<contentOfFile.length(); i++)
            {
                if(contentOfFile.charAt(i) != '\r')
                {
                    word.append(contentOfFile.charAt(i));
                    if(contentOfFile.charAt(i) == ' ' || contentOfFile.charAt(i) == '\n' || contentOfFile.charAt(i) == '\t')
                    {
                        logsTextArea.append("Palabra: " + word.substring(0, word.length()-1) + "\n");
                        words.add(word.toString());
                        word = new StringBuilder();
                    }
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                    "No se ha seleccionado un archivo de texto el cual verificar",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new Exception();
        }
    }

    public void runAutomata(boolean fast)
    {
        if(fast) timer = new Timer(1, this);
        else timer = new Timer(1500, this);
        logsTextArea.append("\n     ~ INICIAR AUTOMATA ~\n\n");
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(indexCurrentWord < words.size())
        {
            String word = words.get(indexCurrentWord);
            logsTextArea.append("-------------------------" + "\n");

            if("abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ".contains(String.valueOf(word.charAt(0))))
            {
                if(checkIfReservedWord(word))
                {
                    Monitor.addOne("Palabra reservada");
                }
                else
                {
                    giveInputToAutomata(word);
                }
            }
            else
            {
                giveInputToAutomata(word);
            }

            indexCurrentWord++;
        }
        else
        {
            //Monitor.printTokens();
            runAutomataFastButton.setEnabled(true);
            runAutomataSlowButton.setEnabled(true);
            timer.stop();

            String data[][] = Monitor.getTableInfo();
            String column[] = {"Tokens","Número de ocurrencias", "Número de errores"};

            errorsTable = new JTable(data,column);
            errorsTable.setEnabled(false);
            for(int i=0; i<data.length; i++) errorsTable.setRowHeight(i, 30);
            errorsTable.getTableHeader().setFont(new Font("sansserif", Font.PLAIN, 12));

            errorsScrollPane = new JScrollPane(errorsTable);
            errorsScrollPane.setBounds(new Rectangle(740, 100, 430, 353));
            add(errorsScrollPane);
        }
    }

    public void giveInputToAutomata(String word)
    {
        char[] chars = word.toCharArray();
        for (char ch : chars)
        {
            logsTextArea.append("Caracter: " + (ch == '\n' ? "LF" : (ch == '\t' ? "TAB" : (ch == ' ' ? "SPACE" : ch))) + "\n");
            inputToAutomata(ch);
        }
    }

    public boolean checkIfReservedWord(String word)
    {
        // compare with keywords
        for(int i=0; i<keyWords.length; i++)
        {
            if(keyWords[i].equals(word.substring(0, word.length()-1)))
            {
                return true;
            }
        }
        return false;
    }

    public void inputToAutomata(char input)
    {
        // automata
        automata.consume(input, logsTextArea);
    }

}
