import org.junit.runners.Parameterized;

public class OrderParameters {

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                { new String[]{"BLACK"} },
                { new String[]{"GREY"} },
                { new String[]{"BLACK", "GREY"} },
                { new String[]{} }
        };
    }
}
