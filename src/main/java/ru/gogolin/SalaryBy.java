package ru.gogolin;

import java.util.List;

public class SalaryBy {
    String info;
    List<Employee> employees;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "SalaryBy{" +
                "info='" + info + '\'' +
                ", employees=" + employees +
                '}';
    }
}
