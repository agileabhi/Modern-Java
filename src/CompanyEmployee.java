public class CompanyEmployee implements  Company,Employee{
   private int numberOfEmployees;
    @Override
    public void getName() {
        Company.super.getName();
        Employee.super.getName();
    }
    public int getNumberOfEmployees(){
        return this.numberOfEmployees;
    }
}
