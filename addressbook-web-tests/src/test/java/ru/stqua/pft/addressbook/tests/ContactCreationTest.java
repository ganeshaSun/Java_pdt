package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("TestName", "TestMiddleName",
            "TestLastName", "TestNickname", "Title Test",
            "Bookovsky", "196190 Saint-Petersburg, Moskovsky pr. 163",
            "88123456456", "+78945634", "testaddress@mail.ru"));
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToContactPage();
  }


}
