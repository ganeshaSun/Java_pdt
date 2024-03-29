package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistationHelper extends HelperBase{

  public RegistationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") +"signup_page.php");
    type(By.name("username"),username);
    type(By.name("email"),email);
    click(By.cssSelector("input[value='Signup']"));
  }

  public void finish(String confirmationLink, String password, String username) {
    wd.get(confirmationLink);
    type(By.name("realname"), username);;
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("//button/span[text()='Update User']"));
  }
}
