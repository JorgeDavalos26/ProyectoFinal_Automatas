import java.util.HashMap;
import java.util.Map;

public class Monitor
{
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

        tokens.put("Error-Identificador", 0);
        tokens.put("Error-Operador relacional", 0);
        tokens.put("Error-Operador lógico", 0);
        tokens.put("Error-Operador aritmético", 0);
        tokens.put("Error-Asignación", 0);
        tokens.put("Error-Número Entero", 0);
        tokens.put("Error-Número decimal", 0);
        tokens.put("Error-Comentario", 0);
        tokens.put("Error-Parentesis", 0);
        tokens.put("Error-Llave", 0);

        tokens.put("Errores", 0);
        tokens.put("Ocurrencias", 0);
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
        for (Map.Entry<String, Integer> t : tokens.entrySet()) t.setValue(0);
    }

    public static String[][] getTableInfo()
    {
        return new String[][]{
            {"Palabras reservadas",tokens.get("Palabra reservada") + "",""},
            {"Identificador",tokens.get("Identificador") + "",tokens.get("Error-Identificador") + ""},
            {"Operador racional",tokens.get("Operador relacional") + "",tokens.get("Error-Operador relacional") + ""},
            {"Operador lógico",tokens.get("Operador lógico") + "",tokens.get("Error-Operador lógico") + ""},
            {"Operador aritmético",tokens.get("Operador aritmético") + "",tokens.get("Error-Operador aritmético") + ""},
            {"Asignación",tokens.get("Asignación") + "",tokens.get("Error-Asignación") + ""},
            {"Número entero",tokens.get("Número Entero") + "",tokens.get("Error-Número Entero") + ""},
            {"Número decimal",tokens.get("Número decimal") + "",tokens.get("Error-Número decimal") + ""},
            {"Comentario",tokens.get("Comentario") + "",tokens.get("Error-Comentario") + ""},
            {"Paréntesis",tokens.get("Parentesis") + "",tokens.get("Error-Parentesis") + ""},
            {"Llave",tokens.get("Llave") + "",tokens.get("Error-Llave") + ""},
            {"Totales",(tokens.get("Ocurrencias") + tokens.get("Palabra reservada")) + "",tokens.get("Errores") + ""},
        };
    }

    public static void printTokens()
    {
        System.out.println("\n");
        for (Map.Entry<String, Integer> t : tokens.entrySet())
        {
            System.out.println("Key: " + t.getKey() + "  Value: " + t.getValue());
        }
    }

}
