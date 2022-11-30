import java.util.HashMap;
import java.util.Map;

public class State {

    String name;
    Map<String, State> links = new HashMap<String, State>();

    boolean isTerminal;
    boolean isError;

    String tokenRepresenting;


    public State()
    {
        isTerminal = false;
        isError = false;
    }

    public State receives(char input)
    {
        char ch = input;

        for (Map.Entry<String, State> state : links.entrySet())
        {
            if(state.getKey().contains(String.valueOf(input)))
            {
                return state.getValue();
            }
        }

        System.out.println("State which occurs the error: " + name);
        System.out.println("The input that causes the crash: " + (ch == '\n' ? "LF" : (ch == '\t' ? "TAB" : (ch == ' ' ? "SPACE" : ch))));
        return null;
    }

    public void addLink(String inputs, State toState)
    {
        links.put(inputs, toState);
    }


}
