import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Automata extends JPanel implements ActionListener
{

    Timer timer;
    JFileChooser fileChooser;
    JLabel filenameLabel;
    JTextArea fileContentTextArea, logsTextArea;

    ArrayList<String> words;
    int indexCurrentWord;
    String contentOfFile;
    final String keyWords[] = new String[]{"if", "else", "switch", "case", "default", "for", "while", "break", "int", "String", "double", "char"};

    public Automata()
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
        logsLabel.setBounds(new Rectangle(470, 130, 340, 40));
        logsLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        add(logsLabel);

        JButton checkFileButton = new JButton("Verificar archivo");
        checkFileButton.addActionListener(e -> checkFile());
        checkFileButton.setBounds(new Rectangle(470, 100, 240, 30));
        add(checkFileButton);

        logsTextArea = new JTextArea();
        logsTextArea.setBounds(new Rectangle(0,0, 180, 440));
        JScrollPane logsScrollPane = new JScrollPane(logsTextArea);
        logsScrollPane.setBounds(new Rectangle(470, 170, 240, 540));
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

        JTable errorsTable = new JTable(data,column);
        for(int i=0; i<data.length; i++) errorsTable.setRowHeight(i, 30);
        errorsTable.getTableHeader().setFont(new Font("sansserif", Font.PLAIN, 12));

        JScrollPane errorsScrollPane = new JScrollPane(errorsTable);
        errorsScrollPane.setBounds(new Rectangle(740, 100, 430, 353));
        add(errorsScrollPane);

        JTextArea explanationTextArea = new JTextArea();
        String explanationContent = "Palabras reservadas:   (if, else, switch, case, default, for, while, break, int, String, double, char) \n"
                + "Identificador:   (Inicia con letra, sin espacios en blanco, sin caracteres especiales, excepto el guión bajo) \n"
                + "Operador racional:   (<, <=, >, >=, ==, !=) \n"
                + "Operador lógico:   (&&, ||, !) \n"
                + "Operador aritmético:   (+, -, *, /, %) \n"
                + "Asignación:   ( = ) \n"
                + "Número entero \n"
                + "Número decimal \n"
                + "Comentario:   ( Con el formato /* */) \n"
                + "Paréntesis:   ( (,) ) \n"
                + "Llave:   ( {, } ) \n";

        explanationTextArea.setText(explanationContent);

        JScrollPane explanationScrollPane = new JScrollPane(explanationTextArea);
        explanationScrollPane.setBounds(new Rectangle(740, 460, 430, 250));
        add(explanationScrollPane);

        // ---------------------------------------------------------------------------------------------

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Cargar archivo de texto", "txt");
        fileChooser.setFileFilter(filter);

        timer = new Timer(1000, this);

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
        catch(Exception e)
        {
        }
    }

    public void checkFile()
    {
        recolectWords();
        runAutomata();
    }

    public void recolectWords()
    {
        if(!contentOfFile.isEmpty())
        {
            words.clear();
            logsTextArea.setText("");

            StringBuilder word = new StringBuilder();

            for(int i=0; i<contentOfFile.length(); i++)
            {
                if(contentOfFile.charAt(i) != '\r')
                {
                    word.append(contentOfFile.charAt(i));
                    if(contentOfFile.charAt(i) == ' ' || contentOfFile.charAt(i) == '\n' || contentOfFile.charAt(i) == '\t')
                    {
                        logsTextArea.append("Word caught: " + word.substring(0, word.length()-1) + "\n");
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
        }
    }

    public void runAutomata()
    {
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String word = words.get(indexCurrentWord);
        logsTextArea.append("Analyzing: " + word.substring(0, word.length()-1) + "\n");

        checkIfReservedWord(word);

        char[] chars = word.toCharArray();
        for (char ch : chars)
        {
            logsTextArea.append("   char: " + (ch == '\n' ? "LF" : (ch == '\t' ? "TAB" : (ch == ' ' ? "SPACE" : ch))) + "\n");

            inputToAutomata(ch);

        }
        indexCurrentWord++;

    }

    public void checkIfReservedWord(String word)
    {
        // compare with keywords
    }

    public void inputToAutomata(char input)
    {
        // automata
    }

}
