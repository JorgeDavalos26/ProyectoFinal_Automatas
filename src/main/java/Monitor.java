import java.util.HashMap;
import java.util.Map;

public class Monitor {

    public static String currentEvaluatedToken = "";

    public static Map<String, Integer> tokens = new HashMap<String, Integer>();

    static {
        tokens.put("Palabra reservada", 0);
        tokens.put("Identificador", 0);
        tokens.put("Operador relacional", 0);
        tokens.put("Operador lógico", 0);
        tokens.put("Operador aritmético", 0);
        tokens.put("Asignación", 0);
        tokens.put("Número Entero", 0);
        tokens.put("Número decimal", 0);
        tokens.put("Comentario", 0);
        tokens.put("Parentesis", 0);
        tokens.put("Llave", 0);
        tokens.put("Errors", 0);
    }

    public static void addOne(String token)
    {
        for (Map.Entry<String, Integer> t : tokens.entrySet())
        {
            if(t.getKey().equals(token))
            {
                t.setValue(t.getValue() + 1);
                break;
            }
        }
    }

    public static void clearTokens()
    {
        for (Map.Entry<String, Integer> t : tokens.entrySet())
        {
            t.setValue(0);
        }
    }

    public static void printTokens()
    {
        System.out.println("\n");
        for (Map.Entry<String, Integer> t : tokens.entrySet())
        {
            System.out.println("Key: " + t.getKey() + "  Value: " + t.getValue());
        }
    }

    public static String[][] getTableInfo()
    {
        return new String[][]{
            {"Palabras reservadas",tokens.get("Palabra reservada") + "",tokens.get("Errors") + ""},
            {"Identificador",tokens.get("Identificador") + "",""},
            {"Operador racional",tokens.get("Operador relacional") + "",""},
            {"Operador lógico",tokens.get("Operador lógico") + "",""},
            {"Operador aritmético",tokens.get("Operador aritmético") + "",""},
            {"Asignación",tokens.get("Asignación") + "",""},
            {"Número entero",tokens.get("Número Entero") + "",""},
            {"Número decimal",tokens.get("Número decimal") + "",""},
            {"Comentario",tokens.get("Comentario") + "",""},
            {"Paréntesis",tokens.get("Parentesis") + "",""},
            {"Llave",tokens.get("Llave") + "",""},
        };
    }


}
