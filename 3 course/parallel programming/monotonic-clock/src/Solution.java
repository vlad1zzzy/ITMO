import org.jetbrains.annotations.NotNull;

/**
 * В теле класса решения разрешено использовать только финальные переменные типа RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 *
 * @author : Kryukov Vladislav
 */
public class Solution implements MonotonicClock {
    private final RegularInt
            c1 = new RegularInt(0),
            c2 = new RegularInt(0),
            c3 = new RegularInt(0),
            v1 = new RegularInt(0),
            v2 = new RegularInt(0);

    @Override
    public void write(@NotNull Time time) {
        c1.setValue(time.getD1());
        c2.setValue(time.getD2());
        c3.setValue(time.getD3());
        v2.setValue(time.getD2());
        v1.setValue(time.getD1());
    }

    @NotNull
    @Override
    public Time read() {
        int v1Value = v1.getValue();
        int v2Value = v2.getValue();
        int c3Value = c3.getValue();
        int c2Value = c2.getValue();
        int c1Value = c1.getValue();

        return new Time(
                c1Value,
                c1Value == v1Value ? c2Value : 0,
                c1Value == v1Value && c2Value == v2Value ? c3Value : 0
        );
    }
}
