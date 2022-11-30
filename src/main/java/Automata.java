import javax.swing.*;

public class Automata
{
    public State currentState, initialState;

    public final static String numbers = "0123456789";
    public final static String letters = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    public final static String special = "+-*/%{}()|&=><_!.";
    public final static String special2 = "@#$^~`[]\";:',?\\";

    public Automata()
    {
        State q0 = new State();
        q0.isTerminal = true;
        q0.name = "q0";

        State q1 = new State();
        q1.name = "q1";
        q1.tokenRepresenting = "Operador aritmético";

        State q2 = new State();
        q2.name = "q2";
        q2.tokenRepresenting = "Operador aritmético";

        State q3 = new State();
        q3.name = "q3";
        q3.tokenRepresenting = "Llave";

        State q4 = new State();
        q4.name = "q4";
        q4.tokenRepresenting = "Parentesis";

        State q5 = new State();
        q5.name = "q5";
        q5.tokenRepresenting = "Número Entero";

        State q6 = new State();
        q6.name = "q6";
        q6.tokenRepresenting = "Número decimal";

        State q7 = new State();
        q7.name = "q7";
        q7.tokenRepresenting = "Número decimal";

        State q8 = new State();
        q8.name = "q8";
        q8.tokenRepresenting = "Operador lógico";

        State q9 = new State();
        q9.name = "q9";
        q9.tokenRepresenting = "Operador lógico";

        State q10 = new State();
        q10.name = "q10";
        q10.tokenRepresenting = "Operador lógico";

        State q11 = new State();
        q11.name = "q11";
        q11.tokenRepresenting = "Operador lógico";

        State q12 = new State();
        q12.name = "q12";
        q12.tokenRepresenting = "Asignación";

        State q13 = new State();
        q13.name = "q13";
        q13.tokenRepresenting = "Operador relacional";

        State q14 = new State();
        q14.name = "q14";
        q14.tokenRepresenting = "Operador relacional";

        State q15 = new State();
        q15.name = "q15";
        q15.tokenRepresenting = "Operador lógico";

        State q16 = new State();
        q16.name = "q16";
        q16.tokenRepresenting = "Identificador";

        State q17 = new State();
        q17.name = "q17";
        q17.tokenRepresenting = "Identificador";

        State q18 = new State();
        q18.name = "q18";
        q18.tokenRepresenting = "Comentario";

        State q19 = new State();
        q19.name = "q19";
        q19.tokenRepresenting = "Comentario";

        State q20 = new State();
        q20.name = "q20";
        q20.tokenRepresenting = "Comentario";

        State qError = new State();
        qError.name = "qError";
        qError.isError = true;
        qError.tokenRepresenting = "Errors";

        q0.addLink("+-", q1);
        q0.addLink("*/%", q2);
        q0.addLink("{}", q3);
        q0.addLink("()", q4);
        q0.addLink(numbers, q5);
        q0.addLink(letters, q17);
        q0.addLink("|", q8);
        q0.addLink("&", q10);
        q0.addLink("=", q12);
        q0.addLink("><", q13);
        q0.addLink("_", q16);
        q0.addLink("!", q15);
        q0.addLink(" \n\t", q0);

        q1.addLink(numbers, q5);
        q1.addLink(" \n\t", q0);
        q1.addLink(letters + special, qError);

        q2.addLink("*", q20);
        q2.addLink(" \n\t", q0);
        q2.addLink(letters + numbers + "+-/%{}()|&=><_!.", qError);

        q3.addLink(" \n\t", q0);
        q3.addLink(letters + numbers + special, qError);

        q4.addLink(" \n\t", q0);
        q4.addLink(letters + numbers + special, qError);

        q5.addLink(numbers, q5);
        q5.addLink(".", q6);
        q5.addLink(" \n\t", q0);
        q5.addLink(letters + "+-*/%{}()|&=><_!", qError);

        q6.addLink(numbers, q7);
        q6.addLink(letters + special, qError);

        q7.addLink(numbers, q7);
        q7.addLink(" \n\t", q0);
        q7.addLink(letters + special, qError);

        q8.addLink("|", q9);
        q8.addLink(letters + numbers + "+-*/%{}()&=><_!.", qError);

        q9.addLink(" \n\t", q0);
        q9.addLink(letters + numbers + "+-*/%{}()&=><_!.", qError);

        q10.addLink("&", q11);
        q10.addLink(letters + numbers + "+-*/%{}()|=><_!.", qError);

        q11.addLink(" \n\t", q0);
        q11.addLink(letters + numbers + "+-*/%{}()|=><_!.", qError);

        q12.addLink(" \n\t", q0);
        q12.addLink(letters + numbers + special, qError);

        q13.addLink("=", q14);
        q13.addLink(" \n\t", q0);
        q13.addLink(letters + numbers + "+-*/%{}()|&><_!.", qError);

        q14.addLink(" \n\t", q0);
        q14.addLink(letters + numbers + special, qError);

        q15.addLink("=", q14);
        q15.addLink(" \n\t", q0);
        q15.addLink(letters + numbers + "+-*/%{}()|&><_!.", qError);

        q16.addLink(letters + numbers, q17);
        q16.addLink(" " + special, qError);

        q17.addLink(letters + numbers + "_", q17);
        q17.addLink(" \n\t", q0);
        q17.addLink("+-*/%{}()|&=><!.", qError);

        q18.addLink(" \n\t", q0);
        q18.addLink(letters + numbers + special, qError);

        q19.addLink("/", q18);
        q19.addLink(letters + numbers + "+-*%{}()|&=><_!.", q20);
        q19.addLink(" \n\t", qError);

        q20.addLink("*", q19);
        q20.addLink(letters + numbers + special, q20);
        q20.addLink(" \n\t", qError);

        qError.addLink(letters + numbers + special, qError);
        qError.addLink(" \n\t", q0);

        currentState = null;
        initialState = q0;
    }

    public void consume(char input, JTextArea logs)
    {
        if(currentState == null) currentState = initialState; // q0

        currentState = currentState.receives(input);

        if(currentState.isTerminal)
        {
            Monitor.addOne(Monitor.currentEvaluatedToken);
            if(!Monitor.currentEvaluatedToken.equals("")) Monitor.addOne("Ocurrencias");
            Monitor.currentEvaluatedToken = "";
            currentState = null;
        }
        else if(currentState.isError)
        {
            if(!Monitor.currentEvaluatedToken.equals(""))
            {
                Monitor.addOne("Errores");
                Monitor.addOne("Error-"+Monitor.currentEvaluatedToken);
                Monitor.currentEvaluatedToken = "";
            }
            if(" \n\t".contains(String.valueOf(input)))
            {
                Monitor.currentEvaluatedToken = "";
                currentState = null;
            }
        }
        else
        {
            Monitor.currentEvaluatedToken = currentState.tokenRepresenting;
        }
    }

}
