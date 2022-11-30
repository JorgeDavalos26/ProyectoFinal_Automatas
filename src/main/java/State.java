import java.util.HashMap;
import java.util.Map;

public class State
{
    String name, tokenRepresenting;
    Map<String, State> links = new HashMap<String, State>();
    boolean isTerminal, isError;

    public State()
    {
        isTerminal = false;
        isError = false;
        tokenRepresenting = "";
        name = "q?";
    }

    public State receives(char input)
    {
        for (Map.Entry<String, State> state : links.entrySet())
            if(state.getKey().contains(String.valueOf(input)))
                return state.getValue();

        char ch = input;
        System.out.println("State: " + name);
        System.out.println("The input that causes crashing: " + (ch == '\n' ? "LF" : (ch == '\t' ? "TAB" : (ch == ' ' ? "SPACE" : ch))));
        return null;
    }

    public void addLink(String inputs, State toState)
    {
        links.put(inputs, toState);
    }

}
