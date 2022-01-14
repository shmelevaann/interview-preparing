package main.java.ru.chiffa.lesson1.task1;

import java.util.Objects;

public class Person {
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String country;
    private final String address;
    private final String phone;
    private final Integer age;
    private final String gender;

    private Person(String firstName, String lastName, String middleName,
                   String country, String address, String phone,
                   Integer age, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }

    public static class PersonBuilder {
        private String firstName;
        private String lastName;
        private String middleName;
        private String country;
        private String address;
        private String phone;
        private Integer age;
        private String gender;

        public PersonBuilder withFirstName(String firstName) {
            Objects.requireNonNull(firstName, "First name shouldn't be null");

            this.firstName = firstName;
            return this;
        }

        public PersonBuilder withLastName(String lastName) {
            Objects.requireNonNull(lastName, "Last name shouldn't be null");

            this.lastName = lastName;
            return this;
        }

        public PersonBuilder withMiddleName(String middleName) {
            Objects.requireNonNull(middleName, "Middle name shouldn't be null");

            this.middleName = middleName;
            return this;
        }

        public PersonBuilder withCountry(String country) {
            Objects.requireNonNull(country, "Country shouldn't be null");

            this.country = country;
            return this;
        }

        public PersonBuilder withAddress(String address) {
            Objects.requireNonNull(address, "Address shouldn't be null");

            this.address = address;
            return this;
        }

        public PersonBuilder withPhone(String phone) {
            Objects.requireNonNull(phone, "Phone shouldn't be null");

            this.phone = phone;
            return this;
        }

        public PersonBuilder withAge(Integer age) {
            Objects.requireNonNull(age, "Age shouldn't be null");

            this.age = age;
            return this;
        }

        public PersonBuilder withGender(String gender) {
            Objects.requireNonNull(gender, "Gender shouldn't be null");

            this.gender = gender;
            return this;
        }

        public Person build() {
            //условная логика, что персон должен иметь хотя бы имя и фамилию
            Objects.requireNonNull(this.firstName, "First name shouldn't be null");
            Objects.requireNonNull(this.lastName, "Last name shouldn't be null");

            return new Person(this.firstName, this.lastName, this.middleName,
                    this.country, this.address, this.phone, this.age, this.gender);
        }
    }
}
