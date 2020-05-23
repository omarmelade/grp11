package sample.constantes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Constantes {

    public static final List<String> days = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("MONDAY");
                add("TUESDAY");
                add("WEDNESDAY");
                add("THURSDAY");
                add("FRIDAY");
            }});

    public static final List<String> hours = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("8");
                add("9");
                add("10");
                add("11");
                add("12");
                add("13");
                add("14");
                add("15");
                add("16");
                add("17");
            }});
}
