package ru.stqua.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private final String id;
  private final String firstname;
  private final String middlename;
  private final String lastname;
  private final String nickname;
  private final String title;
  private final String company;
  private final String address;
  private final String hometel;
  private final String mobile;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  private final String email;
  private  String group;

  public ContactData(String lastname, String firstname, String middlename, String group,  String nickname, String title, String company, String address, String hometel, String mobile, String email) {
    this.id = null;
    this.firstname = firstname;
    this.middlename = middlename;
    this.group = group;
    this.lastname = lastname;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    this.address = address;
    this.hometel = hometel;
    this.mobile = mobile;
    this.email = email;
  }
  public ContactData(String id, String lastname, String firstname) {
    this.id = id;
    this.lastname = lastname;
    this.firstname = firstname;
    this.middlename = null;
    this.group = null;
    this.nickname = null;
    this.title = null;
    this.company = null;
    this.address = null;
    this.hometel = null;
    this.mobile = null;
    this.email = null;
  }

  public ContactData( String lastname, String firstname) {
    this.id = null;
    this.lastname = lastname;
    this.firstname = firstname;
    this.middlename = null;
    this.group = null;
    this.nickname = null;
    this.title = null;
    this.company = null;
    this.address = null;
    this.hometel = null;
    this.mobile = null;
    this.email = null;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getHometel() {
    return hometel;
  }

  public String getMobile() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }

  public String getGroup() {
    return group;
  }
}
