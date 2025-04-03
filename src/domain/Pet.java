package domain;

import domain.Enums.Sex;
import domain.Enums.Type;

public class Pet {
    private String name;
    private Type type;
    private Sex sex;
    private String address;
    private String age;
    private String weight;
    private String race;

    public Pet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setRace(String race) {
        this.race = race;
    }


}
