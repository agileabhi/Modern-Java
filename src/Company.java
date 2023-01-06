@FunctionalInterface
public interface Company {
    public default void getName() {
        System.out.println("Company");
    }
    public int getNumberOfEmployees();
}
