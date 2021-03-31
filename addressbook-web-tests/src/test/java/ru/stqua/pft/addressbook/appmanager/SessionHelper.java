package ru.stqua.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase {

  public SessionHelper(WebDriver wd) {
    super(wd);
  }

  public void login(String username, String psw) {
    type(By.name("user"),username);
    type(By.name("pass"),psw);
    click(By.xpath("//input[@value='Login']"));
  }
}
