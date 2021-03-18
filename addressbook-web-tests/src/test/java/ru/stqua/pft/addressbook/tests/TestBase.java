package ru.stqua.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqua.pft.addressbook.appmanager.ApplicationManager;
import ru.stqua.pft.addressbook.model.ContactData;

public class TestBase {

  protected final ApplicationManager app = new ApplicationManager(BrowserType.IE);

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }


}
