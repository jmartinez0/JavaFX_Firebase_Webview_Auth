/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author MoaathAlrajab
 */
public class Person {
    private String name;
    private String major;
    private int age;

    public Person(String name, String major, int age) {
        this.name = name;
        this.major = major;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return name + major;
    }
    
    @Override
    public boolean equals(Object obj) {
        Person p = (Person) obj;
        if (this.name.equals(p.name)) {
            if (this.major.equals(p.major)) {
                if (this.age == (p.age)) {
                    return true;
                }
            }
        }
        return false;
    }
}
