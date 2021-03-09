package ru.stqua.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SessionHelper extends HelperBase {

  public SessionHelper(FirefoxDriver wd) {
    super(wd);
  }

  public void login(String username, String psw) {
    type(By.name("user"),username);
    type(By.name("pass"),psw);
    click(By.xpath("//input[@value='Login']"));
  }
}
